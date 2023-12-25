from z3 import *

lines = open("input.txt").read().strip().splitlines()

hailstones = []
for l in lines:
    a, b = l.split(' @ ')
    x, y, z = [int(w) for w in a.split(', ')]
    dx, dy, dz = [int(w) for w in b.split(', ')]
    hailstones.append((x, y, z, dx, dy, dz))

fx, fy, fz, fdx, fdy, fdz = (Real(name) for name in ('fx', 'fy', 'fz', 'fdx', 'fdy', 'fdz'))
s = Solver()

for i, (x, y, z, dx, dy, dz) in enumerate(hailstones[:3]):
    t = Real(f't{i}')
    s.add(t >= 0)
    s.add(x + dx * t == fx + fdx * t)
    s.add(y + dy * t == fy + fdy * t)
    s.add(z + dz * t == fz + fdz * t)

assert s.check() == sat
print(s.model().eval(fx + fy + fz))