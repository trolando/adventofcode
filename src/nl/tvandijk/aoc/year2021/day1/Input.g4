grammar Input;

root    : meter (NL meter)*;
meter   : DEPTHNUMBER+ NL?;

DEPTHNUMBER  : [0-9];
WS    : [ \t\r]+ -> skip ;
NL    : '\n';