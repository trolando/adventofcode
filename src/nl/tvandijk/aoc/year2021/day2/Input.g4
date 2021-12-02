grammar Input;

root            : command+;
command         : COMMAND NUMBER;

fragment LETTER : [a-zA-Z];
fragment DIGIT  : [0-9] ;

COMMAND         : LETTER+;
NUMBER          : DIGIT+;
WS              : [ \t\r\n]+ -> skip ;
