import java.util.*;

public class SymbolTable
{
	public String Scope;
	public List<Symbol> SL = new ArrayList<Symbol>();
	public SymbolTable(String Scope)
	{
		this.Scope = Scope;
	}
	public void Add(String var_name, String type_name)
	{
		this.SL.add(new Symbol(var_name, type_name));
	}
	public void Print()
	{
		System.out.println("Testing");
	}
}

class Symbol
{
	protected String var_name;
	protected String var_type;
	protected String Value;
	public Symbol(String var_name, String type_name)
	{	
		this.var_name = var_name;
		this.var_type = var_type;
	}
}


