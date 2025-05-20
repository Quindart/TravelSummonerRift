from typing import List
from typing_extensions import TypedDict
from app.models.message import Message

class State(TypedDict):
    question: str
    language: str
    next_state: str
    thread_id: str
    user_id: str
    messages: List[Message]