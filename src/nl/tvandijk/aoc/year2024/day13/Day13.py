from z3 import *

L = open("input.txt").read().strip().split("\n\n")

for part2 in False, True:
    sum = 0
    for config in L:
        a,b,c = config.split("\n")
        a = a.split(": ")[1].split(", ")
        b = b.split(": ")[1].split(", ")
        c = c.split(": ")[1].split(", ")
        ax = int(a[0][2:])
        ay = int(a[1][2:])
        bx = int(b[0][2:])
        by = int(b[1][2:])
        cx = int(c[0][2:])
        cy = int(c[1][2:])
        s = Optimize()
        counta, countb = (Int(name) for name in ('counta', 'countb'))
        if not part2:
            s.add(counta * ax + countb * bx == cx)
            s.add(counta * ay + countb * by == cy)
        else:
            s.add(counta*ax + countb*bx == 10000000000000+cx)
            s.add(counta*ay + countb*by == 10000000000000+cy)
        s.add(counta >= 0)
        s.add(countb >= 0)
        s.minimize(3*counta + countb)
        if (s.check() == sat):
            sum += s.model().eval(3*counta + countb).as_long()

    print(sum)


