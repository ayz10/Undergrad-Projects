import sys

def incDigits(x):
    count = 0
    for i in range(1, x+1):
        number = str(i)
        digits = []
        for digit in number:
            digits.append(str(digit))
        for j in range(0, len(digits)):
            if len(digits) == 1:
                count+=1
                break
            if 0 <= j+1 < len(digits):
                if digits[j]>=digits[j+1]:
                    break
                count += 1
    print(count)

if __name__ == "__main__" :
    n = int(sys.argv[1])
    incDigits(n)
