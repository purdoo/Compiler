import java.util.*;


class IRNodeList
{
	public List<IRNode> NodeList = new ArrayList<IRNode>();
	
	public List<Expr> ExprList;	
	public SymbolTableStack STS;

	public int TempCounter = 1;	

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
				if(SymbolLookup(E.expr).equals("INT"))
				{
					this.NodeList.add(new IRNode("WRITEI", E.expr));
				}
				else if(SymbolLookup(E.expr).equals("FLOAT"))
				{
					this.NodeList.add(new IRNode("WRITEF", E.expr));
				}
			}
			else // an add/sub/mult/div or assign function
			{
				if(HelperFunctions.ExpressionType(E.expr) == 0) // assign function
				{
					if(SymbolLookup(E.id).equals("INT"))
					{
						this.NodeList.add(new IRNode("STOREI", E.expr, "$T" + String.valueOf(this.TempCounter)));
						this.NodeList.add(new IRNode("STOREI", "$T" + String.valueOf(this.TempCounter), E.id));						
						this.TempCounter ++;
					}
					else if(SymbolLookup(E.id).equals("FLOAT"))
					{
						this.NodeList.add(new IRNode("STOREF", E.expr, "$T" + String.valueOf(this.TempCounter)));
						this.NodeList.add(new IRNode("STOREF", "$T" + String.valueOf(this.TempCounter), E.id));			
						this.TempCounter ++;
					}
				}
				else // a line of expression(s)
				{
					if(SymbolLookup(E.id).equals("INT"))
					{
						String expr = E.expr.replaceAll("\\s","");
						if(HelperFunctions.CountOccurancesOf("(", expr) == 0)
						{
							/*
							StringBuilder builder = new StringBuilder(expr);
							while(HelperFunctions.ExpressionType(builder) != 0)	
							{
								while(HelperFunctions.CountOccurancesOf('*', builder) > 0 || HelperFunctions.CountOccurancesOf('/', builder) > 0)
								{
									//builder = HelperFunctions.ReduceOne(builder, this.TempCounter);								
									//System.out.println(builder);
									this.TempCounter ++;
								}
								while(HelperFunctions.CountOccurancesOf('+', builder) > 0 || HelperFunctions.CountOccurancesOf('-', builder) > 0)
								{
									builder = HelperFunctions.ReduceTwo(builder, this.TempCounter);
									//System.out.println(builder);
									this.TempCounter ++;
								}
							}	
							*/
						}
						else if(HelperFunctions.CountOccurancesOf("(", expr) == 1)
						{
							// Parenthesis Reduction(s) First
							StringBuilder builder = new StringBuilder(expr);
							int open = builder.indexOf("(");
							int close = builder.indexOf(")");
							StringBuilder subBuilder = new StringBuilder(builder.substring(open+1,close));
							//System.out.println(subBuilder);
							// Now simplify the subBuilder (stuff between parenthesis) to a single register							
														
							while(HelperFunctions.ExpressionType(subBuilder) != 0) // while expression still has +-*/ in it
							{
								int multOp = subBuilder.indexOf("*");
								int divOp = subBuilder.indexOf("/");
								if(multOp != -1 && divOp == -1)
								{
									int i = multOp;
									if(Character.isLetter(subBuilder.charAt(i-1)))
									{
										this.NodeList.add(new IRNode("MULTI", subBuilder.substring(i-1, i), subBuilder.substring(i+1, i+2), "$T" + String.valueOf(this.TempCounter)));
										subBuilder.delete(i-1, i+2);
										subBuilder.insert(i-1, "$T"+String.valueOf(this.TempCounter));
										this.TempCounter ++;
									}
									else
									{
										if(this.TempCounter < 10)
										{
											subBuilder.delete(i-3, i+2);
											subBuilder.insert(i-3, "$T"+String.valueOf(this.TempCounter));
										}
										else
										{
											subBuilder.delete(i-4, i+2);
											subBuilder.insert(i-4, "$T"+String.valueOf(this.TempCounter));
										}
									}
									//System.out.println(subBuilder);
								}
								else if(multOp == -1 && divOp != -1)
								{
								}
								else if(multOp != -1 && divOp != -1)
								{

								}
								else // at this point, we have no more *'s or /'s
								{
									int addOp = subBuilder.indexOf("+");
									int subOp = subBuilder.indexOf("-");
									if(addOp != -1 && subOp == -1)
									{
										int i = addOp;
										if(Character.isLetter(subBuilder.charAt(i-1)))
										{
											this.NodeList.add(new IRNode("ADDI", subBuilder.substring(i-1, i), subBuilder.substring(i+1, i+2), "$T" + String.valueOf(this.TempCounter)));
											subBuilder.delete(i-1, i+2);
											subBuilder.insert(i-1, "$T"+String.valueOf(this.TempCounter));
											this.TempCounter ++;
										}
										else
										{
											if(this.TempCounter < 10)
											{
												this.NodeList.add(new IRNode("ADDI", subBuilder.substring(i-3, i), subBuilder.substring(i+1, i+2), "$T" + String.valueOf(this.TempCounter)));
												subBuilder.delete(i-3, i+2);
												subBuilder.insert(i-3, "$T"+String.valueOf(this.TempCounter));
												this.TempCounter ++;
											}
											else
											{
												this.NodeList.add(new IRNode("ADDI", subBuilder.substring(i-4, i), subBuilder.substring(i+1, i+2), "$T" + String.valueOf(this.TempCounter)));
												subBuilder.delete(i-4, i+2);
												subBuilder.insert(i-4, "$T"+String.valueOf(this.TempCounter));
												this.TempCounter ++;
											}
										}
										//System.out.println(subBuilder);
									}
								}
							} // endof while(HelperFunctions.ExpressionType(subBuilder) != 0)
							builder.replace(open, close+1, subBuilder.toString());
							System.out.println(builder);
						}

						else if(HelperFunctions.CountOccurancesOf("(", expr) > 1)
						{
							//System.out.println(expr + "multiple paren");
						}
					}
					
				}
			}
		}
	}
	

	public String Reduce(StringBuilder builder, int order)
	{

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
			IR.PrintNode();
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

	public IRNode(String OpCode, String OpOne, String Result)
	{
		this.OpCode = OpCode;
		this.FirstOperand = OpOne;
		this.Result = Result;
	}

	public IRNode(String OpCode, String OpOne, String OpTwo, String Result)
	{
		this.OpCode = OpCode;
		this.FirstOperand = OpOne;
		this.SecondOperand = OpTwo;
		this.Result = Result;
	}

	public void PrintNode()
	{
		if(this.FirstOperand == null && this.SecondOperand == null)
		{
			System.out.println(";" + this.OpCode + " " + this.Result);
		}
		else if(this.FirstOperand != null && this.SecondOperand == null)
		{
			System.out.println(";" + this.OpCode + " " + this.FirstOperand + " " + this.Result);
		}
		else
		{
			System.out.println(";" + this.OpCode + " " + this.FirstOperand + " " + this.SecondOperand + " " + this.Result);
		}
	}
	
}
