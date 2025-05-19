from py_eureka_client import eureka_client

async def register_to_eureka():
    await eureka_client.init_async(
        eureka_server="http://localhost:8761/eureka/",
        app_name="chatbot-service",
        instance_port=1210
    )