package util.AST;

import checker.SemanticException;

public class Number extends Factor{
	
	private String value;
	
	public Number(String value){
		super.tipo = "INT";
		this.value = value;
		
	}
	
	public String getValue(){
		return this.value;
	}
	
	

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitNumber(this, arg);
	}
	
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Number){
			return this.tipo.equals(((Number)obj).tipo);
		}
		else if(obj instanceof Identifier){
			return this.tipo.equals(((Identifier)obj).tipo);
		}
		else if(obj instanceof Expression){
			return this.tipo.equals(((Expression)obj).tipo);
		}
		
		else{
			return false;
		}
	}

}
