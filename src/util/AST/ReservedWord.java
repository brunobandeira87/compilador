package util.AST;

import checker.SemanticException;

public class ReservedWord extends Terminal {
	
	
	public ReservedWord(String spelling){
		super.spelling = spelling;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return super.spelling;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitReservedWord(this, arg);
	}
	

}
