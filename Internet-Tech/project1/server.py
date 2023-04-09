import socket

def server():
    try:
        ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        print("[S]: Server socket created")
    except socket.error as err:
        print('socket open error: {}\n'.format(err))
        exit()

    server_binding = ('', 50007)
    ss.bind(server_binding)
    ss.listen(1)
    host = socket.gethostname()
    print("[S]: Server host name is {}".format(host))
    localhost_ip = (socket.gethostbyname(host))
    print("[S]: Server IP address is {}".format(localhost_ip))
    csockid, addr = ss.accept()
    print ("[S]: Got a connection request from a client at {}".format(addr))

    # Receive data from the client

    # data_from_server=csockid.recv(100)
    # print(format(data_from_server.decode('utf-8')))
    file1 = open('out-proj.txt', 'w')
    while True:
        data_from_server=csockid.recv(100)
        if data_from_server:
            data_from_server = data_from_server.decode('utf-8')
            file1.write(data_from_server)
        else: 
            break

    # Close the server socket
    ss.close()
    exit()

if __name__ == "__main__":
    # t1 = threading.Thread(name='server', target=server)
    # t1.start()
    server()

    # time.sleep(5)
    print("Done.")
