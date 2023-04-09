import sys

if __name__ == "__main__" :
    list = []
    input = sys.argv[1]
    with open(input, "r") as f:
        for line in f:
            for word in line.split():
                list.append(word)
    unique = set()
    for x in list:
        y = x.lower()
        unique.add(y)
    print(len(unique))