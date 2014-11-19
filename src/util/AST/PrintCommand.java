package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;

public class PrintCommand extends Command{
	
	private ReservedWord reservedWord;
	private Expression expression;
	private ArrayList<Operator> operator;
	
	void setOperator(){
		this.reservedWord = new ReservedWord(TokenKind.WRITEF.toString());
		this.operator = new ArrayList<Operator>();
		this.operator.add(new Operator("("));
		this.operator.add(new Operator("\""));
		this.operator.add(new Operator("\""));
		this.operator.add(new Operator(")"));
	}
	
	public PrintCommand(Expression expression) {
		this.setOperator();
		this.expression = expression;
		
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitPrintCommand(this, arg);
	}

}
