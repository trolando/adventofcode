grammar Tiles;

tiles : tile+;
tile  : 'Tile' id=INT ':' content;
content: PLACE+;

PLACE: [.#] ;
INT  : [0-9]+ ;
WS   : [ \t\r\n]+ -> skip ;







