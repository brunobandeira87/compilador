package util.AST;

import checker.SemanticException;

public class Tipo extends Terminal{

	public Tipo(String spelling){
		super.spelling = spelling;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitTipo(this, arg);
	}
}
