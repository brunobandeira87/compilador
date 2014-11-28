package util.AST;

import checker.SemanticException;
import scanner.TokenKind;

public class CallCommand extends Command{
	
	private ReservedWord reservedWord;
	private Identifier identifier;
	private Operator lpar;
	private ParametersCallCommand parameters;
	private Operator rpar;
	
	
	
	private void setPar(){
		this.lpar = new Operator("(");
		this.rpar = new Operator(")");
	}

	private void setReservedWord(){
		this.reservedWord = new ReservedWord(TokenKind.CALL.toString());
	}



	public CallCommand(Identifier identifier) {
		this.setPar();
		this.setReservedWord();
		
		this.identifier = identifier;
	}



	public CallCommand(Identifier identifier, ParametersCallCommand parameters) {
		
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
