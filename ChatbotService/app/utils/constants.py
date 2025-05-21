from typing import Dict, List

# API Constants
API_PREFIX = "/api"
API_VERSION = "v1"

# Chat Constants
MAX_HISTORY_LENGTH = 10  # Số lượng tin nhắn tối đa trong lịch sử
DEFAULT_LANGUAGE = "vi"

# Error Messages
ERROR_MESSAGES = {
    "invalid_messages": "Tin nhắn không hợp lệ",
    "invalid_language": "Ngôn ngữ không được hỗ trợ",
    "model_error": "Lỗi khi tạo phản hồi từ model",
    "server_error": "Lỗi server, vui lòng thử lại sau"
}

# Travel Destinations
POPULAR_DESTINATIONS: Dict[str, List[str]] = {
    "north": ["Hà Nội", "Sa Pa", "Hạ Long", "Ninh Bình", "Mộc Châu"],
    "central": ["Huế", "Đà Nẵng", "Hội An", "Nha Trang", "Đà Lạt"],
    "south": ["TP.HCM", "Phú Quốc", "Cần Thơ", "Vũng Tàu", "Mũi Né"]
}

# Price Ranges (VND)
PRICE_RANGES = {
    "budget": "500.000-1.000.000",
    "mid_range": "1.000.000-2.000.000",
    "luxury": "2.000.000-5.000.000"
} 