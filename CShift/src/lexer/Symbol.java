package lexer;

import java.nio.file.Path;
import java.util.regex.Pattern;

public enum Symbol
{
	SEMICOLON (";"), OPENPAREN("("), CLOSEPAREN(")"), OPENSQUARE("["), CLOSESQUARE("]"),OPENBRACE("{"), CLOSEBRACE("}"), ASSIGN("="), EQUALS("=="), NOTEQUAL("!="), LESS("<"), GREATER(">"), 
	GOREQUAL(">="), LOREQUAL("<="), PLUS("+"), MINUS("-"), PLUSPLUS("++"), MINUSMINUS("--"), PLUSEQUALS("+="), MINUSEQUALS("-="), TIMES("*"), SLASH("/"), TIMESEQUALS("*="), DIVIDEDEQUALS("/="),
	NAME("[\\w&&[^0-9][\\w]*"),MODULO("%"), MODULOEQUALS("%="), DOT, RIGHTARROW, OR, AND, XOR, NOT, RIGHTSHIFT, LEFTSHIFT, RIGHTSHIFTEQUALS, LEFTSHIFTEQUALS, BITXOR, BITXOREQUALS, BITNOT, BITOR, BITOREQUALS, BITNOTEQUALS, 
	BITAND, RVALUE, BITANDEQUALS, NEW, DELETE, PUBLIC, PRIVATE, PROTECTED, STATIC, CLASS, COLON, COLONCOLON, STRUCT, INT, LONG, SHORT, CHAR, BYTE, BOOL, FLOAT, DOUBLE,
	FOR, WHILE, DO, IF, ELSE, SWITCH, CASE, DEFAULT, VOID, ENUM, INTERFACE, CONST, IMMUTABLE, VOLATILE, UNION, TEMPLATE, ASSERT, TRUE, FALSE, MUTABLE, BREAK, CONTINUE, VIRTUAL,
	TRY, CATCH, AUTO, NULL, OPERATOR, THIS, SUPER, FINALLY, RETURN, COMMA, INTLITERAL, STRINGLITERAL, FLOATLITERAL, CHARLITERAL;
	
	public final Pattern syntax;
	
	private Symbol(Pattern p)
	{
		syntax = p;
		
	}
	
	private Symbol(String pattern)
	{
		syntax = Pattern.compile(pattern);
	}
	public boolean hasInfo()
	{
		Path p;
		
		switch(this)
		{
		case NAME:
		case DOT:
		case RIGHTARROW:
		case INTLITERAL:
		case STRINGLITERAL:
		case FLOATLITERAL:
		case CHARLITERAL:
			return true;
		default: return false;
		}
		
	}
	
	
}
