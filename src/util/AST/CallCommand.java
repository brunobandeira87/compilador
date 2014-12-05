package util.AST;

import checker.SemanticException;
import scanner.TokenKind;

public class CallCommand extends Command{
	
	private ReservedWord reservedWord;
	private Identifier identifier;
	private Operator lpar;
	private ParametersCallCommand parameters;
	private Operator rpar;
	private String tipo;
	
	
	private void setPar(){
		this.lpar = new Operator("(");
		this.rpar = new Operator(")");
	}

	private void setReservedWord(){
		this.reservedWord = new ReservedWord(TokenKind.CALL.toString());
	}



	public CallCommand(String tipo, Identifier identifier) {
		this.setPar();
		this.setReservedWord();
		this.tipo = tipo;
		this.identifier = identifier;
	}



	public CallCommand(String tipo, Identifier identifier, ParametersCallCommand parameters) {
		this.tipo = tipo;
		this.identifier = identifier;
		this.parameters = parameters;
		this.setPar();
		this.setReservedWord();
	}


	public Identifier getIdentifier(){
		return this.identifier;
	}

	public ParametersCallCommand getParams(){
		return this.parameters;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitCallCommand(this, arg);
	}

}
