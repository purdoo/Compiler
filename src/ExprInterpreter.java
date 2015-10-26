import java.util.*;


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

	public Expr(String id, String expr)
	{
		this.id = id;
		this.expr = expr;
	}
}
