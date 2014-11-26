package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;

// 'VOID' Identifier '(' parametersPrototype? ')' 'BE' '{' varDefinition* command* '}'

public class ProcedureDefinition extends CallableDefinition{

	public String tipo;
	private Identifier identifier;
	private ArrayList<Terminal> terminal;
	
	private ArrayList<VariableDefinition> variableDefinition;
	private ArrayList<Command> command;
	private ParametersPrototype params;
	
	private void setOperator(){
		this.tipo = "VOID";
		this.terminal = new ArrayList<Terminal>();
		this.terminal.add( new Operator("("));
		this.terminal.add( new Operator(")"));
		this.terminal.add( new ReservedWord(TokenKind.BE.toString()));
		this.terminal.add( new Operator("{"));
		this.terminal.add( new Operator("}"));
	}

	public ProcedureDefinition(Identifier identifier,
			ArrayList<Command> command, ParametersPrototype params) {
		this.identifier = identifier;
		this.command = command;
		this.params = params;
		this.setOperator();
	}

	public ProcedureDefinition(Identifier identifier, ParametersPrototype params) {

		this.identifier = identifier;
		this.params = params;
		this.setOperator();
	}


	public ProcedureDefinition(Identifier identifier) { 
		this.identifier = identifier;
		this.setOperator();
	}




	public ProcedureDefinition(Identifier identifier,
			ArrayList<VariableDefinition> variableDefinition,
			ArrayList<Command> command, ParametersPrototype params) {
		this.identifier = identifier;
		this.variableDefinition = variableDefinition;
		this.command = command;
		this.params = params;
		this.setOperator();
	}
	
	public ProcedureDefinition(Identifier identifier, ParametersPrototype params, ArrayList<VariableDefinition> variableDefinitions){
		this.identifier = identifier;
		this.params = params;
		this.variableDefinition = variableDefinitions;
		this.setOperator();
	}

	public ProcedureDefinition(Identifier identifier,
			ArrayList<VariableDefinition> variableDefinition,
			ArrayList<Command> command) {
		this.identifier = identifier;
		this.variableDefinition = variableDefinition;
		this.command = command;
		this.setOperator();
	}

	public String getTipo(){
		return this.tipo;
	}
	
	public ArrayList<VariableDefinition> getVariableDefinition(){
		return this.variableDefinition;
	}
	
	public ArrayList<Command> getCommands(){
		return this.command;
	}
	
	@Override
	public Identifier getIdentifier(){
		return this.identifier;
	}
	
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitProcedureDefinition(this, arg);
	}
}
