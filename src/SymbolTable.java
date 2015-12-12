import java.util.*;

class SymbolTableStack
{
	public static int BlockCount = 1;
	public List<SymbolTable> SymbolTables = new ArrayList<SymbolTable>();

	public void AddTable(SymbolTable ST)
	{
		this.SymbolTables.add(ST);
	}

	public void PrintStack()
	{
		int i = 0;
		for(SymbolTable ST : SymbolTables)
		{
			System.out.println("Symbol table " + ST.Scope);
			for(Symbol S : ST.SYMBOLS)
			{
				if(S.var_type == "STRING")
				{
					System.out.println("name " + S.var_name + " type " + S.var_type + " value " + S.Value);
				}
				else
				{
					System.out.println("name " + S.var_name + " type " + S.var_type);
				}
			}
			i ++;
			if(i != SymbolTables.size())
			{
				System.out.print("\n");
			}
			
		}
	}
}


class SymbolTable
{
	public String Scope;
	public String ReturnValue = "";
	public String ReturnType = "";
	public List<Symbol> SYMBOLS = new ArrayList<Symbol>();
	public List<String> Parameters = new ArrayList<String>();
	public List<String> Locals = new ArrayList<String>();
  //public HashMap<String, Integer> Parameters = new HashMap<String, Integer>();
	//public HashMap<String, Integer> Locals = new HashMap<String, Integer>();
	public SymbolTable()
	{
		this.Scope = "BLOCK " + SymbolTableStack.BlockCount;
		SymbolTableStack.BlockCount++;
	}	

	public SymbolTable(String Scope)
	{
		this.Scope = Scope;
	}

	public void AddParam(String name)
	{
		this.Parameters.add(name);
	}

	public void AddLocal(String[] vars)
	{
		for(String var : vars)
		{
			this.Locals.add(var);
		}
	}
	
	public void Add(String var_type, String[] vars)
	{
		for(String var : vars)
		{
			this.SYMBOLS.add(new Symbol(var, var_type));
		}
	}

	public void Add(String var_name, String var_type)
	{
		this.SYMBOLS.add(new Symbol(var_name, var_type));
	}

	public void AddGlobalString(String var_name, String val)
	{
		this.SYMBOLS.add(new Symbol(var_name, "STRING", val));
	}

}

class Symbol
{
	protected String var_name;
	protected String var_type;
	protected String Value;

	public Symbol(String var_name, String var_type)
	{	
		this.var_name = var_name;
		this.var_type = var_type;
	}	

	public Symbol(String var_name, String var_type, String val)
	{	
		this.var_name = var_name;
		this.var_type = var_type;
		this.Value = val;
	}
}


