grammar Expr1;

start : expr+;

expr  : left=expr op=('*'|'+') right=expr     #opExpr
      | '(' expr ')'                          #parenExpr
      | atom=INT                              #atomExpr
      ;

INT   : ('0'..'9')+ ;

WS    : [ \t\r\n]+ -> skip ;
