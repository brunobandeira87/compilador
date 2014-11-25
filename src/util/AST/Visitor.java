package util.AST;

import checker.SemanticException;

public interface Visitor {
	
	public Object visitProgram(Program program, Object arg) throws SemanticException;
	public Object visitVariableGlobalDefinition(VariableGlobalDefinition var, Object arg) throws SemanticException;
	public Object visitFunctionProcedureDefinitionList(FunctionProcedureDefinitionList funcProcDef, Object arg) throws SemanticException;
	public Object visitIntVariableDefinition(IntVariableDefinition intVarDef, Object arg) throws SemanticException;
	public Object visitBoolVariableDefinition(BoolVariableDefinition boolVarDef, Object arg) throws SemanticException;
	public Object visitFunctionDefintion(FunctionDefinition funcDef, Object arg) throws SemanticException;
	public Object visitProcedureDefinition(ProcedureDefinition procDef, Object arg) throws SemanticException;
	public Object visitParametersPrototype(ParametersPrototype params, Object arg) throws SemanticException;
	//public Object visitCommand(Command cmd, Object arg) throws SemanticException;
	public Object visitAssignmentCallCommand(AssignmentCallCommand assign, Object arg) throws SemanticException;
	public Object visitAssignmentCommand(AssignmentCommand assign, Object arg) throws SemanticException;
	public Object visitCallCommand(CallCommand callCmd, Object arg) throws SemanticException;
	public Object visitContinueCommand(ContinueCommand continueCmd, Object arg) throws SemanticException;
	public Object visitBreakCommand(BreakCommand breakCmd, Object arg) throws SemanticException;
	public Object visitPrintCommand(PrintCommand printCmd, Object arg) throws SemanticException;
	public Object visitIfCommand(IfCommand ifCmd, Object arg) throws SemanticException;
	public Object visitElseCommand(ElseCommand elseCmd, Object arg) throws SemanticException;
	public Object visitParametersCallCommand(ParametersCallCommand params, Object arg) throws SemanticException;
	public Object visitResultIsCommand(ResultIsCommand resultCmd, Object arg) throws SemanticException;
	public Object visitWhileCommand(WhileCommand whileCmd, Object arg) throws SemanticException;
	public Object visitExpression(Expression expression, Object arg) throws SemanticException;
	public Object visitExpressionArithmetic(ExpressionArithmetic expAri, Object arg) throws SemanticException;
	public Object visitExpressionMultiplication(ExpressionMultiplication expMul, Object arg) throws SemanticException;
	public Object visitFactor(Factor factor, Object arg) throws SemanticException;
	public Object visitIdentifier(Identifier identifier, Object obj) throws SemanticException;
	public Object visitNumber(Number number, Object obj) throws SemanticException;
	public Object visitBoolean(Bool bool, Object obj) throws SemanticException;
	public Object visitOperator(Operator operator, Object obj) throws SemanticException;
	public Object visitReservedWord(ReservedWord reservedWord, Object obj) throws SemanticException;
	public Object visitTipo(Tipo tipo, Object obj) throws SemanticException;

}
