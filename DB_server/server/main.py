import uvicorn
from app.app_routing import route  # Import the "app" instance from the app_routing module
# from app import app_routing


if __name__ == "__main__":
    uvicorn.run(route, host="0.0.0.0", port=28000)
