grammar Micro;

options {
    language = Java;
}

//Program

id: IDENTIFIER;

pgm_body: ;

KEYWORD : 'PROGRAM' | 'BEGIN' | 'END' | 'FUNCTION' | 'READ' | 'WRITE' | 'IF' | 'ELSE' | 'FI' | 'FOR' | 'ROF' | 'CONTINUE' | 'BREAK' | 'RETURN' | 'INT' | 'VOID' | 'STRING' | 'FLOAT' ;

OPERATOR: (':=' | '+' | '-' | '*' | '/' | '=' | '!=' | '<' | '>' | '(' | ')' | ';' | ',' | '<=' | '>=');

COMMENT: '--' ~[\r\n]* -> skip;

WHITESPACE:  (' ' | '\n' | '\t' | '\f' | '\r')+ -> skip; 

IDENTIFIER: [A-z_][A-z0-9_]*;

//DIGIT: [0-9]
//INTLITERAL: [DIGIT]+

INTLITERAL: [0-9]+;

FLOATLITERAL: (INTLITERAL)*('.')(INTLITERAL)*;

STRINGLITERAL: '"'(~[\r\n])*?'"';


