import json
import psycopg2
from app.dbinfo import connect_db

def contact_pred_id(table, pred_id):
    conn = connect_db(table)
    cur = conn.cursor()
    #SELECT * FROM public.
    query = f"SELECT * FROM public.{table} WHERE pred_id = {pred_id}"

    # SQL 쿼리 실행
    cur.execute(query)

    # 결과 가져오기
    rows = cur.fetchall()
    column_names = [desc[0] for desc in cur.description]

    # 결과를 딕셔너리 형태로 변환
    data_as_dict = [dict(zip(column_names, row)) for row in rows]


    # 커서 및 연결 종료
    cur.close()

    return data_as_dict