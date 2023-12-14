import sys

def sumToN(n):
    return (n*(n+1))//2

if __name__ == "__main__":
    num = int(sys.argv[1])
    regexes = []
    y = 0
    for x in range(0, (num-1)):
        regex = "^" + "(.)"*x + ".?" + "\\\\" + "\\\\".join([str(i) for i in range (sumToN(x), sumToN(x-1), -1)]) + "$"
        regexes.append(regex)

    print ("|".join(regexes))
        
