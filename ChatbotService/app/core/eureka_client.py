from py_eureka_client import eureka_client
import os
from dotenv import load_dotenv

load_dotenv()

async def register_to_eureka():
    await eureka_client.init_async(
        eureka_server=os.getenv("EUREKA_DEFAULT_ZONE"),
        app_name="chatbot-service",
        instance_port=1210
    )