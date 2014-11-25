package util.AST;

import checker.SemanticException;

public class Bool extends Factor{

	private String value;
	
	public Bool(String value){
		//super.spelling = spelling;
		//super.value = value;
		this.value = value;
		this.tipo = "BOOL";
	}
	
	public String getValue(){
		return this.value;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitBoolean(this, arg);
	}
	@Override
	public String toString(int level) {
		
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Bool){
			return this.tipo.equals(((Bool)obj).tipo);
		}else{
			return false;
		}
	}
	
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}

}
