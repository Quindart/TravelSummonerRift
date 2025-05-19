from app.database import PostgresStore
from app.config.model_config import Mistral
from app.prompts.base_prompt import BasePrompt
import json
from app.models.state import State
from app.tools.chat_tools import Chat
from langchain.schema.messages import AIMessage, HumanMessage, SystemMessage
import re

class Nodes:
    def __init__(self):
        self._postgres = PostgresStore()
        self._mistral = Mistral()
        self._llm = self._mistral.llm()
        self._prompt = BasePrompt()
        self._chat = Chat()
        self._llm_bind_tools = self._chat.get_llm_binds_tools()
        self._tool_node = self._chat.get_tool_node()


    
    @staticmethod
    def _ai_to_json(ai: AIMessage):
        if hasattr(ai, "content"):
            content = ai.content
        elif isinstance(ai, str):
            content = ai
        else:
            return {}

        content = re.sub(r"^```json\n|```$", "", content.strip(), flags=re.MULTILINE)

        try:
            return json.loads(content)
        except json.JSONDecodeError as e:
            print("JSON decode error:", e)
            return {}
        
    def route(self, state: State):
        query = state.get("question")
        decision = self._llm.invoke([SystemMessage(content=self._prompt.route_instruction), HumanMessage(content=query)])
        print("Decision:", decision)
        decision = self._ai_to_json(decision)
        print("Decision from AI:", decision)
        
        if decision.get("datasource") == "tools":
            state["next_state"] = "tools"  # Tên chính xác của node tiếp theo
        else:
            state["next_state"] = "generate"  # Dựa trên biểu đồ của bạn
        
        return state 
    
    def using_tools(self, state: State):
        question = state.get("question")
        response = self._llm_bind_tools.invoke([SystemMessage(content=self._prompt.using_tools_prompt.format(user_id=state.get("user_id"))), HumanMessage(content=question)])
        if response.tool_calls:
            tool_run = self._tool_node.invoke([response])
            print("Tool run:", tool_run)
            final_result = self._llm.invoke([HumanMessage(content=self._prompt.tool_prompt.format(question=question, tool_run=tool_run, user_id=state.get("user_id")))])
            print(final_result)
            state["messages"] = final_result.content
            state["next_state"] = "useful"
        else:
          state["next_state"] = "generate"
        return state


    def write_query(self, state: State):
        query = self._chat.write_query(state)
        state["query"] = query
        return state
    
    # def execute_query(self, state: State):
    #     result = self._chat.execute_query(state)
    #     state["result"] = result
    #     return state
    
    # def generate_answer(self, state: State):
    #     answer = self._chat.generate_answer(state)
    #     state["messages"] = answer
    #     return state
    
    def call_model(self, state: State):
        language = state.get("language", "Vietnamese")
        prompt = self._prompt.generate_prompt.format(
            result=state["messages"],
            messages=state["messages"],
            question=state["question"],
            language=language
        )
        response = self._llm.invoke(prompt)
        return { "messages": response, "language": language }
    
