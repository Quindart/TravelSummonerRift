from fastapi import FastAPI
from dotenv import load_dotenv
import os
from app.api.chat import chat_router
from fastapi.middleware.cors import CORSMiddleware
from app.core.eureka_client import register_to_eureka


# Load environment variables
load_dotenv()

app = FastAPI(
    title="Travel Chatbot API",
    description="API for travel chatbot using LangChain and Mistral AI",
    version="1.0.0",
    docs_url="/docs",           # <== Đổi URL Swagger UI
    redoc_url=None,                             # (tuỳ chọn) tắt Redoc nếu không dùng
    openapi_url="/openapi.json", # <== Đổi URL OpenAPI schema,
    root_path="/api/v1/chatbot-service"   # << Đây là phần quan trọng
)

@app.on_event("startup")
async def start_up_event():
    # await register_to_eureka()
    print("Starting up the application...")


app.include_router(chat_router)


if __name__ == "__main__":
    import uvicorn
    uvicorn.run("app.main:app", host="0.0.0.0", port=1210, reload=True) 

# uvicorn app.main:app --reload --port 1210