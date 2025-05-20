from app.database import PostgresStore
from app.config.model_config import Mistral, Gemini
from app.prompts.base_prompt import BasePrompt
import json
from app.models.state import State
from app.tools.chat_tools import Chat
from langchain.schema.messages import AIMessage, HumanMessage, SystemMessage
from app.exceptions.chat_exception import InvalidInputException, ModelNotFoundException, ToolExecutionException
import re

class Nodes:
    def __init__(self):
        try:
            self._postgres = PostgresStore()
            self._mistral = Mistral()
            self._gemini = Gemini()
            self._llm = self._gemini.llm()
            self._prompt = BasePrompt()
            self._chat = Chat()
            self._llm_bind_tools = self._chat.get_llm_binds_tools()
            self._tool_node = self._chat.get_tool_node()
        except Exception as e:
            raise ModelNotFoundException(f"Failed to initialize chat models: {str(e)}")

    @staticmethod
    def _ai_to_json(ai: AIMessage):
        if hasattr(ai, "content"):
            content = ai.content
        elif isinstance(ai, str):
            content = ai
        else:
            raise InvalidInputException("Invalid AI message format")

        content = re.sub(r"^```json\n|```$", "", content.strip(), flags=re.MULTILINE)

        try:
            return json.loads(content)
        except json.JSONDecodeError as e:
            raise InvalidInputException(f"Failed to parse AI response as JSON: {str(e)}")
        
    def route(self, state: State):
        if not state or not state.get("question"):
            raise InvalidInputException("Question is required")
            
        try:
            query = state.get("question")
            decision = self._llm.invoke([SystemMessage(content=self._prompt.route_instruction), HumanMessage(content=query)])
            print("Decision:", decision)
            decision = self._ai_to_json(decision)
            print("Decision from AI:", decision)
            
            if decision.get("datasource") == "tools":
                state["next_state"] = "tools"
            else:
                state["next_state"] = "generate"
            
            return state
        except Exception as e:
            raise ToolExecutionException(f"Error in routing decision: {str(e)}")
    
    def using_tools(self, state: State):
        if not state or not state.get("question"):
            raise InvalidInputException("Question is required")

        try:
            question = state.get("question")
            response = self._llm_bind_tools.invoke([
                SystemMessage(content=self._prompt.using_tools_prompt.format(user_id=state.get("user_id"))),
                HumanMessage(content=question)
            ])

            print("Response from LLM:", response)

            if response.tool_calls:
                tool_run = self._tool_node.invoke([response])
                print("Tool run:", tool_run)
                final_result = self._llm.invoke([
                    HumanMessage(content=self._prompt.tool_prompt.format(
                        question=question,
                        tool_run=tool_run,
                        user_id=state.get("user_id")
                    ))
                ])
                
                assistant_reply = {
                    "role": "assistant",
                    "content": final_result.content
                }

                # Nếu messages chưa tồn tại hoặc rỗng, thì thêm mới
                if not state.get("messages"):
                    state["messages"] = [assistant_reply]
                else:
                    state["messages"][-1] = assistant_reply

                state["next_state"] = "useful"
            else:
                state["next_state"] = "generate"

            return state

        except Exception as e:
            raise ToolExecutionException(f"Error in tool execution: {str(e)}")

    
    # def execute_query(self, state: State):
    #     result = self._chat.execute_query(state)
    #     state["result"] = result
    #     return state
    
    # def generate_answer(self, state: State):
    #     answer = self._chat.generate_answer(state)
    #     state["messages"] = answer
    #     return state
    
    def call_model(self, state: State): 
        try:
            language = state.get("language", "Vietnamese")
            prompt = self._prompt.generate_prompt.format(
                result=state["messages"],
                messages=state["messages"],
                question=state["question"],
                language=language
            )
            response = self._llm.invoke(prompt)

            return {"messages": [{"content": response.content, "role": "assistant"}], "language": language}
        except Exception as e:
            raise ToolExecutionException(f"Error in model call: {str(e)}")
    
