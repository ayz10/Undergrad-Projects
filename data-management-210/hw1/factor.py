import sys

def printFactors(n):
    arr = []
    for i in range(2, n + 1):
        while n % i == 0:
            n = n//i
            arr.append(i)
    for x in arr:
        print(x, end = ' ')

if __name__ == "__main__" :
    n = int(sys.argv[1])
    printFactors(n)