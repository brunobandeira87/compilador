package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;

public class IfCommand extends Command{

	private ReservedWord reservedWord;
	private Expression expression;
	private ArrayList<Command> command;
	private ArrayList<Operator> operator;
	private ElseCommand elseCommand;
	
	
	private void setOperator(){
		this.reservedWord = new ReservedWord(TokenKind.IF.toString());
		this.operator = new ArrayList<Operator>();
		this.operator.add(new Operator("("));
		this.operator.add(new Operator(")"));
		this.operator.add(new Operator("{"));
		this.operator.add(new Operator("}"));
	}

	public IfCommand(Expression expression, ArrayList<Command> command,
			ElseCommand elseCommand) {
		this.expression = expression;
		this.command = command;
		this.elseCommand = elseCommand;
		this.setOperator();
	}
	public IfCommand(Expression expression, ElseCommand elseCommand) {
		this.expression = expression;
		this.elseCommand = elseCommand;
		this.setOperator();
	}

	public IfCommand(Expression expression, ArrayList<Command> command) {
		this.expression = expression;
		this.command = command;
		this.setOperator();
	}

	public IfCommand(Expression expression) {
		this.expression = expression;
	}
	
	public Expression getExpression(){
		return this.expression;
	}
	
	public ArrayList<Command> getCommand(){
		return this.command;
	}
	
	public ElseCommand getElse(){
		return this.elseCommand;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitIfCommand(this, arg);
	}
}
