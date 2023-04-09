def sort(list):
    n = len(list)
    for i in range(n):
        list[i]= int(list[i])
    list.sort()

def mean(list):
    sum = 0
    n = len(list)
    for i in range(0, n):
        sum += int(list[i])
    if n!=0:
        mean = sum/n
    return mean

def median(list):
    sort(list)
    median = 0
    n = len(list)
    if n!=0:
        if n%2 == 0:
            left = list[int(n/2)-1]
            right = list[int((n/2))]
            median = (int(left)+int(right))/2
        else:
            median = list[n//2]
    return median

def stdDev(list):
    mu = mean(list)
    sum = 0
    n = len(list)
    for i in range(0, n):
        sum += (pow(int(list[i])- mu, 2))
    if n!=0:
        radicand = sum/(n-1)
    stdDev = pow(radicand, 0.5)
    return stdDev

if __name__ == "__main__" :
    list = []
    num = None
    while True:
        num = input()
        if num == '-12345':
            break  
        else:
            list.append(num)
    
    print("mean: " + str(mean(list)))
    print("median: " + str(median(list)))
    print("standard deviation: " + str(stdDev(list)))
        
