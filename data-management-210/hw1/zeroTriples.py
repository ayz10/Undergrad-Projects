def strToInt(list):
    n = len(list)
    for i in range(n):
        list[i]= int(list[i])

def zeroTriplets(list, n):
    strToInt(list)
    arr = []
    tripleCount = 0
    found = False
    for i in range(0, n-2):
        for j in range(i+1, n-1):
            for k in range(j+1, n):
                if (list[i] + list[j] + list[k] == 0):
                    tripleCount += 1
                    found = True
    
    if (found == False):
        print(str(tripleCount)+" triples found")
        return
    if tripleCount == 1:
        print(str(tripleCount)+" triple found:")
    else:
        print(str(tripleCount)+" triples found:")
    for i in range(0, n-2):
        for j in range(i+1, n-1):
            for k in range(j+1, n):
                if (list[i] + list[j] + list[k] == 0):
                    print(list[i], list[j], list[k])

if __name__ == "__main__" :
    list = []
    num = None
    while True:
        num = input()
        if num == '-12345':
            break  
        else:
            list.append(num)
    n = len(list)
    zeroTriplets(list, n)
    