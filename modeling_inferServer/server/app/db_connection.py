import datetime
import json
import csv
import psycopg2


with open("../server/env/id.txt", "r") as f:
    user = f.read()

    
with open("../server/env/pw.txt", "r") as f:
    password = f.read()

def connect_db():
    conn = psycopg2.connect(
        host="engineer.i4624.tk", # Server
        database="factory", # User & Default database
        user=user, # User & Default database
        password=password,  # Password
        port=50132 )# Port
    # Call the function to create the table if it doesn't exist
    create_table_if_not_exists(conn)
    return conn
    

def create_table_if_not_exists(conn):
    cur = conn.cursor()

    # Check if the table exists
    cur.execute("SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'rul_result1')")
    table_exists = cur.fetchone()[0]

    if not table_exists:
        # Create the table
        cur.execute("""CREATE TABLE rul_result2 (
            pred_id serial PRIMARY KEY,  
            infer_time timestamp,
            prediction float,
            timestamp time
        );
        """)
        conn.commit()
        print("Table 'rul_result1' created successfully.")
    else:
        print("Table 'rul_result1' already exists.")

    cur.close()



def insert_data(conn, csv_path, current_time):
    
    
    cur = conn.cursor()  # cursor
    
    with open(csv_path, "r") as csv_file:
        csv_reader = csv.reader(csv_file)
        next(csv_reader)
            
        # Insert data into the database
        for row in csv_reader:
            prediction = row[0]  # Value from the first column
            timestamp = row[1]  # Value from the second column
            query = "INSERT INTO RUL_result1 (infer_time, prediction, timestamp) VALUES (%s, %s, %s)"
            cur.execute(query, (current_time, prediction, timestamp))
            conn.commit()
            
    cur.close()


    
def disconnect_db(conn):
    conn.close()
    
## using this to select 
# query = "SELECT * FROM test_return WHERE id = %s"
# cur.execute(query, (desired_id,))
# result = cur.fetchone()