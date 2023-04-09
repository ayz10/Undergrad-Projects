import sys

if __name__ == "__main__" :
    list = []
    input = sys.argv[1]
    with open(input, "r") as f:
        for line in f:
            for word in line.split():
                list.append(word)
    print(len(list))