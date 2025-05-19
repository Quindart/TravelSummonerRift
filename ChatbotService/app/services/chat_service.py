from app.workflow.flow_graph import FlowGraph
from app.models.chat_request import ChatRequest
from fastapi import HTTPException


flow_graph = FlowGraph()

async def root():
    print(flow_graph.get_graph())
    return { "message": "Hello, World!" }

async def chat(request: ChatRequest):
    try:
        result = flow_graph.run(request.question, request.thread_id, request.user_id)
        return result
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
