package util.AST;

import checker.SemanticException;

import java.util.ArrayList;

public class Expression extends Factor{

	private ExpressionArithmetic left;
	private ExpressionArithmetic right;
	private Operator operator;
	
	
	public Expression(ExpressionArithmetic left){
		this.left = left;
	}
	public Expression(ExpressionArithmetic left, Operator operator, ExpressionArithmetic right){
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	public ExpressionArithmetic getLeft(){
		return this.left;
	}
	
	public ExpressionArithmetic getRight(){
		return this.right;
	}
	
	public Operator getOperator(){
		return this.operator;
	}
	
	
	//private String tipo;
	/*
	private ArrayList<ExpressionArithmetic> expressionArithmetic;
	private ArrayList<Operator> operator;
	
	public ArrayList<ExpressionArithmetic> getExpressionArithmeticLeft() {
		return this.expressionArithmetic;
	}

	public ArrayList<Operator> getOperator() {
		return operator;
	}
	
	public Expression(ArrayList<ExpressionArithmetic> expressionArithmetic, ArrayList<Operator> operator){
		this.expressionArithmetic = expressionArithmetic;
		this.operator = operator;
	}

/*
	public Expression(ExpressionArithmetic expressionArithmeticLeft, Operator operator, ExpressionArithmetic expressionArithmeticRight) {
		this.expressionArithmeticLeft = expressionArithmeticLeft;
		this.operator = new ArrayList<Operator>();
		this.operator.add(operator);
		this.expressionArithmeticRight = expressionArithmeticRight;
		super.tipo = this.expressionArithmeticLeft.tipo;
	}
	public Expression(ExpressionArithmetic expressionArithmeticLeft) {
		this.expressionArithmeticLeft = expressionArithmeticLeft;
		super.tipo = this.expressionArithmeticLeft.tipo;
	}
	
	public Expression(ExpressionArithmetic expressionArithmeticLeft, ArrayList<Operator> operator, ExpressionArithmetic expressionArithmeticRight) {
		this.expressionArithmeticLeft = expressionArithmeticLeft;
		this.operator = new ArrayList<Operator>();
		this.operator = (operator);
		this.expressionArithmeticRight = expressionArithmeticRight;
		super.tipo = this.expressionArithmeticLeft.tipo;
	}
	*/
	
	@Override
	public String toString(int level) {
		
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitExpression(this, arg);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Expression){
			return this.tipo.equals(((Expression)obj).tipo);
		}else{
			return false;
		}

	}
	
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}
	
}
