package checker;

import java.util.ArrayList;
import java.util.Vector;

import util.AST.*;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;


public final class Checker implements Visitor {
	
	private IdentificationTable identificationTable;
	private Vector<Object> reference;
	

	public void check(AST ast) throws SemanticException{
		this.identificationTable = new IdentificationTable();
		ast.visit(this,null);
	}
	
	public Checker(){
		this.reference = new Vector<Object>();
	}
	
	
	public Object visitProgram(Program program, Object arg) throws SemanticException {
		Program programAST;
		ArrayList<VariableGlobalDefinition> varGlobal = new ArrayList<VariableGlobalDefinition>();
		FunctionProcedureDefinitionList functionProcedureDefinitionList;
		ArrayList<CallableDefinition> callable = new ArrayList<CallableDefinition>();
		if(program.getVariableGlobalDefinition() != null){
			for(VariableGlobalDefinition var : program.getVariableGlobalDefinition()){
				Object v1 = var.visit(this, null);
				if(v1 instanceof IntVariableDefinition){
					((VariableDefinition)v1).tipo = ((IntVariableDefinition)v1).getTipo();
				}
				else if(v1 instanceof BoolVariableDefinition){
					((VariableDefinition)v1).tipo = ((BoolVariableDefinition)v1).getTipo();
				}
				else {
					throw new SemanticException("ERROR!!");
				}
				VariableGlobalDefinition vg = new VariableGlobalDefinition((VariableDefinition)v1);
				vg.tipo = ((VariableDefinition)v1).getTipo();
				varGlobal.add(vg);
			}
		}
		
		if(program.getFunctionProcedureDefinitionList().getCallableDefinition() != null){
			for(CallableDefinition call : program.getFunctionProcedureDefinitionList().getCallableDefinition()){
				this.reference.add(call);
				//this.identificationTable.enter(call.getIdentifier().value, call);
				callable.add((CallableDefinition)call.visit(this, call));
				this.reference.remove(call);
				
			}
		}
		else{
			throw new SemanticException("You must declare at least one function/procedure");
		}
		
		functionProcedureDefinitionList = new FunctionProcedureDefinitionList(null, callable);
		
		programAST = new Program(varGlobal, functionProcedureDefinitionList);
		
		return programAST;
	}

	public Object visitVariableGlobalDefinition(VariableGlobalDefinition var, Object arg) throws SemanticException {
		
		if(this.identificationTable.getCurrentScope() == 0){
			//Object temp = this.identificationTable.retrieve(var.getVariableDefinition().getIdentifier().getValue());
			//if(temp == null){
				//this.identificationTable.enter(var.getVariableDefinition().getIdentifier().getValue(), var);
				Object temp2 = var.getVariableDefinition();
				if(temp2 instanceof IntVariableDefinition){
					return ((IntVariableDefinition)temp2).visit(this, arg);
				}
				else if(temp2 instanceof BoolVariableDefinition){
					return ((BoolVariableDefinition)temp2).visit(this, arg);
				}
			//}
			
		}
		else{
			throw new SemanticException("It is not possible to declare global variable outside the outtermost block");
		}
		
		return null;
	}

	public Object visitFunctionProcedureDefinitionList(	FunctionProcedureDefinitionList funcProcDef, Object arg) throws SemanticException {
		
		
		for(CallableDefinition callTemp : funcProcDef.getCallableDefinition()){
			//Object temp = this.identificationTable.retrieve(callTemp.getIdentifier().value);
			//if(temp == null){
				//this.identificationTable.enter(callTemp.getIdentifier().value, callTemp);
				this.reference.add(callTemp);
				callTemp.visit(this, arg);
				this.reference.remove(callTemp);
			//}
		}
		
		return funcProcDef;
	}

	public Object visitIntVariableDefinition(IntVariableDefinition intVarDef, Object arg) throws SemanticException {
		Expression exp;
		IntVariableDefinition intAST;
		Object temp = this.identificationTable.retrieve(intVarDef.getIdentifier().getValue());
		
		if(temp == null){
			this.identificationTable.enter(intVarDef.getIdentifier().getValue(), intVarDef);
			Expression e = intVarDef.getExpression();
			Object temp2 = e.visit(this, arg);
			exp = ((Expression)temp2);
			if(exp.getTipo().equals("INT")){
				intAST = new IntVariableDefinition(intVarDef.getIdentifier(), intVarDef.getSign(), exp);
				return intAST;
			}
			else{
				throw new SemanticException("Different Kind of Data type");
			}
		}
		else{
			throw new SemanticException("Variable already declared.");
		}
	
		//return null;
		
		/*
		Object expression = intVarDef.getExpression().visit(this,arg);
		
		if(expression instanceof Number){
			if(((Number)expression).getTipo().equals(intVarDef.getTipo())){
				return expression;
			}
			else{
				throw new SemanticException("Wrong attribution between INT and BOOL");
			}
		}
		else{
			throw new SemanticException("Wrong attribution between INT and BOOL");
		}
		*/		
	}

	public Object visitBoolVariableDefinition(BoolVariableDefinition boolVarDef, Object arg) throws SemanticException {
		Expression exp;
		BoolVariableDefinition boolAST;
		Object temp = this.identificationTable.retrieve(boolVarDef.getIdentifier().getValue());
		
		if(temp == null){
			this.identificationTable.enter(boolVarDef.getIdentifier().getValue(), boolVarDef);
			Expression e = boolVarDef.getExpression();
			Object temp2 = e.visit(this, arg);
			exp = ((Expression)temp2);
			if(exp.getTipo().equals("BOOL")){
				boolAST = new BoolVariableDefinition(boolVarDef.getIdentifier(),  exp);
				return boolAST;
			}
			else{
				throw new SemanticException("Different Kind of Data type");
			}
		}
		else{
			throw new SemanticException("Variable already declared.");
		}
		
	}

	public Object visitFunctionDefintion(FunctionDefinition funcDef, Object arg) throws SemanticException {
		Object temp = this.identificationTable.retrieve(funcDef.getIdentifier().getValue());
		if(temp == null){
			
			this.identificationTable.enter(funcDef.getIdentifier().getValue(), funcDef);
			this.identificationTable.openScope();
			for(VariableDefinition var : funcDef.getVariable()){
				var.visit(this, arg);
			}
		}else{
			throw new SemanticException("Function already declared.");
		}
			/*if(!this.identificationTable.containsKey(funcDef.getIdentifier().toString())){
				this.identificationTable.enter(funcDef.getIdentifier().toString(), funcDef);
				this.identificationTable.openScope();
			}
			else{
				
			}
			*/
		this.identificationTable.closeScope();
		return null;
	}

	public Object visitProcedureDefinition(ProcedureDefinition procDef, Object arg) throws SemanticException {
		
		ProcedureDefinition procDefAST;
		ArrayList<VariableDefinition> variables = new ArrayList<VariableDefinition>();
		ArrayList<Command> commands = new ArrayList<Command>();
		
		
		Object temp = this.identificationTable.retrieve(procDef.getIdentifier().getValue());
		if(temp == null){
			this.identificationTable.enter(procDef.getIdentifier().getValue(), procDef);
			if(procDef.getVariableDefinition() != null)
				for(VariableDefinition var : procDef.getVariableDefinition()){
				
					variables.add((VariableDefinition) var.visit(this, arg));
				}
			if(procDef.getCommands() != null){
				for(Command com : procDef.getCommands()){
				
					if(com instanceof AssignmentCommand){
						commands.add((Command) ((AssignmentCommand)com).visit(this, arg));
					}
					else if(com instanceof BreakCommand){
						((BreakCommand)com).visit(this, arg);
					}
					else if(com instanceof ContinueCommand){
						((ContinueCommand)com).visit(this, arg);
					}
					else if(com instanceof CallCommand){
						((CallCommand)com).visit(this, arg);
					}
					else if(com instanceof IfCommand){
						((IfCommand)com).visit(this, arg);
					}
					else if(com instanceof PrintCommand){
						((PrintCommand)com).visit(this, arg);
					}
					else if(com instanceof WhileCommand){
						commands.add((Command) ((WhileCommand)com).visit(this, arg));
					}
				}
				
				
			}
			
			procDefAST = new ProcedureDefinition(procDef.getIdentifier(), variables, commands, null);
			return procDefAST;

		}
		else{
			
		}
		
		
		
		return procDef;
		/*this.identificationTable.openScope();
		
		Object varDef = procDef.getVariableDefinition();
		
		if(varDef != null){
			for(VariableDefinition var : procDef.getVariableDefinition()){
				Object teste = this.identificationTable.retrieve(var.getIdentifier().value);
				if(teste == null){
					this.identificationTable.enter(var.getIdentifier().value, var);
					var.visit(this, arg);
					//this.identificationTable.enter(var.getIdentifier().value, var);
					
				}
				else{
					throw new SemanticException("There is an Variable/Function already declared named as :" + var.getIdentifier().value);
				}
				
			}
		}
		
		Object commands = procDef.getCommands();
		
		if(commands != null){
			for(Command c : procDef.getCommands()){
				if(c instanceof AssignmentCommand){
					Object teste = this.identificationTable.retrieve(((AssignmentCommand) c).getIdentifier().value);
					if(teste != null){
						this.reference.add(teste);
						Object asgn = c.visit(this, teste);
						
						this.reference.remove(teste);
					}
				}
				else if(c instanceof IfCommand){
					c.visit(this, arg);
				}
				else if(c instanceof ResultIsCommand){
					throw new SemanticException("You cannot return anything inside a Procedure Block -- Type: VOID");
				}
				else if(c instanceof BreakCommand){
					c.visit(this, arg);
				}
				else if(c instanceof ContinueCommand){
					
				}
				else if(c instanceof WhileCommand){
					
				}
				else if(c instanceof CallCommand){
					
				}
				else if(c instanceof PrintCommand){
					
				}
			}
		}
		
		
		this.identificationTable.closeScope();
		
		return procDef;
		*/
	}

	public Object visitParametersPrototype(ParametersPrototype params,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visitAssignmentCallCommand(AssignmentCallCommand assign,	Object arg) throws SemanticException {
		AssignmentCallCommand assAST;
		CallCommand call;
		AST temp = this.identificationTable.retrieve(assign.getCallCommand().getIdentifier().getValue());
		if(temp != null){
			call = (CallCommand) temp.visit(this, arg);
		}
		else{
			
		}
		
		return null;
	}

	public Object visitAssignmentCommand(AssignmentCommand assign, Object arg) throws SemanticException {
		AssignmentCommand asgn = null;
		Identifier id = null;
		Expression expr = null;
		Object ast = this.identificationTable.retrieve(assign.getIdentifier().getValue());
		if(ast != null){
		Object tmp = assign.getExpression();
		
		if(tmp != null){
			if(tmp instanceof Expression){
				Object exp = ((Expression) tmp).visit(this, arg);
				if(((IntVariableDefinition)ast).getTipo().equals(((Expression)exp).getTipo())){
					id = new Identifier(((IntVariableDefinition)ast).getIdentifier().getValue());
				}
				else if(((BoolVariableDefinition)ast).getTipo().equals(((Expression)exp).getTipo())){
					id = new Identifier(((BoolVariableDefinition)ast).getIdentifier().getValue());
				}
				else{
					throw new SemanticException("AssignmentCommand: Variable not Declared");
				}
				
				asgn = new AssignmentCommand(id, (Expression) exp);
				return asgn;
				
				
			}
		}
		return null;
		}
		else{
			throw new SemanticException("AssignmentCommand: Variable not Declared");
		}
	}

	public Object visitCallCommand(CallCommand callCmd, Object arg)	throws SemanticException {
		AST cmd;
		
		cmd = this.identificationTable.retrieve(callCmd.getIdentifier().getValue());
		
		if(cmd != null){
			return cmd;
		}
		else{
			throw new SemanticException("Function/Procedure not declared.");
		}
			
			
		
		//return null;
	}

	public Object visitContinueCommand(ContinueCommand continueCmd, Object arg) throws SemanticException {
		if(arg instanceof WhileCommand){
			return continueCmd;
		}
		else{
			throw new SemanticException("CONTINUE needs to be used within loop command");
		}
	}

	public Object visitBreakCommand(BreakCommand breakCmd, Object arg)	throws SemanticException {
		
		if(arg instanceof WhileCommand){
			
			return breakCmd;
		}else{
			throw new SemanticException("BREAK needs to be used within loop command");
		}
		
		
	}

	public Object visitPrintCommand(PrintCommand printCmd, Object arg)	throws SemanticException {

		return null;
	}

	public Object visitIfCommand(IfCommand ifCmd, Object arg) 	throws SemanticException {
		this.identificationTable.openScope();
		Expression exp = ifCmd.getExpression();
		if(exp != null){
			exp.visit(this, arg);
		}
		
		Object commands = ifCmd.getCommand();
		
		if(commands != null){
			for(Command c : ifCmd.getCommand()){
				if(c instanceof IfCommand){
					return ((IfCommand)c).visit(this, arg);
				}
				else if(c instanceof AssignmentCommand){
					return ((AssignmentCommand)c).visit(this, arg);
				}
				else if(c instanceof WhileCommand){
					return ((WhileCommand)c).visit(this,arg);
				}
				else if(c instanceof CallCommand){
					return ((CallCommand)c).visit(this,arg);
				}
				else if(c instanceof ContinueCommand){
					return ((ContinueCommand)c).visit(this, arg);
				}
				else if(c instanceof BreakCommand){
					return ((BreakCommand)c).visit(this, arg);	
				}
				else if(c instanceof ResultIsCommand){
					return ((ResultIsCommand)c).visit(this, arg);
				}
				else if(c instanceof ElseCommand){
					return ((ElseCommand)c).visit(this, arg);
				}
				else if(c instanceof PrintCommand){
					return ((PrintCommand)c).visit(this, arg);
				}
				
			}
		}
		
		
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

	public Object visitResultIsCommand(ResultIsCommand resultCmd, Object arg) throws SemanticException {
		if(arg instanceof ProcedureDefinition){
			throw new SemanticException("You cannot return anything inside a Procedure (VOID)");
		}else if(arg instanceof FunctionDefinition){
			
		}
		return null;
	}

	public Object visitWhileCommand(WhileCommand whileCmd, Object arg)	throws SemanticException {
		Expression exp = whileCmd.getExpression();
		WhileCommand wcmd;
		ArrayList<Command> command = new ArrayList<Command>();
		Object temp = exp.visit(this, arg);
		if(temp instanceof Expression){
			exp = ((Expression)temp);
		}
		else{
			throw new SemanticException("visitWhileCommand: Expression failed");
		}
		
		Object cmd = whileCmd.getCommand();
		if(cmd != null){
			this.identificationTable.openScope();
			for(Command c : whileCmd.getCommand()){
				if(c instanceof BreakCommand){
					Object tmp = ((BreakCommand)c).visit(this, whileCmd);
					break;
				}
			}
			this.identificationTable.closeScope();
		}
		
		
		return null;
	}

	public Object visitExpression(Expression expression, Object arg) throws SemanticException {
		Expression expressionAST;
		ExpressionArithmetic esquerdo = null;
		ExpressionArithmetic direita = null;
		Operator operador = null;
		Object temp = expression.getLeft();
		operador = expression.getOperator();
		if(temp != null){
			Object tipo = ((ExpressionArithmetic)temp).visit(this, arg);
			esquerdo = ((ExpressionArithmetic)tipo);
			//esquerdo.tipo = ((ExpressionArithmetic)temp).getTipo();
		}
		Object right = expression.getRight();
		if(right != null){
			Object e2 = ((ExpressionArithmetic)right).visit(this, arg);
			
			direita = ((ExpressionArithmetic)e2);
		}
		
		expressionAST = new Expression(esquerdo, operador, direita);
		expressionAST.tipo = esquerdo.getTipo();
		return expressionAST;
	}

	public Object visitExpressionArithmetic(ExpressionArithmetic expAri, Object arg) throws SemanticException {
		ExpressionArithmetic expAST;
		ExpressionMultiplication esquerdo = null;
		ArrayList<ExpressionMultiplication> others = new ArrayList<ExpressionMultiplication>();
		ArrayList<Operator> operadores = expAri.getOperadores();
		Object temp = expAri.getExpressionMultiplicationLeft();
		
		Object type1 = null;
		Object type2 = null;
		if(temp != null){
			type1 = ((ExpressionMultiplication)temp).visit(this, arg);
			if(type1 instanceof ExpressionMultiplication){
				esquerdo = ((ExpressionMultiplication)type1);
				esquerdo.tipo = ((ExpressionMultiplication)type1).getTipo();
			}
			
		
		}
		
		Object right = expAri.getExpressionMultiplicationOthers();
		
		if(right != null){
			for(ExpressionMultiplication exp : expAri.getExpressionMultiplicationOthers()){
				type2 = exp.visit(this, arg);
				if(esquerdo.equals(type2)){
					
					others.add(((ExpressionMultiplication)type2));
				}else{
					throw new SemanticException("Different types");
				}
			}
		}
		else{
			return ((Factor)type1).tipo;
		}
		
		expAST = new ExpressionArithmetic(esquerdo, operadores, others);
		expAST.tipo = esquerdo.getTipo();
		return expAST;
	}

	public Object visitExpressionMultiplication(ExpressionMultiplication expMul, Object arg) throws SemanticException {
		ExpressionMultiplication expAST;
		ArrayList<Factor> others = new ArrayList<Factor>();
		Factor esquerdo = null;
		Object temp = expMul.getFactorLeft();
		Object f1;
		if(temp != null){
			 f1 = ((Factor)temp).visit(this, arg);
			 if(f1 instanceof Number){
				 esquerdo = ((Number)f1);
			 }
			 else if(f1 instanceof Bool){
				 esquerdo = ((Bool)f1);
			 }
			 else if(f1 instanceof AssignmentCallCommand){
				 esquerdo = ((AssignmentCallCommand)f1);
			 }
			 else if(f1 instanceof Identifier){
				 esquerdo = ((Identifier)f1);
			 }
		}
		else{
			return null;
		}
			
		
		Object right = expMul.getFactorOthers();
		
		if(right != null){
			for(Factor f : expMul.getFactorOthers()){
				if(f1.equals(f.visit(this, arg))){
					if(f instanceof Number){
						others.add(((Number)f));
					}else if(f instanceof Bool){
						others.add(((Bool)f));
					}/*else if(f instanceof Bool){
						
					}*/
					
					
					
				}
				else{
					throw new SemanticException("You cannot operate different types of data");
				}
				
			}
		}
		
		/*
		Factor f = expMul.getFactorLeft();
		Object temp = f.visit(this, null);
		
		if(temp instanceof Number){
			return temp;
		}
		else if(temp instanceof Bool){
			return temp;
		}
		*/
		expAST = new ExpressionMultiplication(esquerdo, expMul.getOperadores(), others);
		expAST.tipo = esquerdo.tipo;
		return expAST;
	}

	public Object visitFactor(Factor factor, Object arg) throws SemanticException {
		
		//Object result;
		
		if(factor instanceof Identifier){
			return ((Identifier)factor).visit(this, arg);
		}
		else if(factor instanceof Number){
			return ((Number)factor).visit(this, arg);
		}
		else if(factor instanceof Bool){
			return ((Bool)factor).visit(this, arg);
		}
		else if(factor instanceof Expression){
			return ((Expression)factor).visit(this, arg);
		}
		else if(factor instanceof AssignmentCallCommand){
			return ((AssignmentCallCommand)factor).visit(this, arg);
		}
		
		/*
		if(factor.getNumber() != null){
			Number num = factor.getNumber();
			num.visit(this, arg);
			return num;
		}
		else if(factor.getBool() != null){
			Bool bool = factor.getBool();
			bool.visit(this, arg);
			return bool;
		}
		*/
		
		
		return null;
	}

	public Object visitIdentifier(Identifier identifier, Object obj) throws SemanticException {
		AST ast = this.identificationTable.retrieve(identifier.getValue());
		if(ast != null){
		//System.out.println(identifier.getValue() + "\n");
			if(ast instanceof IntVariableDefinition){
				identifier.tipo = ((IntVariableDefinition)ast).getTipo();
			}else if(ast instanceof BoolVariableDefinition){
				identifier.tipo = ((BoolVariableDefinition)ast).getTipo();
			}
			else{
				throw new SemanticException("visitIdentifier: Variable has different type");
			}
			
			return identifier;
		}else{
			throw new SemanticException("visitIdentifier: Variable Not declared");
		}
	}

	public Object visitNumber(Number number, Object obj) throws SemanticException {

		System.out.println(number.getValue() + "\n");
		number.tipo = "INT";
		return number;
	}

	public Object visitBoolean(Bool bool, Object obj) throws SemanticException {
		System.out.println(bool.getValue());
		bool.tipo = "BOOL";
		return bool;
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
