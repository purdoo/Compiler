grammar Micro;

options {
    language = Java;
}


@members{
	/* Step Three */
	public SymbolTableStack STACK = new SymbolTableStack();
	public SymbolTable TABLE = new SymbolTable("GLOBAL");
	/* Step Four */	
	public ExprInterpreter EI = new ExprInterpreter();
	/* Step Five */
	public SemanticInterpreter SI = new SemanticInterpreter();
}

/* Program */
program: 'PROGRAM' id 'BEGIN' pgm_body 'END';

id: IDENTIFIER;
pgm_body: decl func_declarations;
decl: decl_list*;
decl_list: string_decl_list | var_decl_list ;

/* Global String Declaration */
string_decl: 'STRING' id ':=' str ';'
{
	TABLE.AddGlobalString($id.text, $str.text);
};

string_decl_list: string_decl+;
str: STRINGLITERAL;

/* Variable Declaration */
var_decl_list: var_decl+;
var_decl: var_type id_list
{
	String[] vars = $id_list.text.split(",");
	TABLE.Add($var_type.text, vars);
	TABLE.AddLocal(vars);
} 
';';
var_type: 'FLOAT' | 'INT';
any_type: var_type | 'VOID'; 
id_list: id (',' id)*;
/* End Variable Declarations */


/* Function Paramater List */
param_decl_list: param_decl param_decl_tail | ;
param_decl: var_type id
{
	TABLE.Add($id.text, $var_type.text);
	TABLE.AddParam($id.text);
};
param_decl_tail: ',' param_decl param_decl_tail | ;


/* Function Declarations */
func_declarations: func_decl*;
func_decl: 'FUNCTION' any_type id 
{ 
	STACK.AddTable(TABLE); 
	TABLE = new SymbolTable($id.text);
	TABLE.ReturnType = $any_type.text;
}
'(' param_decl_list ')' 
{
	//Expr E = new Expr("FUNCTION" + ";" + $any_type.text + ";" + $id.text, $param_decl_list.text);
	Expr E = new Expr("FUNCTION", $any_type.text + ";" + $id.text);
	//Expr E = new Expr("FUNCTION", $any_type.text + ";" + $id.text + ";" + $param_decl_list.text);
	EI.AddExpr(E);
}
'BEGIN' func_body 'END'
{
	E = new Expr("END_FUNCTION", $id.text);
	EI.AddExpr(E);
};

func_body: decl stmt_list ;

/* Statement List */
stmt_list: stmt stmt_list | ;
stmt: base_stmt | if_stmt | for_stmt;
base_stmt: assign_stmt | read_stmt | write_stmt | return_stmt;

/* Basic Statements */
assign_stmt: assign_expr ';';
assign_expr: id ':=' expr
{
	Expr E = new Expr($id.text, $expr.text);
	EI.AddExpr(E);	
};
read_stmt: 'READ' '(' id_list ')' ';'
{
	Expr E = new Expr("READ", $id_list.text);
	EI.AddExpr(E);
};

write_stmt: 'WRITE' '(' id_list ')' ';'
{
	Expr E = new Expr("WRITE", $id_list.text);
	EI.AddExpr(E);
};

return_stmt: 'RETURN' expr ';'
{
	TABLE.ReturnValue = $expr.text;
};

/* Expressions */
expr: expr_prefix factor;
expr_prefix: expr_prefix factor addop | ;
factor: factor_prefix postfix_expr;
factor_prefix: factor_prefix postfix_expr mulop | ;
postfix_expr: primary | call_expr;
call_expr :id '(' expr_list ')';
expr_list: expr expr_list_tail | ;
expr_list_tail: ',' expr expr_list_tail | ;
primary: '(' expr ')' | id | INTLITERAL | FLOATLITERAL;
addop: '+' | '-';
mulop: '*' | '/';

/* Complex Statements and Conditions */ 
if_stmt: 'IF' 
{ 
	STACK.AddTable(TABLE);
	TABLE = new SymbolTable();
}
'(' cond ')'
{
	Expr E = new Expr("IF", $cond.text);
	EI.AddExpr(E);
}
decl stmt_list
{
	E = new Expr("ELSE", $stmt_list.text);
	EI.AddExpr(E);
}
else_part 'FI'
{
	E = new Expr("FI", $stmt_list.text);
	EI.AddExpr(E);
};

else_part: ('ELSE' decl stmt_list )? 
{
	STACK.AddTable(TABLE);
	TABLE = new SymbolTable();
};
cond: expr compop expr;
compop: '<' | '>' | '=' | '!=' | '<=' | '>=';

init_stmt: assign_expr | ;
incr_stmt: assign_expr | ;

/* ECE 468 Student version of for statement */
while_stmt: 'WHILE' '(' cond ')' decl stmt_list 'ENDWHILE';

for_stmt: 'FOR' 
{
	STACK.AddTable(TABLE);
	TABLE = new SymbolTable();
}
'(' init_stmt ';' cond ';' incr_stmt ')'
{
	Expr E = new Expr("FOR", $init_stmt.text + "," + $cond.text + "," + $incr_stmt.text);
	EI.AddExpr(E);
}
decl stmt_list
{
	E = new Expr("CONT", $incr_stmt.text);
	EI.AddExpr(E);
}
'ROF'
{
	E = new Expr("ROF", "Ayy lmao");
	EI.AddExpr(E);
}
;

KEYWORD : 'PROGRAM' | 'BEGIN' | 'END' | 'FUNCTION' | 'READ' | 'WRITE' | 'IF' | 'ELSE' | 'FI' | 'FOR' | 'ROF' | 'CONTINUE' | 'BREAK' | 'RETURN' | 'INT' | 'VOID' | 'STRING' | 'FLOAT' ;

OPERATOR: (':=' | '+' | '-' | '*' | '/' | '=' | '!=' | '<' | '>' | '(' | ')' | ';' | ',' | '<=' | '>=');

COMMENT: '--' ~[\r\n]* -> skip;

WHITESPACE:  (' ' | '\n' | '\t' | '\f' | '\r')+ -> skip; 

IDENTIFIER: [A-z_][A-z0-9_]*;

INTLITERAL: [0-9]+;

FLOATLITERAL: (INTLITERAL)*('.')(INTLITERAL)*;

STRINGLITERAL: '"'(~[\r\n])*?'"';


