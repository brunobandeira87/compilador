package util.AST;

public abstract class Terminal extends AST{
	
	public String spelling;
	public String value;
	
	public abstract boolean equals(Terminal term);
}
