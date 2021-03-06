import java.util.*;

/* Expression Interpreter for Lines of Expressions */
class ExprInterpreter
{
	public List<Expr> ExprList = new ArrayList<Expr>();

	public void AddExpr(Expr e)
	{
		this.ExprList.add(e);
	}
}
class Expr
{
	public String id;
	public String expr;

	public String condition;
	public String decl;
	public String stmtList;
	public String elsePart;

	public Expr(String id, String expr)
	{
		this.id = id;
		this.expr = expr;
	}

	public Expr(String cond, String decl, String stmt_list, String else_part)
	{
		this.id = "IF";
		this.condition = cond;
		this.decl = decl;
		this.stmtList = stmt_list;
		this.elsePart = else_part;
	}

}

/* Semantic Interpreter for If/For Statements */
class SemanticInterpreter
{
	public List<Sem> SemList = new ArrayList<Sem>();

	public void AddSem(Sem e)
	{
		this.SemList.add(e);
	}
}

class Sem
{
	public String id;
	public String expr;

	public Sem(String id, String expr)
	{
		this.id = id;
		this.expr = expr;
	}
}