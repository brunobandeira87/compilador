package util.AST;

import checker.SemanticException;

public class BoolVariableDefinition extends VariableDefinition {

	private Operator equalSign ;
	private String tipo;
	private Identifier identifier;
	private Expression expression;
	

	public BoolVariableDefinition(Identifier identifier, Expression expression) {
		//super();
		this.equalSign = new Operator("=");
		
		this.tipo = "BOOL";
		this.identifier = identifier;
		this.expression = expression;
	}
	public String getTipo(){
		return this.tipo;
	}
	
	public Expression getExpression(){
		return this.expression;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	
	@Override
	public Identifier getIdentifier(){
		return this.identifier;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {

		return v.visitBoolVariableDefinition(this, arg);
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
