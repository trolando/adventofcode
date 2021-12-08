grammar Digits;

root            : line (NL line)*;
line            : input '|' output;
input           : code+;
output          : code+;
code            : CODE;

fragment DIGIT  : [0-9];
fragment CHAR   : [a-zA-Z];

CODE            : CHAR+;
WS              : [ \t\r]+ -> skip;
NL              : '\n';

