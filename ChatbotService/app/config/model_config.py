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
        self._model_name = "qwen/qwen3-235b-a22b:free"
        self._provider = "openai"
        self._temperature = 0
    
    def llm(self):
        return init_chat_model(
            model=self._model_name, 
            model_provider=self._provider,
            openai_api_base="https://openrouter.ai/api/v1",
            openai_api_key=os.getenv("OPENAI_API_KEY")
        )


class Gemini:
    def __init__(self):
        self._model_name = "models/gemini-2.5-flash-preview-04-17"
        self._provider = "google_genai"
        self._temperature = 0
    
    def llm(self):
        return init_chat_model(
            model=self._model_name, 
            model_provider=self._provider,
        )