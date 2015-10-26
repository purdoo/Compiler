import java.util.*;


class IRNodeList
{
	public List<IRNode> NodeList = new ArrayList<IRNode>();
	public int TempCounter = 0;	
	public List<Expr> ExprList;	

	public IRNodeList(ExprInterpreter EI)
	{
		this.ExprList = EI.ExprList;
	}
		
	public void Decode()
	{
		/*

		*/
	}

	public void PrintExprList()
	{
		for(Expr E : this.ExprList)
		{
			System.out.println("ID: " + E.id + " Expr: " + E.expr);
		}
	}

	public void AddNode(IRNode node)
	{
		this.NodeList.add(node);
	}
	/*
	public void PrintNodeList()
	{
		for(IRNode IR : NodeList)
		{
			System.out.println("ID: " + IR.id + " Expr: " + IR.expr);
		}
	}	
	*/

}

class IRNode
{
	public String OpCode;
	public String FirstOperand;
	public String SecondOperand;
	public String Result;
		

	public IRNode(String id, String expr)
	{

	}
	
	/*
	public FindOpCode()
	{
		
		switch (operator) 
		{
            case ">":
                return "LE";
            case "<":
                return "GE";
            case ">=":
                return "LT";
            case "<=":
                return "GT";
            case "!=":
                return "EQ";
            case "=":
                return "NE";
            default:
                return "";
        }
		
	}	*/
}
