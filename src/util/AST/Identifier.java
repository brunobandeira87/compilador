package util.AST;

import checker.SemanticException;

public class Identifier extends Factor{

	private String tipo;
	private String value;
	private int position;
	private boolean global;
	private boolean parameter;
	//private String spelling;
		
	public Identifier(String value){
		
		this.value = value;
	}
	
	public Identifier(String value, String tipo){
		this.value = value;
		this.tipo = tipo;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		//super.getSpaces(level); 
		return this.value;
	}
	
	
	public void setParameter(boolean parameter){
		this.parameter = parameter;
	}
	
	public boolean getParameter(){
		return this.parameter;
	}
	
	public void setGlobal(boolean global){
		this.global = global;
	}
	
	public boolean getGlobal(){
		return this.global;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	public int getPosition(){
		return this.position;
	}
	
	public void setPosition(int position){
		this.position = position; 
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitIdentifier(this, arg);
	}
	
	
	public String getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Identifier){
			return this.tipo.equals(((Identifier)obj).tipo);
		}else{
			return false;
		}
	}

	
}

