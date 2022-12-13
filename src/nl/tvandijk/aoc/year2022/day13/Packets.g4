grammar Packets;

root            : list;
number          : NUMBER;
list            : '[' ( (number | list) ','? )* ']';

fragment DIGIT  : [0-9];

//ARROW           : [->];
NUMBER          : DIGIT+;
WS              : [ \t\r\n]+ -> skip;

