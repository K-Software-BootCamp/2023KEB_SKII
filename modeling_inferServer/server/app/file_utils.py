# file_utils.py

import os
import csv
import json
import tempfile
import datetime

def save_uploaded_file(file):
    """
    Save the uploaded file to a temporary directory.
    Args:
        file: The uploaded file.
    Returns:
        The path to the saved file.
    """
    filename = file.filename
    file_path = os.path.join(tempfile.gettempdir(), filename)
    with open(file_path, 'wb') as f:
        f.write(file.file.read())
    return file_path

def save_results(results, file_name, fig, current_time):
    time = current_time.strftime('%Y-%m-%d-%H-%M-%S')
    
    # Create a list of tuples for the aligned data
    aligned_data = list(zip(results['predictions'], results['timestamps']))
    
    # Save the aligned data to a CSV file
    with open(os.path.join('results/file', file_name + time + '.csv'), 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['predictions', 'timestamps'])  # Write the header
        writer.writerows(aligned_data)  # Write the aligned data
    
    # Save the results to a JSON file
    with open(os.path.join('results/file', file_name + time + '.json'), 'w') as jsonfile:
        json.dump(results, jsonfile)

    csvfile_path = os.path.join('results/file', file_name + time + '.csv')
    jsonfile_path = os.path.join('results/file', file_name + time + '.json')
    # Save the plot image
    fig_bytes = fig.savefig(os.path.join('results/plots', time + '.png'), format='png')
    return jsonfile_path, csvfile_path

# def convert_csv(converted_data , csv_filename):
#     with open(csv_filename, "r") as csv_file:
#     csv_reader = csv.reader(csv_file)
#     headers = next(csv_reader)  # Get the header row
    
#     for values in zip(*csv_reader):  # Transpose the data
#         converted_data.append(values)

#     # Write the transformed data to a new CSV file
#     with open(output_filename, "w", newline="") as output_file:
#         csv_writer = csv.writer(output_file)
#         csv_writer.writerow(headers)  # Write the headers
#         csv_writer.writerows(converted_data)