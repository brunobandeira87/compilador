package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;

public class ElseCommand extends Command {
	
	private ReservedWord reservedWord;
	private ArrayList<Operator> operator;
	private ArrayList<Command> command;
	private int scope;
	//private Expression expression;
	
	
	private void setOperator(){
		this.reservedWord = new ReservedWord(TokenKind.ELSE.toString());
		this.operator = new ArrayList<Operator>();
		this.operator.add(new Operator("("));
		this.operator.add(new Operator(")"));
		this.operator.add(new Operator("{"));
		this.operator.add(new Operator("}"));
	}
	
	public ElseCommand(ArrayList<Command> command) {
		this.command = command;
		//this.expression = expression;
		this.setOperator();
	}
	
	public ArrayList<Command> getCommand(){
		return this.command;
	}

	public void setScope(int scope){
		this.scope = scope;
		
	}
	
	public int getScope(){
		return this.scope;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {		
		return v.visitElseCommand(this, arg);
	}

}
