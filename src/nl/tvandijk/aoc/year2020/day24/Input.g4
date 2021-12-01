grammar Input;

root : line ('\n' line)* ;
line : dir* ;
dir  : 'e'
     | 'ne'
     | 'nw'
     | 'se'
     | 'sw'
     | 'w'
     ;

CHAR : [a-zA-Z] ;
INT  : [0-9]+ ;
WS   : [ \t\r]+ -> skip ;