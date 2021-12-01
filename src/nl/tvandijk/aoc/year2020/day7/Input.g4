grammar Input;

root    : line ('\n' line)*;
line    : bag 'contain' nbags+ #containsBags
        | bag 'contain no other bags' #containsNoBags
        ;
nbags   : count=INT bag;
bag     : CHARS CHARS 'bag'
        | CHARS CHARS 'bags';

CHARS : [a-zA-Z]+ ;
INT   : [0-9]+ ;
WS    : [ \t\r,.]+ -> skip ;