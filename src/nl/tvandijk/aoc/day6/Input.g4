grammar Input;

root    : group (NL group)*;
group   : (person NL)* ;
person  : CHAR+
        ;

CHAR  : [a-zA-Z];
WS    : [ \t\r]+ -> skip ;
NL    : '\n';