from pydantic import BaseModel
from typing import Any, Dict, Optional

class ResponseModel(BaseModel):
    data: Any
    status: int
    header: Dict[str, str]
    config: Optional[Dict[str, Any]] = None

    class Config:
        json_schema_extra = {
            "example": {
                "data": {},
                "status": 200,
                "header": {"Content-Type": "application/json"},
                "config": {"timeout": 30}
            }
        } 