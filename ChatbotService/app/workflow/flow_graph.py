from app.nodes.nodes import Nodes
from langgraph.graph import StateGraph, END
from app.models.state import State

class FlowGraph:
    def __init__(self):
        nodes = Nodes()
        graph_builder = StateGraph(state_schema=State)

        # Add các node
        graph_builder.add_node("router", nodes.route)
        graph_builder.add_node("tools", nodes.using_tools)
        # graph_builder.add_node("write_query", nodes.write_query)
        graph_builder.add_node("call_model", nodes.call_model)

        # Set entry point
        graph_builder.set_entry_point("router") 

        # Định nghĩa các conditional edges từ router
        graph_builder.add_conditional_edges(
            "router",
            lambda state: state["next_state"],
            {
                "tools": "tools",
                "generate": "call_model"
            }
        )

        # Định nghĩa các edges khác không liên quan đến router
        graph_builder.add_conditional_edges(
            "tools",
            lambda x: x["next_state"],
            {
                "useful": END,
                "generate": "call_model"
            }
        )
        graph_builder.add_edge("call_model", END)

        self._graph = graph_builder.compile()

    def get_graph(self):
        return self._graph.get_graph().draw_mermaid()

    def run(self, question: str, thread_id: str, user_id: str):
        initial_state: State = {
            "question": question,
            "language": "Vietnamese",
            "next_state": "generate",
            "messages": [],
            "thread_id": thread_id,
            "user_id": user_id
        }
        return self._graph.invoke(initial_state)
