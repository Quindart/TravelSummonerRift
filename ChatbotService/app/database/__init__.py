from dotenv import load_dotenv
import os
from langchain_community.utilities import SQLDatabase
from langchain_community.tools.sql_database.tool import QuerySQLDatabaseTool

load_dotenv()

class PostgresStore:
    def __init__(self):
        self._url = os.getenv("DATABASE_URL")
        self._postgres = SQLDatabase.from_uri(self._url)
        self._run = QuerySQLDatabaseTool(
            db=self._postgres,
            description="Use this tool to query the database"
        )
    def get_db(self):
        return self._postgres
    def run(self, query):
        return self._run.invoke(query)
