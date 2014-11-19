package util.AST;

import checker.SemanticException;

public class Bool extends Terminal{

	
	public Bool(String spelling){
		super.spelling = spelling;
	}
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitBoolean(this, arg);
	}
	@Override
	public String toString(int level) {
		
		return null;
	}
}
