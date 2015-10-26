//java -cp lib/antlr.jar:classes/ Micro inputs/step4_testcase3.micro
// Step 4 reference: http://media.pragprog.com/titles/tpantlr2/code/tour/ExtractInterfaceTool.java

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/*
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.*;
*/
import java.io.FileInputStream;
import java.io.InputStream;

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
			IRNodeList irlist = new IRNodeList(parser.EI);
			//irlist.PrintExprList();
			


			//ParseTree ptree = parser.program();	
			//ParseTreeWalker walker = new ParseTreeWalker(); 
		    //ExtractInterfaceListener extractor = new ExtractInterfaceListener(parser);
			//IRExtractor IRE = new IRExtractor(parser.symbolTree);
		    //walker.walk(listener, ptree); // initiate walk of tree with listener
			//parser.STACK.AddTable(parser.TABLE);
			//parser.STACK.PrintStack();		
			
		}
		catch(Exception e) 
		{
			System.out.println("DECLARATION ERROR n");
		}
	}
}

