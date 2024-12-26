from collections import defaultdict

from z3 import *

L = open("input.txt").read().strip().split("\n")

computers = set()
conn = defaultdict(set)
for l in L:
    S = l.split("-")
    for pc in S:
        computers.add(pc)
    conn[S[0]].add(S[1])
    conn[S[1]].add(S[0])

s = Optimize()
P = {name: Int(name) for name in computers}
for p in P.values():
    s.add(p >= 0)
    s.add(p <= 1)
for pc in computers:
    for pc2 in computers:
        if not pc in conn[pc2]:
            if pc is not pc2:
                s.add(P[pc] + P[pc2] <= 1)
s.maximize(Sum(list(P.values())))
if (s.check() == sat):
    M = s.model()
    selected_computers = [pc for pc, var in P.items() if M.evaluate(var).as_long() == 1]
    print(",".join(sorted(selected_computers)))



