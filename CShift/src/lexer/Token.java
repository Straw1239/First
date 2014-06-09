package lexer;

public class Token
{
	public String info;
	public Symbol type;
	
	public Token(Symbol s, String string)
	{
		type = s;
		info = string;
	}
}
