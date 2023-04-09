import sys

if __name__ == "__main__" :
    list = []
    input = sys.argv[1]
    with open(input, "r") as f:
        for line in f:
            for word in line.split():
                list.append(word)
    list.sort(key = lambda x:x.lower())
    for x in list:
        print(x)
