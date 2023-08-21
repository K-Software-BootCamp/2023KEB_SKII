import requests

def send_request_and_get_response(table, load_cnt):
    url = f"https://rs.i4624.tk/predict/?table={table}&load_cnt={load_cnt}"
    response = requests.get(url)
    print(response)
    return response.json()  


def send_request_and_get_response_table(table, load_cnt):
    
    
    url = f"https://rs.i4624.tk/predict/?table={table}&load_cnt={load_cnt}"
    response = requests.get(url)
    print(response)
    return response.json()  
