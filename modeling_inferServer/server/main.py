import uvicorn
from app.app_routing import route  # Import the "app" instance from the app_routing module

## background running uvicorn --reload main:__main__ --port 48000 --host 0.0.0.0 
if __name__ == "__main__":
    uvicorn.run(route, host="0.0.0.0", port=48000)
