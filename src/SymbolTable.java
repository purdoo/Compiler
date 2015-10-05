import java.util.*;

public class SymbolTable
{
	public SymbolTable()
	{
		
	}
	
}

class Symbol
{
	protected String var_name;
	protected VarType var_type;
	protected String Value;
	public Symbol(String var_name, VarType type_name, String value)
	{	
		this.var_name = var_name;
		this.var_type = var_type;
		this.Value = value;
	}
	public enum VarType {
		STRING,
		INT,
		FLOAT;
	}
}


