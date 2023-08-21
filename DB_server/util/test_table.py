import psycopg2
import os
import csv

with open("./.env/id.txt", "r") as f:
    user = f.read()

with open("./.env/pw.txt", "r") as f:
    password = f.read()

conn = psycopg2.connect(
    host="engineer.i4624.tk",
    database="factory",
    user=user,
    password=password,
    port=50132,
)

# CSV 파일 디렉토리 설정
csv_directory = '/home/dbuser-4624/dataBase/ieee-phm-2012-data-challenge-dataset-master/Full_Test_Set/Bearing1_6'

# 열 이름 설정
column_names = ['hour', 'minutes', 'second', 'microsecond', 'horiz_accel', 'vert_accel']

# 데이터베이스 연결
cur = conn.cursor()

# 테이블 생성
table_name = 'Full_Test_table_bearing1_6'

# 테이블 생성 구문
create_table_sql = f'''
    CREATE TABLE IF NOT EXISTS {table_name} (
        id SERIAL PRIMARY KEY,
        hour integer,
        minutes integer,
        second integer,
        microsecond integer,
        horiz_accel float,
        vert_accel float,
        csv_number text
    );
'''
cur.execute(create_table_sql)

# 커밋
conn.commit()

# CSV 파일 읽기 및 데이터 삽입
csv_files = [f for f in os.listdir(csv_directory) if f.endswith('.csv')]

# 파일명 순서대로 정렬
csv_files.sort()

for file_num, csv_file in enumerate(csv_files, start=1):
    csv_file_name = os.path.splitext(csv_file)[0]  # 파일명에서 .csv 제외
    csv_file_path = os.path.join(csv_directory, csv_file)

    with open(csv_file_path, 'r') as file:
        reader = csv.reader(file)
        next(reader)  # 첫 번째 행은 건너뛰기 (컬럼 이름)

        # Batch inserts within a transaction
        rows_to_insert = []
        for idx, row in enumerate(reader, start=1):
            if len(row) == len(column_names):  # 데이터의 열 수가 일치하는 경우
                row.append(csv_file_name)  # csv_number 열에 파일명 추가
                row[3] = int(float(row[3]))  # '1.0004e+005'를 정수로 변환
                rows_to_insert.append(tuple(row))

        insert_sql = f'''
            INSERT INTO {table_name} (hour, minutes, second, microsecond, horiz_accel, vert_accel, csv_number) 
            VALUES (%s, %s, %s, %s, %s, %s, %s);
        '''
        cur.executemany(insert_sql, rows_to_insert)

    # Commit the transaction after each file is processed
    conn.commit()

    # Print progress
    print(f"Processed file {file_num} of {len(csv_files)}: {csv_file}")
    
# Commit the transaction after all files are processed
conn.commit()

# 연결 종료
cur.close()
conn.close()

print("CSV files have been imported into the table.")
