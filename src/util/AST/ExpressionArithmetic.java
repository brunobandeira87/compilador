package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class ExpressionArithmetic extends AST{

	private ExpressionMultiplication expressionMultiplicationLeft;
	private ArrayList<Operator> operadores ;
	private ArrayList<ExpressionMultiplication> expressionMultiplicationOthers;

	public ExpressionMultiplication getExpressionMultiplicationLeft() {
		return expressionMultiplicationLeft;
	}

	public ArrayList<Operator> getOperadores() {
		return operadores;
	}

	public ArrayList<ExpressionMultiplication> getExpressionMultiplicationOthers() {
		return expressionMultiplicationOthers;
	}

	public ExpressionArithmetic(ExpressionMultiplication expressionMultiplicationLeft,
			ArrayList<Operator> operadores,
			ArrayList<ExpressionMultiplication> expressionMultiplicationOthers) {
		this.expressionMultiplicationLeft = expressionMultiplicationLeft;
		this.operadores = operadores;
		this.expressionMultiplicationOthers = expressionMultiplicationOthers;
		
	}

	public ExpressionArithmetic(ExpressionMultiplication expressionMultiplicationLeft) {
		this.expressionMultiplicationLeft = expressionMultiplicationLeft;
		super.tipo = this.expressionMultiplicationLeft.tipo;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitExpressionArithmetic(this, arg);
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
}
