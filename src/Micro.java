import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class Micro {
    public static void main(String[] args) throws Exception 
    {
		try
		{
		    CharStream inStream = new ANTLRFileStream(args[0]);
			MicroLexer lexer = new MicroLexer(inStream);
			TokenStream tokenStream = new CommonTokenStream(lexer);
		    MicroParser parser = new MicroParser(tokenStream);
			parser.setErrorHandler(new BailErrorStrategy()); 
			parser.program();
			parser.STACK.AddTable(parser.TABLE);
			parser.STACK.PrintStack();		
			
			
		}
		catch(Exception e) 
		{
			System.out.println("Not accepted");
		}
	}
}

