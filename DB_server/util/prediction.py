import psycopg2
import json
import os


with open("server/.env/id.txt", "r") as f:
    user = f.read()

with open("server/.env/pw.txt", "r") as f:
    password = f.read()

conn = psycopg2.connect(
    host="engineer.i4624.tk",
    database="factory",
    user=user,
    password=password,
    port=50132,
)
# JSON 데이터
json_data = {
    'inference_time': '09:02:10',
    'prediction': 0,
    'timestamp': '08:33:01'
}

# 데이터베이스 연결
cur = conn.cursor()

# 테이블 생성
table_name = 'prediction_table_ex'

# 테이블 생성 구문
create_table_sql = f'''
    CREATE TABLE IF NOT EXISTS {table_name} (
        pred_id serial PRIMARY KEY,
        inference_time varchar,
        prediction float,
        timestamp varchar
    );
'''
cur.execute(create_table_sql)

# JSON 데이터 파싱 및 데이터 삽입
inference_time = json_data["inference_time"]
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

# 연결 종료
cur.close()
conn.close()

print(f"Data with pred_id {pred_id} has been imported into the table.")