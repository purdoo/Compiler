import java.util.*;

public class SymbolTable
{
	public String Scope;
	public List<Symbol> SL = new ArrayList<Symbol>();
	public List<String> Scopes = new ArrayList<String>();
	public SymbolTable(String Scope)
	{
		this.Scope = Scope;
	}
	public void Add(String var_name, String type_name)
	{
		this.SL.add(new Symbol(var_name, type_name));
	}
	public void AddScope(String function_name)
	{
		this.Scopes.add(this.Scope);
		this.Scope = function_name;	
	}
	
	public void Print()
	{
		this.Scopes.add(this.Scope);
		System.out.println("Testing");
		for(String iter : this.Scopes)
		{
			System.out.println("Symbol table " + iter);
		}
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


