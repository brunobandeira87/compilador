package checker;

import java.util.ArrayList;

import util.AST.*;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;


public final class Checker implements Visitor {
	
	private IdentificationTable identificationTable;
	

	public void check(AST ast) throws SemanticException{
		this.identificationTable = new IdentificationTable();
		ast.visit(this,null);
	}
	
	
	public Object visitProgram(Program program, Object arg) throws SemanticException {
		
		if(program.getVariableGlobalDefinition() != null){
			for(VariableGlobalDefinition var : program.getVariableGlobalDefinition()){
				var.visit(this, null);
			}
		}
		
		
		for(CallableDefinition call : program.getFunctionProcedureDefinitionList().getCallableDefinition()){
			if(call instanceof ProcedureDefinition){
				this.identificationTable.enter(call.getIdentifier().spelling, call);
				call.visit(this, null);
			}
		}
		
		return program;
	}

	public Object visitVariableGlobalDefinition(VariableGlobalDefinition var, Object arg) throws SemanticException {
		
		if(this.identificationTable.getCurrentScope() == 0){
			if(!this.identificationTable.containsKey(var.getVariableDefinition().getIdentifier().toString())){
				this.identificationTable.enter(var.getVariableDefinition().getIdentifier().toString(), var);
			}
			else{
				throw new SemanticException("Variable already declared.");
			}
			
		}
		else{
			throw new SemanticException("It is not possible to declare global variable outside the outtermost block");
		}
		
		return null;
	}

	public Object visitFunctionProcedureDefinitionList(	FunctionProcedureDefinitionList funcProcDef, Object arg) throws SemanticException {
		
		
		for(CallableDefinition callTemp : funcProcDef.getCallableDefinition()){
			callTemp.visit(this, null);
		}
		
		return null;
	}

	public Object visitIntVariableDefinition(IntVariableDefinition intVarDef, Object arg) throws SemanticException {

		this.identificationTable.enter(intVarDef.getIdentifier().spelling, intVarDef);
		Expression e = intVarDef.getExpression();
	
		if(e != null){
			e.visit(this, null);
		}
		
		
		return intVarDef;
	}

	public Object visitBoolVariableDefinition(BoolVariableDefinition boolVarDef, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitFunctionDefintion(FunctionDefinition funcDef, Object arg) throws SemanticException {
			if(!this.identificationTable.containsKey(funcDef.getIdentifier().toString())){
				this.identificationTable.enter(funcDef.getIdentifier().toString(), funcDef);
				this.identificationTable.openScope();
			}
			else{
				
			}
		return null;
	}

	public Object visitProcedureDefinition(ProcedureDefinition procDef, Object arg) throws SemanticException {
		
		this.identificationTable.openScope();
		
		for(VariableDefinition var : procDef.getVariableDefinition()){
			if(var instanceof IntVariableDefinition){
				var.visit(this, null);
			}
		}
		
		return procDef;
	}

	public Object visitParametersPrototype(ParametersPrototype params,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitAssignmentCommand(AssignmentCommand assign, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitCallCommand(CallCommand callCmd, Object arg)	throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitContinueCommand(ContinueCommand continueCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBreakCommand(BreakCommand breakCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitPrintCommand(PrintCommand printCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitIfCommand(IfCommand ifCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitElseCommand(ElseCommand elseCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitParametersCallCommand(ParametersCallCommand params,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitResultIsCommand(ResultIsCommand resultCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitWhileCommand(WhileCommand whileCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitExpression(Expression expression, Object arg) throws SemanticException {
		
		ExpressionArithmetic expAr = expression.getExpressionArithmeticLeft();
		
		expAr.visit(this, null);
		
				
		return expression;
	}

	public Object visitExpressionArithmetic(ExpressionArithmetic expAri, Object arg) throws SemanticException {
		
		ExpressionMultiplication expMul = expAri.getExpressionMultiplicationLeft();
		expMul.visit(this, null);

		return expAri;
	}

	public Object visitExpressionMultiplication(ExpressionMultiplication expMul, Object arg) throws SemanticException {
		
		Factor f = expMul.getFactorLeft();
		f.visit(this, null);

		return expMul;
	}

	public Object visitFactor(Factor factor, Object arg) throws SemanticException {
		
		if(factor.getNumber() != null){
			Number num = factor.getNumber();
			num.visit(this, null);
			return num;
		}
		
		
		return null;
	}

	public Object visitIdentifier(Identifier identifier, Object obj)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitNumber(Number number, Object obj) throws SemanticException {

		return number.spelling;
	}

	public Object visitBoolean(Bool bool, Object obj) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitOperator(Operator operator, Object obj)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitReservedWord(ReservedWord reservedWord, Object obj)	throws SemanticException {
		
		
		
		
		return null;
	}

	public Object visitTipo(Tipo tipo, Object obj) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}


}
