import os
from dotenv import load_dotenv
from langchain.chat_models import init_chat_model


load_dotenv()

class Mistral:
    def __init__(self):
        self._model_name = "mistral-large-latest"
        self._provider = "mistralai"
        self._temperature = 0
    
    def llm(self):
        return init_chat_model(
            model=self._model_name, 
            model_provider=self._provider, 
        )

class OpenAI:
    def __init__(self):
        self._model_name = "gpt-4o-mini"
        self._provider = "openai"
        self._temperature = 0
    
    def llm(self):
        return init_chat_model(
            model=self._model_name, 
            model_provider=self._provider, 
        )