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
    | atom # AtomExpression;

atom: NUMBER # numberAtom;

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

WS : (' ' | '\t')+ -> skip;