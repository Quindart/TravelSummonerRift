from pydantic_settings import BaseSettings
from typing import Optional

class Settings(BaseSettings):
    # API Keys
    MISTRAL_API_KEY: str
    OPENAI_API_KEY: Optional[str] = None
    
    # Model Settings
    DEFAULT_MODEL: str = "mistral-large-latest"
    MODEL_PROVIDER: str = "mistralai"
    
    # API Settings
    API_V1_STR: str = "/api/v1"
    
    # Database Settings
    DATABASE_URL: str = "postgresql+psycopg2://zy:npg_VeClRkTZ28fS@ep-little-block-a1gvm7lg-pooler.ap-southeast-1.aws.neon.tech:5432/tourdb"
    
    class Config:
        env_file = ".env"
        case_sensitive = True

settings = Settings() 