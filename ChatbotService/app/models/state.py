from typing import List
from typing_extensions import TypedDict


class State(TypedDict):
    question: str
    query: str
    result: str
    answer: str
    language: str
    next_state: str
    thread_id: str
    user_id: str
    messages: List[any]