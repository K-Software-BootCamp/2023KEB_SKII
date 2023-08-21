import datetime
from fastapi import FastAPI, UploadFile
from fastapi.responses import FileResponse
from app.cnn_lstm import CNN_LSTM_FP, infer_model, series_infer
from app.data import load_data_from_pfile, PHMTestDataset_Sequential
from app.file_utils import save_results, save_uploaded_file
from app.db_connection import connect_db, insert_data, disconnect_db 
from app.test_dataset_preparation import load_data
import torch
import matplotlib.pyplot as plt
from pydantic import BaseModel
import os
import json

route = FastAPI()

def dbworks(csvfile_path, current_time):
    conn = connect_db()
    insert_data(conn, csvfile_path, current_time)

@route.post("/modelpredict")
async def modelpredict(file: UploadFile, modelfile:UploadFile):
    # Read the file contents
    file_path = save_uploaded_file(file)
    model_path = save_uploaded_file(modelfile)
    # Load the data from the file
    sample_data = load_data_from_pfile(file_path)

    # Load the PyTorch model
    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    model = CNN_LSTM_FP().to(device)

    '''
    need to using 'modelfile' to uploaded model
    '''
    model.load_state_dict(torch.load(model_path, map_location=device))

    # Do the inference + timestamp result
    results = infer_model(model, file_path, device)
    results['timestamps'] = sample_data['timestamps']
    results['timestamps'] = [ts.strftime('%H:%M:%S') for ts in results['timestamps']]

    current_time = datetime.datetime.now().replace(microsecond=0)
    # Plotting and saving
    fig, ax = plt.subplots(nrows=1, ncols=1, figsize=[10, 10])
    ax.scatter(range(len(results['predictions'])), results['predictions'], c='b', marker='.', label='predictions') # type: ignore
    ax.set_title(f'Model File: {modelfile.filename}')
    ax.annotate(f"Inference File: {file.filename}",
            xy=(0.5, 1.1), xycoords="axes fraction", 
            ha="center", fontsize=10)
    ax.legend()
    
    filename = 'inf_result'
    jsonfile_path, csvfile_path = save_results(results, filename, fig,current_time)
    # Load the prediction results from the original JSON file

    dbworks(csvfile_path, current_time)
    
    return FileResponse(
        path=jsonfile_path,
        media_type='application/json'
    )



@route.get("/predict/")
async def seriesPredict(table: str, load_cnt: int):
    
    
    if load_cnt < 6:
        for i in range(1,5):
            print("load count below 5")
            print(i)
            data = load_data(table, i)  
        load_cnt = 5
    
    data = load_data(table, load_cnt)
    
    # delete tmp pkz
    if load_cnt > 7:
        pkz_file = os.path.join('static', f'{load_cnt-2}_tmp_bearing.pkz')
        os.remove(pkz_file)

    # Load the PyTorch model
    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    model = CNN_LSTM_FP().to(device)
    model.load_state_dict(torch.load('../model/weight/cnn_lstm_final.pth', map_location=device))

    # Do the inference
    results = series_infer(model, device, data)
    results['timestamps'] = data['timestamps']
    results['timestamps'] = [ts.strftime('%H:%M:%S') for ts in results['timestamps']]
    
     # Get the last timestamp and prediction
    last_timestamp = data['timestamps'][-1].strftime('%H:%M:%S')
    last_prediction = results['predictions'][0]

    infer_time = datetime.datetime.now().replace(microsecond=0)
    print("results all")
    print(results['timestamps'])
    print(results['predictions'])
    
    
    
    output_result = {'timestamp': last_timestamp, 'prediction': last_prediction}

    print(output_result)

    return output_result
    
    # # Plotting and saving
    # fig, ax = plt.subplots(nrows=1, ncols=1, figsize=[10, 10])
    # ax.scatter(range(len(results['predictions'])), results['predictions'], c='b', marker='.', label='predictions') # type: ignore
    # ax.legend()
    # filename = 'inf_result'
    
    # jsonfile_path, csvfile_path = save_results(results, filename, fig, infer_time) ## folder input fix required 
    # # Load the prediction results from the original JSON file

    
    # return FileResponse(
    #     path=jsonfile_path,
    #     media_type='application/json'
    # )