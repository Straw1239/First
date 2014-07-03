package lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lexer
{
	
	public static void main(String[] args)
	{
		Reader r = new StringReader("/*15*/12346hello");
		lex(r);
	}
	
	public static List<Token> lex(Scanner source)
	{
		List<Token> tokens = new ArrayList<>();
		
		return null;
	}
	
	public static List<Token> lex(Reader source)
	{
		List<Token> tokens = new ArrayList<>();
		StreamTokenizer first = new StreamTokenizer(source);
		first.slashSlashComments(true);
		first.slashStarComments(true);
		first.eolIsSignificant(false);
		first.parseNumbers();
		
		StringBuilder b = new StringBuilder();
		while(first.ttype != StreamTokenizer.TT_EOF)
		{
			int c;
			try
			{
				c = first.nextToken();
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
			switch (c)
			{
			case StreamTokenizer.TT_WORD:
				b.append(first.sval); break;
			case StreamTokenizer.TT_NUMBER:
				b.append(first.nval); break;
			
			}
		}
		System.out.println(b);
		return tokens;
	}
}
