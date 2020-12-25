grammar Input;

players : player player;
player  : 'Player' INT ':' card+;
card    : INT;

CHAR : [a-zA-Z]+ ;
INT  : [0-9]+ ;
WS   : [ \t\r\n]+ -> skip ;