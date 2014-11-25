package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public abstract class Factor extends AST{
	/*
	private Identifier identifier;
	private Number number;
	private Expression expression;
	private CallCommand callcomand;
	private Bool bool;
	
	public void setIdentifier(Identifier identifier){
		this.identifier = identifier;
	}
	
	public void setNumber(Number number){
		this.number = number;
	}
	
	public void setExpression(Expression expression){
		this.expression = expression;
	}
	
	public void setCallCommand(CallCommand callcommand){
		this.callcomand = callcommand;
	}
	
	public void setBool(Bool bool){
		this.bool = bool;
	}
	
/*
	private Identifier identifier;
	private Number number;
	private Bool bool; 
	private Expression expression;
	private CallCommand callCommand;
	private ArrayList<Operator> par = new ArrayList<Operator>();
	

	public Factor(Factor factorLeft, ArrayList<Operator> operadores, ArrayList<Factor> factorOthers, Identifier identifier) {
		super(factorLeft, operadores, factorOthers);
		this.identifier = identifier;
		super.tipo = this.identifier.spelling;
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores, ArrayList<Factor> factorOthers,CallCommand callCommand) {
		super(factorLeft, operadores, factorOthers);
		this.callCommand = callCommand;
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores, ArrayList<Factor> factorOthers, Number number) {
		super(factorLeft, operadores, factorOthers);
		this.number = number;
		super.tipo = this.number.getTipo();
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores, ArrayList<Factor> factorOthers, Bool bool) {
		super(factorLeft, operadores, factorOthers);
		this.bool = bool;
		super.tipo = this.bool.getTipo();
	}


	public Factor(Factor factorLeft, ArrayList<Operator> operadores, ArrayList<Factor> factorOthers, Expression expression) {
		super(factorLeft, operadores, factorOthers);
		this.expression = expression;
		this.setPar();
	}
	
	public Number getNumber(){
		return this.number;
	}
	
	public Bool getBool(){
		return this.bool;
	}
	
	private void setPar(){
		this.par.add(new Operator("LPAR", "("));
		this.par.add(new Operator("RPAR", ")"));
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
	
	
	
	
	
	
	*/

	
	public abstract String getTipo();

	public abstract boolean equals(Object obj) ;
	
}
