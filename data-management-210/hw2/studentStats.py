import sys
import numpy as np

def avgGPA(arr):
    gpaSum = 0.0
    i = 0
    while i <len(arr):
        gpaSum = gpaSum + float(arr[i][3])
        i = i + 1
    gpaAvg = gpaSum/len(arr)
    return gpaAvg

def maxCSGPA(arr):
    GPAList = []
    i = 0
    while i < len(arr):
        if arr[i][2] == 'CS':
            GPAList.append(arr[i][3])
        i = i+1
    GPAList.sort(reverse=True)
    return GPAList[0]

def over35(arr):
    studentList = []
    i = 0
    while i < len(arr):
        if float(arr[i][3]) > 3.5:
            studentList.append(arr[i][0])
        i = i+1
    return len(studentList)

def over25(arr):
    GPAList = []
    GPAsum = 0.0
    i = 0
    while i < len(arr):
        if int(arr[i][1]) >= 25:
            GPAList.append(float(arr[i][3]))
        i = i+1
    for x in GPAList:
        GPAsum = GPAsum + x
    GPAavg = GPAsum/len(GPAList)
    return GPAavg

def highestAvg(arr):
    dict = {}
    i = 0
    while i < len(arr):
        if int(arr[i][1]) <= 22:
            dict.setdefault(arr[i][2], []).append(arr[i][3])
        i = i+1
    final = max(dict, key = dict.get)
    return final



if __name__ == "__main__" :
    nameList = []
    ageList = []
    majorList = []
    gpaList = []
    d={}
    with open('roster2.dat', "r") as f:
        lines = f.readlines()
        for line in lines:
            (name, age, major, gpa) = line.split(",")
            nameList.append(name)
            ageList.append(age)
            majorList.append(major)
            gpaList.append(gpa)

    arr = np.zeros(len(nameList), dtype={'names':('name','age','major','gpa'), 'formats':('U50','i4','U4','f8')})

    arr['name'] = nameList
    arr['age'] = ageList
    arr['major'] = majorList
    arr['gpa'] = gpaList

    print(avgGPA(arr))
    print(maxCSGPA(arr))
    print(over35(arr))
    print(over25(arr))
    print(highestAvg(arr))
