package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class IntVariableDefinition extends VariableDefinition{

	private Terminal equalSign ;
	private Terminal tipo;
	private Identifier identifier;
	private Expression expression;
	
	public IntVariableDefinition(Terminal tipo, Identifier identifier, Terminal equalSign, Expression expression){
		this.tipo = tipo;
		this.identifier = identifier;
		this.equalSign = equalSign;
		this.expression = expression;
	}
	
	public Expression getExpression(){
		return this.expression;
	}
	
	@Override
	
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitIntVariableDefinition(this, arg);
	}
	
	@Override
	public Identifier getIdentifier() {
		// TODO Auto-generated method stub
		return this.identifier;
	}

}
