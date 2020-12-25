grammar Input;

start : instruction+;

instruction : 'mask' '=' XSTR                  # maskInstruction
            | 'mem[' loc=XSTR ']' '=' val=XSTR # memInstruction
            ;

XSTR : [0123456789X]+ ;
WS   : [ \t\r,\n]+ -> skip ;
