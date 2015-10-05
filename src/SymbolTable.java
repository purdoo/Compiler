import java.util.*;



class SymbolTableStack
{
	public static int BlockCount = 1;
	public List<SymbolTable> SymbolTables = new ArrayList<SymbolTable>();
	public SymbolTableStack()
	{
		// do I need a constructor?		
	}

	public void AddTable(SymbolTable ST)
	{
		this.SymbolTables.add(ST);
	}

	public void PrintStack()
	{
		for(SymbolTable ST : SymbolTables)
		{
			System.out.println("Symbol table " + ST.Scope);
			for(Symbol S : ST.SYMBOLS)
			{
				if(S.var_type == "STRING")
				{
					System.out.println("name " + S.var_name + " type " + S.var_type + " value " + S.Value);
				}		
			}
			System.out.print("\n");
		}
	}
}


class SymbolTable
{
	public String Scope;
	public List<Symbol> SYMBOLS = new ArrayList<Symbol>();

	public SymbolTable()
	{
		this.Scope = "BLOCK " + SymbolTableStack.BlockCount;
		SymbolTableStack.BlockCount++;
	}	

	public SymbolTable(String Scope)
	{
		this.Scope = Scope;
	}
	
	public void Add(String var_name, ArrayList<String> vars)
	{
		//this.SYMBOLS.add(new Symbol(var_name, "STRING", val));
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
	public Symbol(String var_name, String var_type, String val)
	{	
		this.var_name = var_name;
		this.var_type = var_type;
		this.Value = val;
	}
}


