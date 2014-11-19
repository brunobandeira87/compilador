package util.AST;

import checker.SemanticException;

public class Expression extends AST{
	private ExpressionArithmetic expressionArithmeticLeft;
	private Operator Operator;
	private ExpressionArithmetic expressionArithmeticRight;
	

	public ExpressionArithmetic getExpressionArithmeticLeft() {
		return expressionArithmeticLeft;
	}

	public Operator getOperator() {
		return Operator;
	}

	public ExpressionArithmetic getExpressionArithmeticRight() {
		return expressionArithmeticRight;
	}

	public Expression(ExpressionArithmetic expressionArithmeticLeft,
			Operator Operator, ExpressionArithmetic expressionArithmeticRight) {
		this.expressionArithmeticLeft = expressionArithmeticLeft;
		this.Operator = Operator;
		this.expressionArithmeticRight = expressionArithmeticRight;
	}
	public Expression(ExpressionArithmetic expressionArithmeticLeft) {
		this.expressionArithmeticLeft = expressionArithmeticLeft;
	}
	
	@Override
	public String toString(int level) {
		
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitExpression(this, arg);
	}

	
}
