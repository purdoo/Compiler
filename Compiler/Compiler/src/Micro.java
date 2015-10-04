import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


public class Micro {
    public static void main(String[] args) throws Exception 
    {
		CharStream inStream = new ANTLRFileStream(args[0]);
		MicroLexer lexer = new MicroLexer(inStream);
		    Token token = lexer.nextToken();
		String tokenName = "";
		while(token.getType() != -1 || token.getType() != MicroLexer.EOF) 
		{
			System.out.println("Token Type: " + MicroLexer.tokenNames[token.getType()]);
		    System.out.println("Value: " + token.getText());
			token = lexer.nextToken();
	    }
		System.out.println("");
	    //System.exit(0);
	}
}

