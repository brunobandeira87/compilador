package util.AST;

import checker.SemanticException;

public class Tipo extends Terminal{

	public Tipo(String spelling, String value){
		super.spelling = spelling;
		super.value = value;
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
	@Override
	public boolean equals(Terminal obj) {
		if(this.spelling.toString().equals(obj.spelling.toString())){
			return true;
		}else{
			return false;
		}
		
	}
	
}
