from typing import List, Optional
from app.models.chat import Message

def validate_messages(messages: Optional[List[Message]] = None) -> bool:
    """
    Kiểm tra tính hợp lệ của danh sách tin nhắn
    """
    if not messages:
        return True
        
    valid_roles = {"human", "assistant", "system"}
    
    for message in messages:
        if message.role not in valid_roles:
            return False
        if not message.content or not isinstance(message.content, str):
            return False
    
    return True

def validate_language(language: str) -> bool:
    """
    Kiểm tra mã ngôn ngữ
    """
    valid_languages = {"vi", "en"}  # Có thể mở rộng thêm
    return language.lower() in valid_languages 