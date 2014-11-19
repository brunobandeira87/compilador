package util.AST;

import checker.SemanticException;

public class Identifier extends Terminal{

	
	public Identifier(String spelling){
		super.spelling = spelling;
	}
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		//super.getSpaces(level); 
		return super.spelling;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitIdentifier(this, arg);
	}
}

