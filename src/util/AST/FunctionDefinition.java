package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;

public class FunctionDefinition extends CallableDefinition{

	private Tipo tipo;
	private ArrayList<Terminal> terminal = new ArrayList<Terminal>();
	private Identifier identifier;
	private ParametersPrototype parameters; 
	
	
	public FunctionDefinition(Tipo tipo, Identifier identifier){

		this.setTerminal();
		this.identifier = identifier;
		this.tipo = tipo;
	}
	
	public FunctionDefinition(Tipo tipo, Identifier identifier, ParametersPrototype parameters){
		this.setTerminal();
		this.tipo = tipo;
		
		this.identifier = identifier;
		this.parameters = parameters;
	}
	
	private void setTerminal(){
		
		this.terminal.add(new Operator("("));
		this.terminal.add(new Operator(")"));
		this.terminal.add(new Operator("="));
		this.terminal.add(new ReservedWord(TokenKind.VALOF.toString()));
		this.terminal.add(new Operator("{"));
		this.terminal.add(new Operator("}"));
		
	}
	
	public Identifier getIdentifier(){
		return this.identifier;
	}
	
	public Tipo getTipo(){
		return this.tipo;
	}
	
	@Override
	public String toString(int level) {
		StringBuffer st = new StringBuffer();
		
		
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitFunctionDefintion(this, arg);
	}
}
