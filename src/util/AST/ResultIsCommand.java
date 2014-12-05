package util.AST;

import checker.SemanticException;
import scanner.TokenKind;

public class ResultIsCommand extends Command{

	private ReservedWord reservedWord;
	private Expression expression;	
	private String tipo;
	
	
	public ResultIsCommand(String tipo, Expression expression) {
		this.reservedWord = new ReservedWord(TokenKind.RESULTIS.toString());
		this.expression = expression;
		this.tipo = tipo;
	}

	public Expression getExpression(){
		return this.expression;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitResultIsCommand(this, arg);
	}
}
