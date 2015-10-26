/* Helper/Utility Functions for my Compiler */

public class HelperFunctions
{
	public static int ExpressionType(String expr)
	{
		/* return 0 for an assign expression
		 * 
		 *
		 */
		if(CountOccurancesOf("+", expr) == 0 && CountOccurancesOf("-", expr) == 0 && CountOccurancesOf("*", expr) == 0 && CountOccurancesOf("/", expr) == 0)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

	public static int CountOccurancesOf(String s, String line)
	{
		return (line.length() - line.replace(s, "").length());
	}
}
