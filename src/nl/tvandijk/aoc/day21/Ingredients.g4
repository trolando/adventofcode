grammar Ingredients;

products   : product (NL product)*;
product    : ingredient+ 'contains' allergen+;
ingredient : CHAR;
allergen   : CHAR;

CHAR : [a-zA-Z]+ ;
INT  : [0-9]+ ;
WS   : [ \t\r,)(]+ -> skip ;
NL   : '\n' ;