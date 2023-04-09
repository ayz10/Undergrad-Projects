import sys

def isPrime(n):
    if n <= 1 :
        return False
    for i in range(2, n):
        if n % i == 0:
            return False
    return True
 
def printPrime(n):
    for i in range(2, n + 1):
        if isPrime(i):
            print(i, end = " ")
 
# Driver code
if __name__ == "__main__" :
    n = int(sys.argv[1])
    printPrime(n)