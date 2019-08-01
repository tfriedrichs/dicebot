grammar DiceExpression;

@header {
package io.github.tfriedrichs.dicebot.parser;
}

parse: expression EOF;
expression:
    LPAREN expression RPAREN # ParenExpression
    | MINUS expression # MinusExpression
    | expression op=(MULT | DIV) expression # MultExpression
    | expression op=(PLUS | MINUS) expression # AddExpression
    | MAX LPAREN expression COMMA expression RPAREN # MaxExpression
    | MIN LPAREN expression COMMA expression RPAREN # MinExpression
    | ABS LPAREN expression RPAREN # AbsExpression
    | expression DICE sides modifier* # DiceWithExpressionAtom
    | DICE sides modifier* # DiceWithoutExpressionAtom
    | NUMBER # NumberAtom;

sides: NUMBER | FATE | PERCENT;
modifier:
    LBRACE modifier RBRACE  # BraceModifier
    | KEEP directionSelector # KeepModifier
    | DROPHIGH NUMBER? # DropHighModifier
    | DROPLOW NUMBER? # DropLowModifier
    | SUCCESS comparisonSelector # SuccessModifier
    | FAILURE comparisonSelector # FailureModifier
    | EXPLODE comparisonSelector? # ExplodeModifier
    | COMPOUND comparisonSelector? # CompoundModifier
    | PENETRATE comparisonSelector? # PenetrateModifier
    | CRIT_SUCCESS comparisonSelector # CriticalSuccessModifier
    | CRIT_FAILURE comparisonSelector # CriticalFailureModifier;
comparisonSelector: op=(LESSER | LESSER_EQUALS | EQUALS | GREATER_EQUALS | GREATER)? NUMBER;
directionSelector: direction=(HIGH | LOW) NUMBER?;

CRIT_SUCCESS: 'cs';
CRIT_FAILURE: 'cf';
COMPOUND: '!c';
PENETRATE: '!p';
EXPLODE: '!';
KEEP: 'k' | 'K';
DROPHIGH: 'dh';
DROPLOW: 'dl';
SUCCESS: 'e';
FAILURE: 'f';
FATE: 'F';
PERCENT: '%';
LBRACE: '{';
RBRACE: '}';
LPAREN: '(';
RPAREN: ')';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
COMMA: ',';
MAX: 'max';
MIN: 'min';
ABS: 'abs';
NUMBER: '0'..'9'+;
DICE: 'd' | 'D';
LESSER: '<';
LESSER_EQUALS: '<=';
EQUALS: '=';
GREATER_EQUALS: '>=';
GREATER: '>';
HIGH: 'h';
LOW: 'l';


WS : (' ' | '\t')+ -> skip;