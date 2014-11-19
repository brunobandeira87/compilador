package util.AST;

import checker.SemanticException;

public class Operator extends Terminal{
	
	public Operator(String spelling){
		super.spelling = spelling;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitOperator(this, arg);
	}

}
