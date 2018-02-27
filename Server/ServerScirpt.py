import mysql.connector
import socket
import time
import datetime
import json

#Runs SQL query then loads to json then sends it using socket

def main():
    conn = mysql.connector.connect(host='localhost',database='BarDB',user='root',password='pwd')
    cursor = conn.cursor()
    while True:
        time.sleep(1)
        current_time = datetime.datetime.now()
        current_time_minus_five = current_time - datetime.timedelta(days=10)
        query = 'SELECT name FROM Recipe where recipeID in (SELECT recipeID FROM Transactions WHERE datetime BETWEEN \"' + current_time_minus_five.strftime('%Y-%m-%d %H:%M:%S') +'\" AND \"' + current_time.strftime('%Y-%m-%d %H:%M:%S') + '\")'
        print(query)
        cursor.execute(query)
        for name in cursor:
             print(name[0])
             data_string = json.loads(name[0]) #not working yet

    



    """HOST = ''                 # Symbolic name meaning the local host
    PORT = 50007              # Arbitrary non-privileged port
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((HOST, PORT))
    s.listen(1)
    conn, addr = s.accept()
    print ('Connected by', addr)
    while 1:
        data = conn.recv(1024)
        if not data: break
        conn.send(data)"""
    conn.close()




if __name__ == "__main__":
    main()