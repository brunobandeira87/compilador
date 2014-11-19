package util.AST;

import checker.SemanticException;

public class BoolVariableDefinition extends VariableDefinition {

	private Terminal equalSign ;
	private Terminal tipo;
	private Identifier identifier;
	private Expression expression;
	

	public BoolVariableDefinition(Terminal equalSign, Terminal tipo,
			Identifier identifier, Expression expression) {
		//super();
		this.equalSign = equalSign;
		this.tipo = tipo;
		this.identifier = identifier;
		this.expression = expression;
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
