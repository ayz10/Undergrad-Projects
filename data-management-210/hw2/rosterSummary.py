import sys

def merge(a, b):
    dict = {**a,**b}
    for key, value in dict.items():
        if key in a and key in b:
            dict[key] = [value, a[key]]
    return dict

def majorCount(d):
    dict = {}
    for key in d:
        students = d[key]
        count = len(students)
        dict.setdefault(key, []).append(count)   
    return dict

def avgCredits(d):
    thisdict = {}
    for key in d:
        creditsSum = 0.0
        students = d[key]
        for a, b, c in students:
            creditsSum = creditsSum + float(c)
        creditsAvg = creditsSum/len(students)
        thisdict.setdefault(key, []).append(creditsAvg)
    return thisdict 
    
def avgGPA(d):
    dict = {}
    for key in d:
        gpaSum = 0.0
        students = d[key]
        for a, b, c in students:
            gpaSum = gpaSum + float(b)
        gpaAvg = gpaSum/len(students)
        dict.setdefault(key, []).append(gpaAvg)
    return dict

if __name__ == "__main__" :
    d={}
    with open('roster1.dat', "r") as f:
        lines = f.readlines()
        for line in lines:
            (name, major, gpa, credits) = line.split(",")
            tuple = (name, gpa, credits)
            d.setdefault(major, []).append(tuple)
    gpaDict = avgGPA(d)
    creditsDict = avgCredits(d)
    countDict = majorCount(d)
    final = merge(countDict,merge(creditsDict, gpaDict))
    with open('roster1.out', 'w') as f:
        for key, value in final.items():
            f.write('%s, %s\n' % (key, value))
