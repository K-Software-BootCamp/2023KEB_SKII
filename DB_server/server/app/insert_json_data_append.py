import datetime

from app.dbinfo import connect_db, create_table_if_not_exists


def insert_json_data(table_name, json_data):
    conn = connect_db(table_name)
    cur = conn.cursor()

    inference_time = datetime.datetime.now().replace(microsecond=0)
    prediction = json_data["prediction"]
    timestamp = json_data["timestamp"]

    insert_sql = f'''
        INSERT INTO {table_name} (inference_time, prediction, timestamp) 
        VALUES (%s, %s, %s)
        RETURNING pred_id;
    '''
    cur.execute(insert_sql, (inference_time, prediction, timestamp))
    pred_id = cur.fetchone()[0]
    conn.commit()

    cur.close()

    return pred_id

# def main():
#     conn = connect_db()

#     table_name = 'prediction_table_ex'
#     create_table_if_not_exists(conn, table_name)

#     json_data = {
#         'inference_time': '09:02:20',
#         'prediction': 0.12748925,
#         'timestamp': '08:33:11'
#     }

#     pred_id = insert_json_data(conn, table_name, json_data)
    
#     conn.close()

#     print(f"Data with pred_id {pred_id} has been imported into the table.")
