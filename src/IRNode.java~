import java.util.*;
import java.util.Collections;

class IRNodeList
{
	public List<IRNode> NodeList = new ArrayList<IRNode>();
	public List<IRNode> ReservedNodeList = new ArrayList<IRNode>();
	public List<Expr> ExprList;
	public SymbolTableStack STS;

	/* For Label Generation and Tracking */
	public int TempCounter = 1;
	public int LocalCounter = 1;

	public ArrayList<Integer> Labels = new ArrayList<>();
	public int LabelCounter = 1;
	public int LabelIndex = -1;
	public int LabelIndexRef = 0;
	public int FunctionCount = 0;
	public String CurrentFunction = "";
	

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
			if(E.id == "FUNCTION")
			{
				this.TempCounter = 1; // maybe
				this.LocalCounter = 1;
				this.FunctionCount += 1;
				String[] temp;
				temp = E.expr.split(";");
				this.CurrentFunction = temp[1];
				if(!(this.FunctionCount == 1 && temp[1].equals("main")))
				{
					this.NodeList.add(new IRNode("LABEL", temp[1]));
					this.NodeList.add(new IRNode("LINK", ""));
					/*
					for(SymbolTable ST : this.STS.SymbolTables)
					{
						if(ST.Scope.equals(temp[1]))
						{
							for(String S : ST.Parameters)
							{
								System.out.println(S);
							}
						}
					}*/
				}
			}
			if(E.id == "END_FUNCTION")
			{
				if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
				{
					
					for(SymbolTable ST : this.STS.SymbolTables)
					{
						if(ST.Scope.equals(this.CurrentFunction))
						{
							this.NodeList.add(new IRNode("STOREF", ST.ReturnValue, "$R"));
						}
					}
					//System.out.println(this.NodeList.get(list.size() - 1));
					this.NodeList.add(new IRNode("RET", ""));
					this.NodeList.add(new IRNode("", ""));
				}
			}
			if(E.id == "WRITE")
			{
				// need to accomodate having multiple variables to write in one call
				if(E.expr.indexOf(",") != -1)
				{
					String writeVars = E.expr.replaceAll("\\s","");
					List<String> writeVarsList = Arrays.asList(writeVars.split(","));
					for(String s : writeVarsList)
					{
						if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
						{
							if(SymbolLookup(s).equals("STRING"))
							{
								this.NodeList.add(new IRNode("WRITES", s));
							}
						}
						else
						{
							if(SymbolLookup(s).equals("INT"))
							{
								this.NodeList.add(new IRNode("WRITEI", s));
							}
							else if(SymbolLookup(s).equals("FLOAT"))
							{
								this.NodeList.add(new IRNode("WRITEF", s));
							}
							else if(SymbolLookup(s).equals("STRING"))
							{
								this.NodeList.add(new IRNode("WRITES", s));
							}
						}

					}
				}
				else
				{
					if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
					{
						if(SymbolLookup(E.expr).equals("STRING"))
						{
							this.NodeList.add(new IRNode("WRITES", E.expr));
						}
					}
					else
					{
						if(SymbolLookup(E.expr).equals("INT"))
						{
							this.NodeList.add(new IRNode("WRITEI", E.expr));
						}
						else if(SymbolLookup(E.expr).equals("FLOAT"))
						{
							this.NodeList.add(new IRNode("WRITEF", E.expr));
						}
						else if(SymbolLookup(E.expr).equals("STRING"))
						{
							this.NodeList.add(new IRNode("WRITES", E.expr));
						}
					}

				}
			}
			else if(E.id == "READ")
			{
				// need to accomodate having multiple variables to read in one call
				if(E.expr.indexOf(",") != -1)
				{
					String writeVars = E.expr.replaceAll("\\s","");
					List<String> writeVarsList = Arrays.asList(writeVars.split(","));
					for(String s : writeVarsList)
					{
						if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
						{
							if(SymbolLookup(E.expr).equals("INT"))
							{
								this.NodeList.add(new IRNode("READI", "$L" + String.valueOf(this.LocalCounter)));
								this.LocalCounter ++;
							}
							else if(SymbolLookup(E.expr).equals("FLOAT"))
							{
								this.NodeList.add(new IRNode("READF", "$L" + String.valueOf(this.LocalCounter)));
								this.LocalCounter ++;
							}
						}
						else
						{
							if(SymbolLookup(s).equals("INT"))
							{
								this.NodeList.add(new IRNode("READI", s));
							}
							else if(SymbolLookup(s).equals("FLOAT"))
							{
								this.NodeList.add(new IRNode("READF", s));
							}
							else if(SymbolLookup(s).equals("FLOAT"))
							{
								this.NodeList.add(new IRNode("READS", s));
							}
						}

					}
				}
				else
				{
					if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
					{
						if(SymbolLookup(E.expr).equals("INT"))
						{
							this.NodeList.add(new IRNode("READI", "$L" + String.valueOf(this.LocalCounter)));
							this.LocalCounter ++;
						}
						else if(SymbolLookup(E.expr).equals("FLOAT"))
						{
							this.NodeList.add(new IRNode("READF", "$L" + String.valueOf(this.LocalCounter)));
							this.LocalCounter ++;
						}
					}
					else
					{
						if(SymbolLookup(E.expr).equals("INT"))
						{
							this.NodeList.add(new IRNode("READI", E.expr));
						}
						else if(SymbolLookup(E.expr).equals("FLOAT"))
						{
							this.NodeList.add(new IRNode("READF", E.expr));
						}
						else if(SymbolLookup(E.expr).equals("FLOAT"))
						{
							this.NodeList.add(new IRNode("READS", E.expr));
						}
					}
				}
			
			}
			else if(E.id == "IF")
			{
				String expr = E.expr.replaceAll("\\s","");
				String op = this.FindCompOp(expr);
				int opIndex = expr.indexOf(op);
				StringBuilder exprBuilder = new StringBuilder(expr);
				String lhs = exprBuilder.substring(0, opIndex);
				String rhs = "";
				if(op.length() == 1)
				{
					rhs = exprBuilder.substring(opIndex+1, expr.length());
				}
				else
				{
					rhs = exprBuilder.substring(opIndex+2, expr.length());
				}
				//System.out.println(rhs);
				if(rhs.contains("(") && rhs.contains(")"))
				{
					//(0.0-tolerance)
					String reduced;
					StringBuilder builder = new StringBuilder(rhs);
					while(HelperFunctions.CountOccurancesOf('(', builder) != 0)
					{
						int open = builder.indexOf("(");
						int close = builder.indexOf(")");
						StringBuilder subBuilder = new StringBuilder(builder.substring(open+1,close));
						reduced = ReduceF(subBuilder);
						builder.replace(open, close+1, reduced);
						this.TempCounter -= 1;
					}
					//rhs = builder.toString();
					//System.out.println(rhs);
				}
				if(HelperFunctions.isInteger(rhs))
				{
					this.NodeList.add(new IRNode("STOREI", rhs, "$T" + String.valueOf(this.TempCounter)));
				}
				else if(HelperFunctions.IsFloat(rhs))
				{
					this.NodeList.add(new IRNode("STOREF", rhs, "$T" + String.valueOf(this.TempCounter)));
				}
				else
				{
					String s = this.SymbolLookup(rhs);
					if(s.equals("INT"))
					{
						this.NodeList.add(new IRNode("STOREI", rhs, "$T" + String.valueOf(this.TempCounter)));
					}
					else if(s.equals("FLOAT"))
					{
						this.NodeList.add(new IRNode("STOREF", rhs, "$T" + String.valueOf(this.TempCounter)));
					}
				}
				if(op == "<")
				{
					this.NodeList.add(new IRNode("GE", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.LabelCounter)));
				}
				else if(op == ">")
				{
					this.NodeList.add(new IRNode("LE", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.LabelCounter)));
				}
				else if(op == "=")
				{
					this.NodeList.add(new IRNode("NE", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.LabelCounter)));
				}
				else if(op == "!=")
				{
					this.NodeList.add(new IRNode("EQ", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.LabelCounter)));
				}
				else if(op == "<=")
				{
					this.NodeList.add(new IRNode("GT", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.LabelCounter)));
				}
				else if(op == ">=")
				{
					this.NodeList.add(new IRNode("LT", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.LabelCounter)));
				}
				this.TempCounter++;
				this.LabelIndex ++;
				this.Labels.add(this.LabelCounter);
				this.LabelCounter += 2;
			}
			else if(E.id == "ELSE")
			{
				this.NodeList.add(new IRNode("JUMP", "label" + String.valueOf(this.Labels.get(this.LabelIndex) + 1)));
				this.NodeList.add(new IRNode("LABEL", "label" + String.valueOf(this.Labels.get(this.LabelIndex))));
			}
			else if(E.id == "FI")
			{
				this.NodeList.add(new IRNode("JUMP", "label" + String.valueOf(this.Labels.get(this.LabelIndex) + 1)));
				this.NodeList.add(new IRNode("LABEL", "label" + String.valueOf(this.Labels.get(this.LabelIndex) + 1)));
				this.Labels.remove(this.LabelIndex);
				this.LabelIndex --;
			}
			else if(E.id == "FOR")
			{	
				String expr = E.expr.replaceAll("\\s","");
				String[] parts = expr.split(",");
				int lIndex = expr.indexOf(":");
				int rIndex = expr.indexOf("=");
				StringBuilder exprBuilder = new StringBuilder(parts[0]);
				String rhs = "";
				if(parts.length == 3)
				{
					
					this.ReservedNodeList.add(this.NodeList.get(this.NodeList.size() - 1));
					this.NodeList.remove(this.NodeList.size() - 1);
					this.ReservedNodeList.add(this.NodeList.get(this.NodeList.size() - 1));
					this.NodeList.remove(this.NodeList.size() - 1);
					this.ReservedNodeList.add(this.NodeList.get(this.NodeList.size() - 1));
					this.NodeList.remove(this.NodeList.size() - 1);
				}
				else
				{
					//System.out.println("no increment statement in for loop");
				}
				if(exprBuilder.toString().equals(""))
				{
					//System.out.println("Builder is empty");
					//this.ReservedNodeList.add(this.NodeList.get(this.NodeList.size() - 1));
					//this.NodeList.remove(this.NodeList.size() - 1);
				}
				else
				{
					//this.ReservedNodeList.add(this.NodeList.get(this.NodeList.size() - 1));
					//this.NodeList.remove(this.NodeList.size() - 1);
					//String lhs = exprBuilder.substring(0, lIndex);
					rhs = exprBuilder.substring(rIndex+1, parts[0].length());
				}
				this.LabelIndex ++;
				this.Labels.add(this.LabelCounter);
				this.LabelCounter += 3;
				this.NodeList.add(new IRNode("LABEL", "label" + String.valueOf(this.Labels.get(this.LabelIndex))));
				exprBuilder = new StringBuilder(parts[1]);
				String op = this.FindCompOp(parts[1]);
				int opIndex = parts[1].indexOf(op);
				String lhs = exprBuilder.substring(0, opIndex);
				if(op.length() == 1)
				{
					rhs = exprBuilder.substring(opIndex+1, parts[1].length());
				}
				else
				{
					rhs = exprBuilder.substring(opIndex+2, parts[1].length());
				}
				if(HelperFunctions.isInteger(rhs))
				{
					this.NodeList.add(new IRNode("STOREI", rhs, "$T" + String.valueOf(this.TempCounter)));
				}
				else
				{
					this.NodeList.add(new IRNode("STOREF", rhs, "$T" + String.valueOf(this.TempCounter)));
				}
				if(op == "<")
				{
					this.NodeList.add(new IRNode("GE", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.Labels.get(this.LabelIndex)+2)));
				}
				else if(op == ">")
				{
					this.NodeList.add(new IRNode("LE", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.Labels.get(this.LabelIndex)+2)));
				}
				else if(op == "=")
				{
					this.NodeList.add(new IRNode("NE", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.Labels.get(this.LabelIndex)+2)));
				}
				else if(op == "!=")
				{
					this.NodeList.add(new IRNode("EQ", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.Labels.get(this.LabelIndex)+2)));
				}
				else if(op == "<=")
				{
					this.NodeList.add(new IRNode("GT", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.Labels.get(this.LabelIndex)+2)));
				}
				else if(op == ">=")
				{
					this.NodeList.add(new IRNode("LT", lhs, "$T" + String.valueOf(this.TempCounter), "label" + String.valueOf(this.Labels.get(this.LabelIndex)+2)));
				}
				this.TempCounter++;
			}
			else if(E.id == "CONT")
			{
				this.NodeList.add(new IRNode("LABEL", "label" + String.valueOf(this.Labels.get(this.LabelIndex) + 1)));
				Collections.reverse(this.ReservedNodeList);
				while(!this.ReservedNodeList.isEmpty())
				{
					this.NodeList.add(this.ReservedNodeList.get(0));
					this.ReservedNodeList.remove(0);
				}
				this.NodeList.add(new IRNode("JUMP", "label" + String.valueOf(this.Labels.get(this.LabelIndex))));
			}
			else if(E.id == "ROF")
			{
				//System.out.println("For Loop Ended...");
				this.NodeList.add(new IRNode("LABEL", "label" + String.valueOf(this.Labels.get(this.LabelIndex) + 2)));
				this.Labels.remove(this.LabelIndex);
				this.LabelIndex --;
			}

			else // an add/sub/mult/div or assign function
			{
				if(HelperFunctions.ExpressionType(E.expr) == 0) // assign function
				{
					//System.out.println(E.expr); Liow
					if(E.expr.contains("(") && E.expr.contains(")"))
					{
						String[] temp;
						temp = E.expr.split("\\(");
						// need to push here
						this.NodeList.add(new IRNode("PUSH", ""));
						/*
						for(SymbolTable ST : this.STS.SymbolTables)
						{
							if(ST.Scope.equals(temp[0]))
							{
								for(Symbol S : ST.SYMBOLS)
								{
									System.out.println(S.var_name);
								}
							}
						}*/
						this.NodeList.add(new IRNode("JSR", temp[0]));
					}
					else if(SymbolLookup(E.id).equals("INT"))
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
							StringBuilder builder = new StringBuilder(expr);
							//System.out.println("No Parenthesis: " + builder);
							String reduced = Reduce(builder, 1);
							//System.out.println(reduced);
							this.NodeList.add(new IRNode("STOREI", reduced, E.id));
						}
						else if(HelperFunctions.CountOccurancesOf("(", expr) == 1)
						{
							StringBuilder builder = new StringBuilder(expr);
							int open = builder.indexOf("(");
							int close = builder.indexOf(")");
							StringBuilder subBuilder = new StringBuilder(builder.substring(open+1,close));
							String reduced = Reduce(subBuilder, 1);
							builder.replace(open, close+1, reduced);
							reduced = Reduce(builder, 1);
							this.NodeList.add(new IRNode("STOREI", reduced, E.id));
						}
						else if(HelperFunctions.CountOccurancesOf("(", expr) > 1)
						{
							String reduced;
							StringBuilder builder = new StringBuilder(expr);
							while(HelperFunctions.CountOccurancesOf('(', builder) != 0)
							{
								int open = builder.indexOf("(");
								int close = builder.indexOf(")");
								StringBuilder subBuilder = new StringBuilder(builder.substring(open+1,close));
								reduced = Reduce(subBuilder, 1);
								builder.replace(open, close+1, reduced);
							}
							reduced = Reduce(builder, 1);
							this.NodeList.add(new IRNode("STOREI", reduced, E.id));
						}
					}
					else if(SymbolLookup(E.id).equals("FLOAT"))
					{
						String expr = E.expr.replaceAll("\\s","");
						if(HelperFunctions.CountOccurancesOf("(", expr) == 0)
						{
							StringBuilder builder = new StringBuilder(expr);
							//System.out.println("No Parenthesis: " + builder);
							String reduced = ReduceF(builder);
							this.NodeList.add(new IRNode("STOREF", reduced, E.id));
						}
						else if(HelperFunctions.CountOccurancesOf("(", expr) == 1)
						{
							StringBuilder builder = new StringBuilder(expr);
							int open = builder.indexOf("(");
							int close = builder.indexOf(")");
							StringBuilder subBuilder = new StringBuilder(builder.substring(open+1,close));
							String reduced = ReduceF(subBuilder);
							builder.replace(open, close+1, reduced);
							reduced = ReduceF(builder);
							this.NodeList.add(new IRNode("STOREF", reduced, E.id));
						}
						else if(HelperFunctions.CountOccurancesOf("(", expr) > 1)
						{
							String reduced;
							StringBuilder builder = new StringBuilder(expr);
							while(HelperFunctions.CountOccurancesOf('(', builder) != 0)
							{
								int open = builder.indexOf("(");
								int close = builder.indexOf(")");
								StringBuilder subBuilder = new StringBuilder(builder.substring(open+1,close));
								reduced = ReduceF(subBuilder);
								builder.replace(open, close+1, reduced);
							}
							reduced = ReduceF(builder);
							this.NodeList.add(new IRNode("STOREF", reduced, E.id));
						}
					}
				}
			}
		}
	}


	/*
	*   Reduce (Integer Version) Function
	*
	*/
	public String Reduce(StringBuilder subBuilder, int order)
	{
		while(HelperFunctions.ExpressionType(subBuilder) != 0) // while expression still has an op in it
		{
			//System.out.println("Reducing: " + subBuilder);
			int multOp = subBuilder.indexOf("*");
			int divOp = subBuilder.indexOf("/");
			if(multOp != -1 && divOp == -1)
			{
				int i = multOp;
				int left = CheckLeftExpression(subBuilder, i);
				if(HelperFunctions.isInteger(subBuilder.substring(i-left, i).toString()))
				{
					this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i);
					subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
					//System.out.println(subBuilder);
					if(this.TempCounter < 10)
					{
						i += 2;
					}
					else if(this.TempCounter < 100)
					{
						i += 3;
					}
					else if(this.TempCounter < 1000)
					{
						i += 4;
					}
					i -= (left-1);
					this.TempCounter ++;
					left = CheckLeftExpression(subBuilder, i);
				}
				int right = CheckRightExpression(subBuilder, i);
				if(HelperFunctions.isInteger(subBuilder.substring(i+1, i+right).toString()))
				{
					this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i+1, i+right);
					subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
					this.TempCounter ++;
					right = CheckRightExpression(subBuilder, i);
				}
				this.NodeList.add(new IRNode("MULTI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
				subBuilder.delete(i-left, i+right);
				subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
				this.TempCounter ++;
			}
			else if(multOp == -1 && divOp != -1)
			{
				int i = divOp;
				int left = CheckLeftExpression(subBuilder, i);
				if(HelperFunctions.isInteger(subBuilder.substring(i-left, i).toString()))
				{
					this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i);
					subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
					if(this.TempCounter < 10)
					{
						i += 2;
					}
					else if(this.TempCounter < 100)
					{
						i += 3;
					}
					else if(this.TempCounter < 1000)
					{
						i += 4;
					}
					i -= (left-1);
					this.TempCounter ++;
					left = CheckLeftExpression(subBuilder, i);
				}
				int right = CheckRightExpression(subBuilder, i);
				if(HelperFunctions.isInteger(subBuilder.substring(i+1, i+right).toString()))
				{
					this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i+1, i+right);
					subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
					this.TempCounter ++;
					right = CheckRightExpression(subBuilder, i);
				}
				this.NodeList.add(new IRNode("DIVI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
				subBuilder.delete(i-left, i+right);
				subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
				this.TempCounter ++;
			}
			else if(multOp != -1 && divOp != -1)
			{
				int i = (multOp < divOp) ? multOp : divOp;
				int left = CheckLeftExpression(subBuilder, i);
				if(HelperFunctions.isInteger(subBuilder.substring(i-left, i).toString()))
				{
					this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i);
					subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
					if(this.TempCounter < 10)
					{
						i += 2;
					}
					else if(this.TempCounter < 100)
					{
						i += 3;
					}
					else if(this.TempCounter < 1000)
					{
						i += 4;
					}
					i -= (left-1);
					this.TempCounter ++;
					left = CheckLeftExpression(subBuilder, i);
				}
				int right = CheckRightExpression(subBuilder, i);
				if(HelperFunctions.isInteger(subBuilder.substring(i+1, i+right).toString()))
				{
					this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i+1, i+right);
					subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
					this.TempCounter ++;
					right = CheckRightExpression(subBuilder, i);
				}
				if(multOp < divOp)
				{
					this.NodeList.add(new IRNode("MULTI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
				}
				else
				{
					this.NodeList.add(new IRNode("DIVI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
				}
				subBuilder.delete(i-left, i+right);
				subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
				this.TempCounter ++;
			}
			else // at this point, we have no more *'s or /'s
			{
				int addOp = subBuilder.indexOf("+");
				int subOp = subBuilder.indexOf("-");
				if(addOp != -1 && subOp == -1)
				{
					int i = addOp;
					int left = CheckLeftExpression(subBuilder, i);
					if(HelperFunctions.isInteger(subBuilder.substring(i-left, i).toString()))
					{
						this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i-left, i);
						subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
						if(this.TempCounter < 10)
						{
							i += 2;
						}
						else if(this.TempCounter < 100)
						{
							i += 3;
						}
						else if(this.TempCounter < 1000)
						{
							i += 4;
						}
						i -= (left-1);
						this.TempCounter ++;
						left = CheckLeftExpression(subBuilder, i);
					}
					int right = CheckRightExpression(subBuilder, i);
					if(HelperFunctions.isInteger(subBuilder.substring(i+1, i+right).toString()))
					{
						this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i+1, i+right);
						subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
						this.TempCounter ++;
						right = CheckRightExpression(subBuilder, i);
					}
					this.NodeList.add(new IRNode("ADDI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					this.TempCounter ++;
				}
				else if(addOp == -1 && subOp != -1)
				{
					int i = subOp;
					int left = CheckLeftExpression(subBuilder, i);
					if(HelperFunctions.isInteger(subBuilder.substring(i-left, i).toString()))
					{
						//System.out.println("Number found in expression: " + subBuilder.substring(i+1, i+right));
						this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i-left, i);
						subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
						if(this.TempCounter < 10)
						{
							i += 2;
						}
						else if(this.TempCounter < 100)
						{
							i += 3;
						}
						else if(this.TempCounter < 1000)
						{
							i += 4;
						}
						i -= (left-1);
						this.TempCounter ++;
						left = CheckLeftExpression(subBuilder, i);
					}
					int right = CheckRightExpression(subBuilder, i);
					if(HelperFunctions.isInteger(subBuilder.substring(i+1, i+right).toString()))
					{
						this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i+1, i+right);
						subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
						this.TempCounter ++;
						right = CheckRightExpression(subBuilder, i);
					}
					this.NodeList.add(new IRNode("SUBI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					this.TempCounter ++;
				}
				else if(addOp != -1 && subOp != -1)
				{
					int i = (addOp < subOp) ? addOp : subOp;
					int left = CheckLeftExpression(subBuilder, i);
					if(HelperFunctions.isInteger(subBuilder.substring(i-left, i).toString()))
					{
						this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i-left, i);
						subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
						if(this.TempCounter < 10)
						{
							i += 2;
						}
						else if(this.TempCounter < 100)
						{
							i += 3;
						}
						else if(this.TempCounter < 1000)
						{
							i += 4;
						}
						i -= (left-1);
						this.TempCounter ++;
						left = CheckLeftExpression(subBuilder, i);
					}
					int right = CheckRightExpression(subBuilder, i);
					if(HelperFunctions.isInteger(subBuilder.substring(i+1, i+right).toString()))
					{
						this.NodeList.add(new IRNode("STOREI", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i+1, i+right);
						subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
						this.TempCounter ++;
						right = CheckRightExpression(subBuilder, i);
					}
					if(addOp < subOp)
					{
						this.NodeList.add(new IRNode("ADDI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					}
					else
					{
						this.NodeList.add(new IRNode("SUBI", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					}
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					this.TempCounter ++;
				}
			}
		}
		return subBuilder.toString();
	}


	/*
	*   Reduce (Float Version) Function
	*
	*/
	public String ReduceF(StringBuilder subBuilder)
	{
		while(HelperFunctions.ExpressionType(subBuilder) != 0) // while expression still has an op in it
		{
			//System.out.println("ReducingF: " + subBuilder);
			int multOp = subBuilder.indexOf("*");
			int divOp = subBuilder.indexOf("/");
			if(multOp != -1 && divOp == -1)
			{
				int i = multOp;
				int left = CheckLeftExpression(subBuilder, i);
				if(HelperFunctions.IsFloat(subBuilder.substring(i-left, i).toString()))
				{
					//System.out.println("LHS is float " + subBuilder.substring(i-left, i));
					this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i);
					subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
					//System.out.println("After reducing LHS: " + subBuilder);
					if(this.TempCounter < 10)
					{
						i += 2;
					}
					else if(this.TempCounter < 100)
					{
						i += 3;
					}
					else if(this.TempCounter < 1000)
					{
						i += 4;
					}
					i -= (left-1);
					this.TempCounter ++;
					left = CheckLeftExpression(subBuilder, i);
				}
				int right = CheckRightExpression(subBuilder, i);
				if(HelperFunctions.IsFloat(subBuilder.substring(i+1, i+right).toString()))
				{
					this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i+1, i+right);
					subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
					this.TempCounter ++;
					right = CheckRightExpression(subBuilder, i);
				}
				if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
				{
					for(SymbolTable ST : this.STS.SymbolTables)
					{
						if(ST.Scope.equals(this.CurrentFunction))
						{
							int paramIndex = 1;
							for(String S : ST.Parameters)
							{
								if(S.equals(subBuilder.substring(i-left, i)))
								{
									subBuilder.delete(i-left, i);
									subBuilder.insert(i-left, "$P"+String.valueOf(paramIndex));
									
									if(paramIndex < 10)
									{
										i += 2;
									}
									else if(paramIndex < 100)
									{
										i += 3;
									}
									else if(paramIndex < 1000)
									{
										i += 4;
									}
									i -= (left-1);
									left = CheckLeftExpression(subBuilder, i);
								}
								if(S.equals(subBuilder.substring(i+1, i+right)))
								{
									subBuilder.delete(i+1, i+right);
									subBuilder.insert(i+1, "$P"+String.valueOf(paramIndex));
									right = CheckRightExpression(subBuilder, i);
								}
								paramIndex ++;
							}
						}
					}
					this.NodeList.add(new IRNode("MULTF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter))); 
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					
				}
				else
				{
					this.NodeList.add(new IRNode("MULTF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
				}
				this.TempCounter ++;
			}
			else if(multOp == -1 && divOp != -1)
			{
				int i = divOp;
				int left = CheckLeftExpression(subBuilder, i);
				if(HelperFunctions.IsFloat(subBuilder.substring(i-left, i).toString()))
				{
					this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i);
					subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
					if(this.TempCounter < 10)
					{
						i += 2;
					}
					else if(this.TempCounter < 100)
					{
						i += 3;
					}
					else if(this.TempCounter < 1000)
					{
						i += 4;
					}
					i -= (left-1);
					this.TempCounter ++;
					left = CheckLeftExpression(subBuilder, i);
				}
				
				int right = CheckRightExpression(subBuilder, i);
				if(HelperFunctions.IsFloat(subBuilder.substring(i+1, i+right).toString()))
				{
					this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i+1, i+right);
					subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
					this.TempCounter ++;
					right = CheckRightExpression(subBuilder, i);
				}
				if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
				{
					for(SymbolTable ST : this.STS.SymbolTables)
					{
						if(ST.Scope.equals(this.CurrentFunction))
						{
							int paramIndex = 1;
							for(String S : ST.Parameters)
							{
								if(S.equals(subBuilder.substring(i-left, i)))
								{
									subBuilder.delete(i-left, i);
									subBuilder.insert(i-left, "$P"+String.valueOf(paramIndex));
									
									if(paramIndex < 10)
									{
										i += 2;
									}
									else if(paramIndex < 100)
									{
										i += 3;
									}
									else if(paramIndex < 1000)
									{
										i += 4;
									}
									i -= (left-1);
									left = CheckLeftExpression(subBuilder, i);
								}
								if(S.equals(subBuilder.substring(i+1, i+right)))
								{
									subBuilder.delete(i+1, i+right);
									subBuilder.insert(i+1, "$P"+String.valueOf(paramIndex));
									right = CheckRightExpression(subBuilder, i);
								}
								paramIndex ++;
							}
						}
					}
					this.NodeList.add(new IRNode("DIVF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter))); 
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					
				}
				else
				{
					this.NodeList.add(new IRNode("DIVF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
				}
				this.TempCounter ++;
			}
			else if(multOp != -1 && divOp != -1)
			{
				int i = (multOp < divOp) ? multOp : divOp;
				int left = CheckLeftExpression(subBuilder, i);
				if(HelperFunctions.IsFloat(subBuilder.substring(i-left, i).toString()))
				{
					this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i);
					subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
					if(this.TempCounter < 10)
					{
						i += 2;
					}
					else if(this.TempCounter < 100)
					{
						i += 3;
					}
					else if(this.TempCounter < 1000)
					{
						i += 4;
					}
					i -= (left-1);
					this.TempCounter ++;
					left = CheckLeftExpression(subBuilder, i);
				}
				//System.out.println(subBuilder);
				int right = CheckRightExpression(subBuilder, i);
				if(HelperFunctions.IsFloat(subBuilder.substring(i+1, i+right).toString()))
				{
					this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i+1, i+right);
					subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
					this.TempCounter ++;
					right = CheckRightExpression(subBuilder, i);
				}
				if(multOp < divOp)
				{
					this.NodeList.add(new IRNode("MULTF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
				}
				else
				{
					this.NodeList.add(new IRNode("DIVF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
				}
				subBuilder.delete(i-left, i+right);
				subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
				this.TempCounter ++;
			}
			else // at this point, we have no more *'s or /'s
			{
				int addOp = subBuilder.indexOf("+");
				int subOp = subBuilder.indexOf("-");
				if(addOp != -1 && subOp == -1)
				{
					int i = addOp;
					int left = CheckLeftExpression(subBuilder, i);
					if(HelperFunctions.IsFloat(subBuilder.substring(i-left, i).toString()))
					{
						this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i-left, i);
						subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
						if(this.TempCounter < 10)
						{
							i += 2;
						}
						else if(this.TempCounter < 100)
						{
							i += 3;
						}
						else if(this.TempCounter < 1000)
						{
							i += 4;
						}
						i -= (left-1);
						this.TempCounter ++;
						left = CheckLeftExpression(subBuilder, i);
					}
					int right = CheckRightExpression(subBuilder, i);
					if(HelperFunctions.IsFloat(subBuilder.substring(i+1, i+right).toString()))
					{
						this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i+1, i+right);
						subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
						this.TempCounter ++;
						right = CheckRightExpression(subBuilder, i);
					}
					
					if(!(this.FunctionCount == 1 && this.CurrentFunction.equals("main")))
					{
						for(SymbolTable ST : this.STS.SymbolTables)
						{
							if(ST.Scope.equals(this.CurrentFunction))
							{
								int paramIndex = 1;
								for(String S : ST.Parameters)
								{
									if(S.equals(subBuilder.substring(i-left, i)))
									{
										subBuilder.delete(i-left, i);
										subBuilder.insert(i-left, "$P"+String.valueOf(paramIndex));
										
										if(paramIndex < 10)
										{
											i += 2;
										}
										else if(paramIndex < 100)
										{
											i += 3;
										}
										else if(paramIndex < 1000)
										{
											i += 4;
										}
										i -= (left-1);
										left = CheckLeftExpression(subBuilder, i);
									}
									if(S.equals(subBuilder.substring(i+1, i+right)))
									{
										subBuilder.delete(i+1, i+right);
										subBuilder.insert(i+1, "$P"+String.valueOf(paramIndex));
										right = CheckRightExpression(subBuilder, i);
									}
									paramIndex ++;
								}
							}
						}
						this.NodeList.add(new IRNode("ADDF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter))); 
						subBuilder.delete(i-left, i+right);
						subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
						
					}
					else
					{
						this.NodeList.add(new IRNode("ADDF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter))); //Liow
						subBuilder.delete(i-left, i+right);
						subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					}
					this.TempCounter ++;
				}
				else if(addOp == -1 && subOp != -1)
				{
					int i = subOp;
					int left = CheckLeftExpression(subBuilder, i);
					if(HelperFunctions.IsFloat(subBuilder.substring(i-left, i).toString()))
					{
						this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i-left, i);
						subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
						if(this.TempCounter < 10)
						{
							i += 2;
						}
						else if(this.TempCounter < 100)
						{
							i += 3;
						}
						else if(this.TempCounter < 1000)
						{
							i += 4;
						}
						i -= (left-1);
						this.TempCounter ++;
						left = CheckLeftExpression(subBuilder, i);
					}
					int right = CheckRightExpression(subBuilder, i);
					if(HelperFunctions.IsFloat(subBuilder.substring(i+1, i+right).toString()))
					{
						this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i+1, i+right);
						subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
						this.TempCounter ++;
						right = CheckRightExpression(subBuilder, i);
					}
					this.NodeList.add(new IRNode("SUBF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					this.TempCounter ++;
				}
				else if(addOp != -1 && subOp != -1)
				{
					int i = (addOp < subOp) ? addOp : subOp;
					int left = CheckLeftExpression(subBuilder, i);
					if(HelperFunctions.IsFloat(subBuilder.substring(i-left, i).toString()))
					{
						this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i-left, i), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i-left, i);
						subBuilder.insert(i-left, "$T" + String.valueOf(this.TempCounter));
						if(this.TempCounter < 10)
						{
							i += 2;
						}
						else if(this.TempCounter < 100)
						{
							i += 3;
						}
						else if(this.TempCounter < 1000)
						{
							i += 4;
						}
						i -= (left-1);
						this.TempCounter ++;
						left = CheckLeftExpression(subBuilder, i);
					}
					int right = CheckRightExpression(subBuilder, i);
					if(HelperFunctions.IsFloat(subBuilder.substring(i+1, i+right).toString()))
					{
						this.NodeList.add(new IRNode("STOREF", subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
						subBuilder.delete(i+1, i+right);
						subBuilder.insert(i+1, "$T" + String.valueOf(this.TempCounter));
						this.TempCounter ++;
						right = CheckRightExpression(subBuilder, i);
					}
					if(addOp < subOp)
					{
						this.NodeList.add(new IRNode("ADDF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					}
					else
					{
						this.NodeList.add(new IRNode("SUBF", subBuilder.substring(i-left, i), subBuilder.substring(i+1, i+right), "$T" + String.valueOf(this.TempCounter)));
					}
					subBuilder.delete(i-left, i+right);
					subBuilder.insert(i-left, "$T"+String.valueOf(this.TempCounter));
					this.TempCounter ++;
				}
			}
		}
		return subBuilder.toString();
	}

	public int CheckLeftExpression(StringBuilder sb, int index)
	{
		int i = index - 1;
		int offset = 0;
		while(sb.charAt(i) != '+' && sb.charAt(i) != '-' && sb.charAt(i) != '*' && sb.charAt(i) != '/')
		{
			if(i == 0)
			{
				return offset + 1;
			}
			i --;
			offset ++;
		} 
		return offset;
	}

	public int CheckRightExpression(StringBuilder sb, int index)
	{
		int i = index + 1;
		int offset = 1;
		while(sb.charAt(i) != '+' && sb.charAt(i) != '-' && sb.charAt(i) != '*' && sb.charAt(i) != '/')
		{
			if(i == sb.length()-1)
			{
				return offset + 1;
			}
			i ++;
			offset ++;
		}
		return offset;
	}

	public String FindCompOp(String s)
	{
		// compop: '<' | '>' | '=' | '!=' | '<=' | '>=';
		int temp;

		temp = s.indexOf("<=");
		if(temp != -1)
		{
			return "<=";
		}
		temp = s.indexOf(">=");
		if(temp != -1)
		{
			return ">=";
		}
		temp = s.indexOf("<");
		if(temp != -1)
		{
			return "<";
		}
		temp = s.indexOf(">");
		if(temp != -1)
		{
			return ">";
		}
		temp = s.indexOf("!=");
		if(temp != -1)
		{
			return "!=";
		}
		temp = s.indexOf("=");
		if(temp != -1)
		{
			return "=";
		}
		return "ERROR";
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
