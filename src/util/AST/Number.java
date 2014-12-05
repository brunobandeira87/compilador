package util.AST;

import checker.SemanticException;

public class Number extends Factor{
	
	private String value;
	private String tipo;
	
	public Number(String value){
		this.tipo = "INT";
		this.value = value;
		
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public String getTipo(){
		return this.tipo;
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
	public boolean equals(Object obj) {
		if(obj instanceof Number){
			return this.tipo.equals(((Number)obj).tipo);
		}
		else if(obj instanceof Identifier){
			return this.tipo.equals(((Identifier)obj).getTipo());
		}
		else if(obj instanceof Expression){
			return this.tipo.equals(((Expression)obj).getTipo());
		}
		
		else{
			return false;
		}
	}

}
