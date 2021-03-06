package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;
import sun.org.mozilla.javascript.internal.ast.VariableDeclaration;

public class FunctionDefinition extends CallableDefinition{

	private String tipo;
	private ArrayList<Terminal> terminal = new ArrayList<Terminal>();
	private Identifier identifier;
	private ParametersPrototype parameters; 
	private ArrayList<Command> command;
	private ArrayList<VariableDefinition> variableDefinition;
	private boolean hasReturn;
	
	public FunctionDefinition(String tipo, Identifier identifier){

		this.setTerminal();
		this.identifier = identifier;
		this.tipo = tipo;
	}
	
	public FunctionDefinition(String tipo, Identifier identifier, ParametersPrototype parameters){
		this.setTerminal();
		this.tipo = tipo;
		
		this.identifier = identifier;
		this.parameters = parameters;
	}
	
	public void setHasReturn(){
		this.hasReturn = true;
	}
	
	public boolean getHasReturn(){
		return this.hasReturn;
	}
	
	public FunctionDefinition(String tipo, 	Identifier identifier, ParametersPrototype parameters, ArrayList<Command> command,
			ArrayList<VariableDefinition> variableDefinition) {
		this.tipo = tipo;
		
		this.identifier = identifier;
		this.parameters = parameters;
		this.command = command;
		this.variableDefinition = variableDefinition;
		this.setTerminal();
	}

	public FunctionDefinition(Identifier identifier,ParametersPrototype parameters,ArrayList<VariableDefinition> variableDefinition,
			ArrayList<Command> command) {
		this.identifier = identifier;
		this.parameters = parameters;
		this.variableDefinition = variableDefinition;
		this.command = command;
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
	
	public String getTipo(){
		return this.tipo;
	}
	
	public ArrayList<VariableDefinition> getVariable(){
		return this.variableDefinition;
	}
	
	public ArrayList<Command> getCommand(){
		return this.command;
	}
	
	public ParametersPrototype getParams(){
		return this.parameters;
	}
	
	public void setParams(ParametersPrototype params){
		this.parameters = params;
		
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
