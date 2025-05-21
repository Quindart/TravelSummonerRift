# Travel Chatbot Service

Chatbot hướng dẫn du lịch thông minh sử dụng FastAPI và LangChain.

## Tính năng

- Chatbot hướng dẫn du lịch bằng tiếng Việt
- Sử dụng Mistral AI để tạo phản hồi thông minh
- API RESTful với FastAPI
- Hỗ trợ đa luồng chat

## Cài đặt

1. Clone repository:
```bash
git clone <repository-url>
cd ChatbotService
```

2. Tạo môi trường ảo:
```bash
python -m venv .venv
source .venv/bin/activate  # Linux/Mac
# HOẶC
.venv\Scripts\activate  # Windows
```

3. Cài đặt dependencies:
```bash
pip install -r requirements.txt
```

4. Tạo file .env:
```bash
cp .env.example .env
```
Sau đó cập nhật API keys trong file .env

## Chạy ứng dụng

1. Chạy server development:
```bash
uvicorn app.main:app --reload
```

2. Truy cập API docs tại:
```
http://localhost:8000/docs
```

## API Endpoints

- POST `/api/chat`: Endpoint chính để tương tác với chatbot
  - Request body:
    ```json
    {
      "question": "string",
      "messages": [
        {
          "role": "string",
          "content": "string"
        }
      ],
      "thread_id": "string",
      "language": "string"
    }
    ```
  - Response:
    ```json
    {
      "answer": "string",
      "thread_id": "string"
    }
    ```

## Cấu trúc Project

```
.
├── app/
│   ├── api/
│   │   └── routes.py
│   ├── core/
│   │   └── config.py
│   ├── models/
│   │   └── chat.py
│   ├── services/
│   │   └── chat_service.py
│   └── main.py
├── tests/
├── .env
├── .env.example
├── requirements.txt
└── README.md
``` 