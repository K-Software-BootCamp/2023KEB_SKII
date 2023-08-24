import json
import psycopg2
from app.dbinfo import connect_db

# data =  contact_data('learning_table16', 'acc_00001')

def contact_raw_id(table, id):
    conn = connect_db(table)
    cur = conn.cursor()
    #SELECT * FROM public.learning_table_bearing1_1 WHERE csv_number IN (SELECT csv_number FROM public.learning_table_bearing1_1 WHERE id = 1);
    if id == 0:
        print('0 route')
        # query = f"SELECT * FROM public.{table}"
    else:
        print('non 0 route')
        query = f"SELECT * FROM public.{table} WHERE csv_number IN (SELECT csv_number FROM public.{table} WHERE id = {id})"

    # SQL 쿼리 실행
    cur.execute(query)

    # 결과 가져오기
    rows = cur.fetchall()
    column_names = [desc[0] for desc in cur.description]

    # 결과를 딕셔너리 형태로 변환
    data_as_dict = [dict(zip(column_names, row)) for row in rows]

    # 딕셔너리를 JSON 형태로 직렬화
    json_data = json.dumps(data_as_dict, indent=4)

    # 커서 및 연결 종료
    cur.close()

    return data_as_dict
