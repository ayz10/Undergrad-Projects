import socket

def client():
    try:
        cs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        print("[C]: Client socket created")
    except socket.error as err:
        print('socket open error: {} \n'.format(err))
        exit()
        
    # Define the port on which you want to connect to the server
    port = 50007
    localhost_addr = socket.gethostbyname(socket.gethostname())

    # connect to the server on local machine
    server_binding = (localhost_addr, port)
    cs.connect(server_binding)

    # read input file
    file1 = open('in-proj.txt', 'r')
    Lines = file1.readlines()
    count = 0

    # send a intro message to the server.  
    for line in Lines:
        count += 1
        line = line.rstrip('\n')[::-1]
        cs.send(line.encode('utf-8'))
    

    # close the client socket
    cs.close()
    exit()

if __name__ == "__main__":
    # t1 = threading.Thread(name='server', target=server)
    # t1.start()

    # # time.sleep(random.random() * 5)
    # t2 = threading.Thread(name='client', target=client)
    # t2.start()

    client()
    # time.sleep(5)
    print("Done.")
