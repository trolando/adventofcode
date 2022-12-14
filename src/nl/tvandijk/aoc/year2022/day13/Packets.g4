grammar Packets;

root            : list;
number          : NUMBER;
list            : '[' (number | list)* ']';

fragment DIGIT  : [0-9];

NUMBER          : DIGIT+;
WS              : [ \t\r\n,]+ -> skip;

