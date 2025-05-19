from app.database import PostgresStore
# from langchain_community.tools import BraveSearch
from dotenv import load_dotenv
import os
from langchain.schema.document import Document
import json
import requests
from langchain_core.tools import tool
from langgraph.prebuilt import ToolNode
from app.config.model_config import Mistral
from langchain.schema.messages import HumanMessage
from typing_extensions import Annotated
from typing import TypedDict
from app.prompts.base_prompt import BasePrompt
from app.models.state import State

class QueryOutput(TypedDict):
    """Generated SQL query."""

    query: Annotated[str, ..., "Syntactically valid SQL query."]

class Chat:
    def __init__(self):
        self._db = PostgresStore().get_db()
        self._prompt = BasePrompt()
        self._model = Mistral()
        self._llm = self._model.llm()

        @tool
        def query_tour(state: State):
            """Write a query to the database to get tour information."""
            prompt = self._prompt.query_prompt.format(
                dialect=self._db.dialect,
                top_k=10,
                table_info=self._db.get_table_info(),
                input=state["question"],
                user_id=state["user_id"]
            )
            structured_llm = self._llm.with_structured_output(QueryOutput)
            result = structured_llm.invoke(prompt)

            state["query"] = result["query"]
            print("query", state["query"])
            # thực hiện query
            result = self._db.run(result["query"])
            # print("result", result)

            state["result"] = result

            return result

        # @tool
        # def execute_query(self, state: State):
        #     """Execute a query to the database."""
        #     result = self._db.invoke(state["query"])

        #     return result

        # @tool
        # def generate_answer(self, state: State):
        #     """Generate an answer to the question."""
        #     prompt = self._prompt.generate_prompt.invoke({
        #         "result": state["result"],
        #         "messages": state["messages"],
        #         "question": state["question"],
        #     })
        #     response = self._model.invoke(prompt)
        #     return response.content

        # @tool
        # def call_model(self, state: State):
        #     """Call the model to generate an answer to the question."""
        #     language = state.get("language", "Vietnamese")
        #     prompt = self._prompt.generate_prompt.invoke({
        #         "messages": state["messages"],
        #         "question": state["question"],
        #         "language": language
        #     })
        #     response = self._model.invoke(prompt)
        #     return { "messages": response, "language": language }
    
        self._tools = [query_tour]
        self._tool_node = ToolNode(self._tools)

        self._llm_binds_tools = self._llm.bind_tools(self._tools)
    
    def get_llm_binds_tools(self):
        return self._llm_binds_tools

    def get_tool_node(self):
        return self._tool_node

    def get_llm(self):
        return self._llm

