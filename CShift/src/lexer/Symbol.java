package lexer;

import java.util.Scanner;
import java.util.regex.Pattern;
import static lexer.Patterns.*;

public enum Symbol
{
	
	SEMICOLON (";"), OPENPAREN("("), CLOSEPAREN(")"), OPENSQUARE("["), CLOSESQUARE("]"),OPENBRACE("{"), CLOSEBRACE("}"), ASSIGN("="), EQUALS("=="), NOTEQUAL("!="), LESS("<"), GREATER(">"), 
	GOREQUAL(">="), LOREQUAL("<="), PLUS("+"), MINUS("-"), PLUSPLUS("++"), MINUSMINUS("--"), PLUSEQUALS("+="), MINUSEQUALS("-="), TIMES("*"), SLASH("/"), TIMESEQUALS("*="), DIVIDEDEQUALS("/="),
	NAME("[\\w&&[^0-9][\\w]*"),MODULO("%"), MODULOEQUALS("%="), DOT("\\."), RIGHTARROW("->"), OR("\\|\\|"), AND("&&"), XOR("^^"), NOT("!"), RIGHTSHIFT(">>"), LEFTSHIFT("<<"), RIGHTSHIFTEQUALS(">>="),
	LEFTSHIFTEQUALS("<<="), BITXOR("^"), BITXOREQUALS("^="), BITNOT("~"), BITOR("\\|"), BITOREQUALS("\\|="), BITNOTEQUALS("~="), BITAND("&"), BITANDEQUALS("&="), NEW("new"), DELETE("delete"), 
	PUBLIC("public"), PRIVATE("private"), PROTECTED("protected"), STATIC("static"), CLASS("class"), COLON(":"), COLONCOLON("::"), STRUCT("struct"), INT("int"), LONG("long"),
	SHORT("short"), CHAR("char"), BYTE("byte"), BOOL("bool"),FLOAT("float"), DOUBLE("double"), FOR("for"), WHILE("while"), DO("do"), IF("if"), ELSE("else"), 
	SWITCH("switch"), CASE("case"), DEFAULT("default"), VOID("void"), ENUM("enum"), INTERFACE("interface"), CONST("const"), IMMUTABLE("immutable"), VOLATILE("volatile"), UNION("union"), 
	TEMPLATE("template"), ASSERT("assert"), TRUE("true"), FALSE("false"), MUTABLE("mutable"), BREAK("break"), CONTINUE("continue"), VIRTUAL("virtual"),
	TRY("try"), CATCH("catch"), AUTO("auto"), NULL("null"), OPERATOR("operator"), THIS("this"), SUPER("super"), FINALLY("finally"), RETURN("return"), COMMA(","), 
	INTLITERAL(INT_PATTERN), LONGLITERAL(LONG_PATTERN), STRINGLITERAL(STRING_PATTERN), FLOATLITERAL(FLOAT_PATTERN), DOUBLELITERAL(DOUBLE_PATTERN), CHARLITERAL(CHAR_PATTERN);
	
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
