package util.AST;

import checker.SemanticException;
import scanner.TokenKind;


public class VariableGlobalDefinition extends AST{

	private ReservedWord reserverdWord;
	private VariableDefinition variableDefinition;
	private String tipo;
	
	
	public VariableGlobalDefinition(VariableDefinition variableDefinition) {
		this.reserverdWord = new ReservedWord(TokenKind.GLOBAL.toString()); 
		this.variableDefinition = variableDefinition;
		if(variableDefinition instanceof IntVariableDefinition){
			this.tipo = "INT";
		}
		else{
			this.tipo = "BOOL";
		}
	}
	
	public ReservedWord getReservedWord(){
		return this.reserverdWord;
	}
	
	public VariableDefinition getVariableDefinition(){
		return this.variableDefinition;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	@Override
	public String toString(int level) {
		String rw = reserverdWord.spelling;
		VariableDefinition var = this.variableDefinition;
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitVariableGlobalDefinition(this, arg);
	}
	
	

}
