import java.util.*;


class IRNodeList
{
	public List<IRNode> NodeList = new ArrayList<IRNode>();
	
	public List<Expr> ExprList;	
	public SymbolTableStack STS;

	public int TempCounter = 0;	

	public IRNodeList(ExprInterpreter EI, SymbolTableStack STS)
	{
		this.ExprList = EI.ExprList;
		this.STS = STS;
		this.Decode();
	}
		
	public void Decode()
	{
		for(Expr E : this.ExprList)
		{
			if(E.id == "WRITE") // currently assuming we are only writing one var per call of WRITE(...)
			{
				//System.out.println("Found a write");
				//String temp = SymbolLookup(E.expr);
				//System.out.println(temp);
				if(SymbolLookup(E.expr).equals("INT"))
				{
					this.NodeList.add(new IRNode("WRITEI", E.expr));
				}
				else if(SymbolLookup(E.expr).equals("FLOAT"))
				{
					this.NodeList.add(new IRNode("WRITEF", E.expr));
				}
				
			}
		}
	}
	
	/* Given a symbol (variable) name, look at the symbol table to determine its type */
	public String SymbolLookup(String SymbolName)
	{
		for(SymbolTable ST : this.STS.SymbolTables)
		{
			for(Symbol S : ST.SYMBOLS)
			{
				if(S.var_name.equals(SymbolName))
				{
					return S.var_type;
				}
			}
		}
		return "Symbol Not Found";
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
	
	public void PrintNodeList()
	{
		for(IRNode IR : this.NodeList)
		{
			System.out.println(";" + IR.OpCode + " " + IR.Result);
		}
	}	
	

}

class IRNode
{
	public String OpCode;
	public String FirstOperand;
	public String SecondOperand;
	public String Result;
		
	public IRNode(String OpCode, String Result)
	{
		this.OpCode = OpCode;
		this.Result = Result;
	}
	
}
