grammar MessageRules;

start : parserule+ word+;

parserule  : id=INT ':' expr;

expr  : left=expr right=expr     #serialExpr
      | left=expr '|' right=expr #orExpr
      | id=INT                   #subruleExpr
      | '"' ch=CHAR '"'          #letterExpr
      ;

word  : CHAR;

INT   : [0-9]+ ;
CHAR  : [a-z]+ ;
WS    : [ \t\r\n]+ -> skip ;
