grammar Input;

root            : drawn (board)*;
drawn           : NUMBER (',' NUMBER)*;
board           : NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER;

fragment DIGIT  : [0-9] ;

NUMBER          : DIGIT+;
WS              : [ \t\r\n]+ -> skip ;

