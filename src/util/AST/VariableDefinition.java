package util.AST;

public abstract class VariableDefinition  extends AST {

	public boolean global; 
	public int position;
	public abstract Identifier getIdentifier();
	
	//public abstract String getTipo();
}
