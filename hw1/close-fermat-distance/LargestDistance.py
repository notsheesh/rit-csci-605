min_val = 2 
max_val = 5
from math import floor

'''
a cube + b cube = c sq 
for a max, b = bmin and c = cmax 
a cube = c sq - b cube
amax cube = cmax sq - bmin cube
amax cube = max sq - 1
amax = cubert(max sq - 1)
similarly, bmax = amax
'''
a_max = floor((max_val**2-1)**(1/3))
b_max = a_max
c_max = min_val
for a in range(1, a_max+1): 
    for b in range(1, b_max+1):
        c = (a**3+b**3)**(1/2)
        if c % 1 == 0:
            c = int(c)
            if c in range(min_val, max_val+1):
                c_max = c
                print("({a}, {b}, {c}): {a}^3 + {b}^3 == {c}^2".format(
                    a = a, 
                    b = b, 
                    c = c
                    ))

print("The greatest distance in range {mn} and {mx} is {c_max}".format(
    mn = min_val, mx = max_val, c_max = c_max
))