import json
import psycopg2

def connect_db(Database):
    with open(".env/id.txt", "r") as f:
        user = f.read()
    with open(".env/pw.txt", "r") as f:
        password = f.read()
    conn = psycopg2.connect(
        host="engineer.i4624.tk",  # Server
        database='factory',  # User & Default database
        user=user,  # User & Default database
        password=password,  # Password
        port=50132)  # Port
    # Call the function to create the table if it doesn't exist
    create_table_if_not_exists(conn, Database)
    return conn

def create_table_if_not_exists(conn, name):
    cur = conn.cursor()
    query = f"SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = '{name}')"
    # Check if the table exists
    cur.execute(query)
    table_exists = cur.fetchone()[0]

    if not table_exists:
        print(f"Table {name} does not exists.")
        maketable(conn, name)
    cur.close()

def maketable(conn, name):
    # 데이터베이스 연결
    cur = conn.cursor()

    # 테이블 생성
    table_name = name

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
    conn.commit()
    cur.close()