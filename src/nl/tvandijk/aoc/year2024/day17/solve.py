from z3 import *

opt = Optimize()
s = BitVec('s', 64)
a, b, c = s, 0, 0
for x in [2,4,1,3,7,5,1,5,0,3,4,2,5,5,3,0]:
    b = a % 8
    b = b ^ 3
    c = a >> b
    a = a >> 3
    b = b ^ c
    b = b ^ 5
    opt.add((b % 8) == x)
opt.add(a == 0)
opt.minimize(s)
assert str(opt.check()) == 'sat'
print(opt.model().eval(s))
