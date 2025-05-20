from app.database import PostgresStore
# from langchain_community.tools import BraveSearch
from dotenv import load_dotenv
import os
from langchain.schema.document import Document
import json
import requests
from langchain_core.tools import tool
from langgraph.prebuilt import ToolNode
from app.config.model_config import Mistral, OpenAI, Gemini
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

        # @tool
        # def query_tour(state: State):
        #     """Write a query to the database to get tour information."""
        #     prompt = self._prompt.query_prompt.format(
        #         dialect=self._db.dialect,
        #         top_k=10,
        #         table_info=self._db.get_table_info(),
        #         input=state["question"],
        #         user_id=state["user_id"]
        #     )
        #     structured_llm = self._llm.with_structured_output(QueryOutput)
        #     result = structured_llm.invoke(prompt)

        #     state["query"] = result["query"]
        #     print("query", state["query"])
        #     # thực hiện query
        #     result = self._db.run(result["query"])
        #     # print("result", result)

        #     state["result"] = result

        #     return result

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

        @tool
        def get_available_tours(state: State):
            """
            Lấy danh sách các tour đang hoạt động, còn hiệu lực (start_date > CURRENT_DATE),
            kèm thông tin danh mục.
            """
            print("statehehee", state)
            query = """
                SELECT 
                    t.tour_id, 
                    t.is_active, 
                    t.description, 
                    t.duration, 
                    t.name, 
                    t.price, 
                    t.thumbnail, 
                    ct.name AS category_name
                FROM 
                    tours t
                JOIN 
                    category_tours ct ON t.category_tour_id = ct.category_tour_id
                JOIN 
                    tour_schedules ts ON t.tour_id = ts.tour_id
                WHERE 
                    t.is_active = TRUE
                    AND ts.start_date > CURRENT_DATE
                ORDER BY 
                    t.created_at DESC
            """

            result = self._db.run(query)
            print("Available tours:", result)
            return result

        @tool
        def get_user_info(state: State):
            """
            Lấy thông tin cơ bản của người dùng theo user_id.
            Chỉ bao gồm các trường cần thiết để hiển thị hồ sơ.
            """
            print("state", state)
            user_id = state.get("user_id")
            if not user_id:
                raise ValueError("Thiếu user_id trong state")

            query = f"""
                SELECT 
                    user_id,
                    full_name,
                    email,
                    phone,
                    avatar_url,
                    gender,
                    birthday
                FROM 
                    users
                WHERE 
                    user_id = '{user_id}';
            """
            result = self._db.run(query)
            return result

        @tool
        def get_user_booked_tours(state: State):
            """
            Lấy danh sách các tour mà người dùng đã đặt, kèm theo thông tin lịch trình tour.
            """
            user_id = state.get("user_id")
            print("user_id", user_id)
            if not user_id:
                raise ValueError("Thiếu user_id trong state")

            query = f"""
                SELECT 
                    b.booking_id,
                    b.status,
                    b.created_at,
                    b.total_price,
                    ts.name AS tour_name,
                    ts.tour_id
                    ts.start_date,
                    ts.end_date,
                    ts.adult_price,
                    ts.child_price,
                    ts.baby_price
                FROM 
                    bookings b
                JOIN 
                    tour_schedules ts ON b.tour_schedule_id = ts.tour_schedule_id
                WHERE 
                    b.user_id = '{user_id}'
                    AND b.is_active = TRUE
                    AND ts.is_active = TRUE
                ORDER BY 
                    ts.start_date DESC;
            """
            result = self._db.run(query)
            return result
    
        self._tools = [get_available_tours, get_user_info, get_user_booked_tours]
        self._tool_node = ToolNode(self._tools)

        self._llm_binds_tools = self._llm.bind_tools(self._tools)
    
    def get_llm_binds_tools(self):
        return self._llm_binds_tools

    def get_tool_node(self):
        return self._tool_node

    def get_llm(self):
        return self._llm

