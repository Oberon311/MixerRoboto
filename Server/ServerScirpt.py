import mysql.connector
import socket
import time
import sys
import datetime
import json

#Runs SQL query then loads to json then sends it using socket

def main():
    conn = mysql.connector.connect(host='localhost',database='BarDB',user='root',password='mixerroboto')
    cursor = conn.cursor()
    TCP_IP = socket.gethostname()
    TCP_PORT = 5000
    BUFFER_SIZE = 1024  # Normally 1024, but we want fast response
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((TCP_IP, TCP_PORT))
    data_string = "SQL return NULL"



    
    while True:
        time.sleep(1)
        current_time = datetime.datetime.now()
        current_time_minus_five = current_time - datetime.timedelta(days=30)
        query = 'SELECT name FROM Recipe where recipeID in (SELECT recipeID FROM Transactions WHERE datetime BETWEEN \"' + current_time_minus_five.strftime('%Y-%m-%d %H:%M:%S') +'\" AND \"' + current_time.strftime('%Y-%m-%d %H:%M:%S') + '\")'
        cursor.execute(query)
        data = {}
        for name in cursor:
            if 'name' in data:
               data['name'] = data['name'] + ' , ' +  name[0]
            else:
                data['name'] = name[0]
            data_string = json.dumps(data)
            parsed = json.loads(data_string)
            #print json.dumps(parsed, indent=2, sort_keys = True)
        s.send(data_string)
        if not s.recv(BUFFER_SIZE): 
            print("Pi did not confirm message") 
            break
        #print "received data:", data


    




    conn.close()
    s.close()



if __name__ == "__main__":
    main()