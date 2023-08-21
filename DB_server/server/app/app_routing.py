from fastapi import FastAPI, HTTPException
from app.outputdb import contact_raw_id
from app.output_pred import contact_pred_id
from app.insert_json_data_append import insert_json_data
from app.request_infer import send_request_and_get_response, send_request_and_get_response_table
from app.db_row_count import row_count
import asyncio
import requests
from fastapi import BackgroundTasks

from app.request_infer import send_request_and_get_response


route = FastAPI()

# Define a global flag to control the loop
stop_flag = False


def fetch_and_insert_data(load_cnt, table, table_name):
    try:
        # response.raise_for_status()  # Check for HTTP request errors
        response_data = send_request_and_get_response_table(table, load_cnt)
        insert_json_data(table_name, response_data)
    except requests.exceptions.HTTPError as http_err:
        print(f"HTTP error for load_cnt={load_cnt}: {http_err}")
    except ValueError as json_err:
        print(f"JSON parsing error for load_cnt={load_cnt}: {json_err}")

async def run_fetch_and_insert_data(max_load_cnt, table, table_name):
    # Sequentially process load_cnt values
    for load_cnt in range(5, max_load_cnt+1):
        if stop_flag:
            break
            
        fetch_and_insert_data(load_cnt, table, table_name)
        
        # Add a delay between iterations (e.g., 10 seconds)
        await asyncio.sleep(1)

@route.get("/data/{table}/{id}")
async def get_data_request(table: str=None, id: int=None):
    try:
        result = contact_raw_id(table, id)
        return result
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@route.get("/output/{table}/{pred_id}")
async def get_data_request(table: str=None, pred_id: int=None):
    try:
        result = contact_pred_id(table, pred_id)
        return result
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
    
@route.get("/request/{table}/all")
async def send_infer_all(table: str = None, background_tasks: BackgroundTasks = BackgroundTasks()):
    table_name = "prediction_"+table
    print(table_name)
    try:
        rows = row_count(table)
        rows_per_load_cnt = 2560   
        max_load_cnt = int(rows / rows_per_load_cnt)
        print(max_load_cnt)

        # Start background tasks
        background_tasks.add_task(run_fetch_and_insert_data, max_load_cnt, table, table_name)
        
        return {"message": "Data requests started."}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
    
@route.get("/request/{table}/{id}")
async def send_infer(table: str=None, id: int=None):
    table_name = "prediction_"+table
    print("request table_name ") 
    print(table_name)
    try:
        response_data = send_request_and_get_response(table, id)
        print("response works")
        insert_json_data(table_name, response_data)
        
        return response_data
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
# Define an endpoint to stop the loop
@route.post("/stop_loop")
async def stop_loop():
    global stop_flag
    stop_flag = True
    return {"message": "Loop will stop."}