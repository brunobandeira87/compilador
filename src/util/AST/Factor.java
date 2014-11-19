package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class Factor extends ExpressionMultiplication{

	private Identifier identifier;
	private Number number;
	private Bool bool; 
	private Expression expression;
	private ParametersCallCommand parametersCallCommand;
	private Operator lpar, rpar;
	

	public Factor(Factor factorLeft, ArrayList<Operator> operadores,
			ArrayList<Factor> factorOthers, Identifier identifier) {
		super(factorLeft, operadores, factorOthers);
		this.identifier = identifier;
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores,
			ArrayList<Factor> factorOthers, Identifier identifier,
			ParametersCallCommand parametersCallCommand, Operator lpar, Operator rpar) {
		super(factorLeft, operadores, factorOthers);
		this.identifier = identifier;
		this.parametersCallCommand = parametersCallCommand;
		this.lpar = lpar;
		this.rpar = rpar;
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores,
			ArrayList<Factor> factorOthers, Number number) {
		super(factorLeft, operadores, factorOthers);
		this.number = number;
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores,
			ArrayList<Factor> factorOthers, Bool bool) {
		super(factorLeft, operadores, factorOthers);
		this.bool = bool;
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores,
			ArrayList<Factor> factorOthers, Expression expression, Operator lpar, Operator rpar) {
		super(factorLeft, operadores, factorOthers);
		this.expression = expression;
		this.lpar = lpar;
		this.rpar = rpar;
	}
	
	public Number getNumber(){
		return this.number;
	}
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return super.toString(level);
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitFactor(this, arg);
	}
	
	
	
	
	
	
	
	
}
