import json
import psycopg2
from app.dbinfo import connect_db

def row_count(table):
    conn = connect_db(table)
    cur = conn.cursor()
    #SELECT * FROM public.
    query = f"SELECT COUNT(*) FROM {table}"

    # SQL 쿼리 실행
    cur.execute(query)

    # 결과 가져오기
    total_rows = cur.fetchone()[0]

    # 결과를 딕셔너리 형태로 변환


    # 커서 및 연결 종료
    cur.close()

    return total_rows
