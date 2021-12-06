grammar Lines;

root            : line+;
line            : NUMBER ',' NUMBER '->' NUMBER ',' NUMBER;

fragment DIGIT  : [0-9];

//ARROW           : [->];
NUMBER          : DIGIT+;
WS              : [ \t\r\n]+ -> skip;

