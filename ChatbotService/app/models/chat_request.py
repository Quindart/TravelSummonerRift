from pydantic import BaseModel

class ChatRequest(BaseModel):
    thread_id: str
    question: str
    user_id: str
