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
				// add print statements			
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
	public void Add(String var_name, String type_name)
	{
		this.SYMBOLS.add(new Symbol(var_name, type_name));
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


