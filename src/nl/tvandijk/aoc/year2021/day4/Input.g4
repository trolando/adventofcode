grammar Input;

root            : drawn (board)*;
drawn           : NUMBER (',' NUMBER)*;
board           : NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER;

fragment LETTER : [a-zA-Z];
fragment DIGIT  : [0-9] ;

COMMAND         : LETTER+;
NUMBER          : DIGIT+;
WS              : [ \t\r\n]+ -> skip ;

