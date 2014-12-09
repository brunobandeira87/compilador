package checker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import scanner.TokenKind;
import util.AST.*;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;


public final class Checker implements Visitor {
	
	private IdentificationTable identificationTable;
	private Vector<Object> reference;
	private int localVar;

	public AST check(AST ast) throws SemanticException{
		Object newAST = null;
		this.identificationTable = new IdentificationTable();
		try{
			newAST =  ast.visit(this,null);
			
		}catch(SemanticException e){
			e.printStackTrace();
		}
		return (AST)newAST; 
	}
	
	public Checker(){
		this.reference = new Vector<Object>();
		this.localVar = 0;
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
					//((IntVariableDefinition)v1).seipo = ((IntVariableDefinition)v1).getTipo();
					VariableGlobalDefinition vg = new VariableGlobalDefinition(((IntVariableDefinition)v1));
					varGlobal.add(vg);
				//	vg.tipo = (((IntVariableDefinition)v1)).getTipo();
					
				}
				else if(v1 instanceof BoolVariableDefinition){
					VariableGlobalDefinition vg = new VariableGlobalDefinition((BoolVariableDefinition)v1);
					varGlobal.add(vg);
				}
				else {
					throw new SemanticException("ERROR!!");
				}
							
			}
		}
		
		
		
		if(program.getFunctionProcedureDefinitionList().getCallableDefinition() != null){
			for(CallableDefinition call : program.getFunctionProcedureDefinitionList().getCallableDefinition()){
				//this.reference.add(call);
				//this.identificationTable.enter(call.getIdentifier().value, call);
				this.reference.add(call);
				callable.add((CallableDefinition)call.visit(this, null));
				this.reference.remove(call);
				
			}
		}
		else{
			throw new SemanticException("visitProgram => You MUST declare at least one function/procedure as a \'main\'");
		}
		
		functionProcedureDefinitionList = new FunctionProcedureDefinitionList(null, callable);
		
		programAST = new Program(varGlobal, functionProcedureDefinitionList);
		program = programAST;
		return programAST;
	}

	public Object visitVariableGlobalDefinition(VariableGlobalDefinition var, Object arg) throws SemanticException {
		
		if(this.identificationTable.getCurrentScope() == 0){
			//Object temp = this.identificationTable.retrieve(var.getVariableDefinition().getIdentifier().getValue());
			//if(temp == null){
				//this.identificationTable.enter(var.getVariableDefinition().getIdentifier().getValue(), var);
				Object temp2 = var.getVariableDefinition();
				if(temp2 instanceof IntVariableDefinition){
					return ((IntVariableDefinition)temp2).visit(this, var);
				}
				else if(temp2 instanceof BoolVariableDefinition){
					return ((BoolVariableDefinition)temp2).visit(this, var);
				}
			//}
			
		}
		else{
			throw new SemanticException("visitVariableGlobalDefinition => It is not possible to declare global variable outside the outtermost block");
		}
		
		return null;
	}

	public Object visitFunctionProcedureDefinitionList(	FunctionProcedureDefinitionList funcProcDef, Object arg) throws SemanticException {
		FunctionProcedureDefinitionList funcPAST;
		ArrayList<CallableDefinition>  callDef = new ArrayList<CallableDefinition>();
		if(funcProcDef.getCallableDefinition() != null){
			for(CallableDefinition callTemp : funcProcDef.getCallableDefinition()){
					this.reference.add(callTemp);
					this.identificationTable.openScope();
					callDef.add((CallableDefinition) callTemp.visit(this, arg));
					this.reference.remove(callTemp);
					this.identificationTable.closeScope();
				//}
			}
		}
		
		funcPAST = new FunctionProcedureDefinitionList(funcProcDef.getReservedWords(), callDef);
		
		return funcPAST;
	}

	public Object visitIntVariableDefinition(IntVariableDefinition intVarDef, Object arg) throws SemanticException {
		Expression exp;
		IntVariableDefinition intAST;
		Object temp = this.identificationTable.retrieve(intVarDef.getIdentifier().getValue());
		
		if(arg instanceof VariableGlobalDefinition){
			return null;
		}
		
		else{
			
			if(temp == null){
				//this.identificationTable.enter(intVarDef.getIdentifier().getValue(), intVarDef);
				Expression e = intVarDef.getExpression();
				Object temp2 = e.visit(this, arg);
				exp = ((Expression)temp2);
				if(exp.getTipo().equals("INT")){
					intAST = new IntVariableDefinition(intVarDef.getIdentifier(), intVarDef.getSign(), exp);
					if(arg instanceof VariableGlobalDefinition){
						intAST.global = true;
					}else{
						this.localVar++;
						intAST.getIdentifier().setPosition(this.localVar);
						intAST.getIdentifier().setGlobal(false);
						intAST.getIdentifier().setTipo("INT");
						
					}
					this.identificationTable.enter(intAST.getIdentifier().getValue(), intAST);
					return intAST;
				}
				else{
					throw new SemanticException("visitIntVariableDefinition => Type mismatch");
				}
			}
			else{
				throw new SemanticException("visitIntVariableDefinition => Variable \'"  + intVarDef.getIdentifier().getValue() +  "\' already declared.");
			}
		}
		
	
	}

	public Object visitBoolVariableDefinition(BoolVariableDefinition boolVarDef, Object arg) throws SemanticException {
		Expression exp;
		BoolVariableDefinition boolAST;
		Object temp = this.identificationTable.retrieve(boolVarDef.getIdentifier().getValue());
		
		if(temp == null){
			//this.identificationTable.enter(boolVarDef.getIdentifier().getValue(), boolVarDef);
			Expression e = boolVarDef.getExpression();
			Object temp2 = e.visit(this, arg);
			exp = ((Expression)temp2);
			if(exp.getTipo().equals("BOOL")){
				boolAST = new BoolVariableDefinition(boolVarDef.getIdentifier(),  exp);
				if(arg instanceof VariableGlobalDefinition){
					boolAST.getIdentifier().setGlobal(true);
				}
				else{
					this.localVar++;
					boolAST.getIdentifier().setTipo("BOOL");
					boolAST.getIdentifier().setPosition(this.localVar);
					boolAST.getIdentifier().setGlobal(false);
				}
				this.identificationTable.enter(boolVarDef.getIdentifier().getValue(), boolAST);
				return boolAST;
			}
			else{
				throw new SemanticException("visitBoolVariableDefinition => Type Mismatch. Type passed: \'" + exp.getTipo() +"\'. Type needed: \'BOOL\'");
			}
		}
		else{
			throw new SemanticException("visitBoolVariableDefinition => Variable \'" + boolVarDef.getIdentifier().getValue() + "\' already declared.");
		}
		
	}

	public Object visitFunctionDefintion(FunctionDefinition funcDef, Object arg) throws SemanticException {
//		System.out.println(this.identificationTable.getCurrentScope());
		
		FunctionDefinition funcDefAST;
		ArrayList<VariableDefinition> variables = new ArrayList<VariableDefinition>();
		ArrayList<Command> commands = new ArrayList<Command>();
		ParametersPrototype params;
		Vector<Object> refer = new Vector<Object>();
		refer.add(funcDef);
		
		
		Object temp = this.identificationTable.retrieve(funcDef.getIdentifier().getValue());
		if(temp == null){
			
			
			params = funcDef.getParams();
			Object paramsTemp;
			this.identificationTable.enter(funcDef.getIdentifier().getValue(), funcDef);
			this.identificationTable.openScope();
			if(params != null){
				paramsTemp = params.visit(this, funcDef);
				funcDef.setParams((ParametersPrototype)(paramsTemp));
			}
			
			
			
			
			//System.out.println(funcDef.getIdentifier().getValue());
			if(funcDef.getVariable() != null)
				
				for(VariableDefinition var : funcDef.getVariable()){
					
					variables.add((VariableDefinition) var.visit(this, funcDef));
				}
			if(funcDef.getCommand() != null){
				for(Command com : funcDef.getCommand()){
					
					if(com instanceof AssignmentCommand){
						commands.add((Command) ((AssignmentCommand)com).visit(this, refer));
					}
					else if(com instanceof BreakCommand){
						commands.add((Command) ((BreakCommand)com).visit(this, refer));
					}
					else if(com instanceof ContinueCommand){
						commands.add((Command) ((ContinueCommand)com).visit(this, refer));
					}
					else if(com instanceof CallCommand){
						commands.add((Command) ((CallCommand)com).visit(this, refer));
					}
					else if(com instanceof IfCommand){
						commands.add((Command)((IfCommand)com).visit(this, refer));
					}
					else if(com instanceof PrintCommand){
						commands.add((Command)((PrintCommand)com).visit(this, refer));
					}
					else if(com instanceof WhileCommand){
						commands.add((Command) ((WhileCommand)com).visit(this, refer));
					}
					else if(com instanceof ResultIsCommand){
						commands.add((Command) ((ResultIsCommand)com).visit(this, refer));
						//hasReturn = true;
					}
				}
				
				
			}
			
			boolean hasReturn = ((FunctionDefinition)refer.firstElement()).getHasReturn();
			
			if(hasReturn == false){
				throw new SemanticException("visitFunctionDefinition => Function MUST have at least one RESULTIS Statement.");
			}
			
			funcDefAST = new FunctionDefinition(funcDef.getTipo(),funcDef.getIdentifier(), funcDef.getParams(),commands, variables);
			funcDefAST.setHasReturn();
			
			

			this.identificationTable.closeScope();
			this.localVar = 0;
			return funcDefAST;
		}else{
			throw new SemanticException("visitFunctionDefinition => Function/Procedure \'" + funcDef.getIdentifier().getValue() + "\' already declared.");
		}

	}

	public Object visitProcedureDefinition(ProcedureDefinition procDef, Object arg) throws SemanticException {
		
		ProcedureDefinition procDefAST;
		ArrayList<VariableDefinition> variables = new ArrayList<VariableDefinition>();
		ArrayList<Command> commands = new ArrayList<Command>();
		ParametersPrototype params;
		Vector<Object> refer = new Vector<Object>();
		refer.add(procDef);
		
		Object temp = this.identificationTable.retrieve(procDef.getIdentifier().getValue());
		if(temp == null){
			this.identificationTable.enter(procDef.getIdentifier().getValue(), procDef);
			
			params = procDef.getParams();
			Object paramsTemp = null;
			this.identificationTable.openScope();
			if(params != null)
				paramsTemp = params.visit(this, procDef);
			
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
						commands.add((Command) ((BreakCommand)com).visit(this, arg));
					}
					else if(com instanceof ContinueCommand){
						commands.add((Command) ((ContinueCommand)com).visit(this, arg));
					}
					else if(com instanceof CallCommand){
						commands.add((Command) ((CallCommand)com).visit(this, arg));
					}
					else if(com instanceof IfCommand){
						commands.add((Command)((IfCommand)com).visit(this, refer));
					}
					else if(com instanceof PrintCommand){
						commands.add((Command)((PrintCommand)com).visit(this, arg));
					}
					else if(com instanceof WhileCommand){
						commands.add((Command) ((WhileCommand)com).visit(this, refer));
					}
					else if(com instanceof ResultIsCommand){
						commands.add((Command) ((ResultIsCommand)com).visit(this, refer));
					}
				}
				
				
			}
			this.identificationTable.closeScope();
			procDefAST = new ProcedureDefinition(procDef.getIdentifier(), variables, commands, (ParametersPrototype) paramsTemp);
			this.localVar = 0;
			return procDefAST;

		}
		else{
			
		}
		
		
		
		return procDef;
		
	}

	public Object visitParametersPrototype(ParametersPrototype params,	Object arg) throws SemanticException {

		
		String id;
		
		if(arg instanceof ProcedureDefinition){
			id = ((ProcedureDefinition)arg).getIdentifier().getValue(); 
		}else{
			id = ((FunctionDefinition)arg).getIdentifier().getValue();
		}
		
		if(id.equals("main")){
			if(params == null){
				return null;
			}else{
				throw new SemanticException("visitParametersPrototype => \'main\' function/procedure MUST have none parameters");
			}
		}else{
			ArrayList<Tipo> tipoTemp = params.getTipo();
			ArrayList<Identifier> idTemp = params.getIdentifier();
			
			Iterator tipoTempIt = tipoTemp.iterator();
			ListIterator idTempIt = idTemp.listIterator();
			int index = 0;
			while(tipoTempIt.hasNext() && idTempIt.hasNext()){
				index++;
				Object idCurrent = idTempIt.next();
				((Identifier)idCurrent).setTipo(((Tipo)(tipoTempIt.next())).value);
				((Identifier)idCurrent).setPosition(index);
				((Identifier)idCurrent).setParameter(true);
				idTempIt.set(idCurrent);
				this.identificationTable.enter(((Identifier)idCurrent).getValue(), (AST)idCurrent);
			}		
			
			return new ParametersPrototype(tipoTemp, idTemp, null);
		}
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
		Object ast = this.identificationTable.retrieve(assign.getIdentifier().getValue());
		
		if(ast != null){
			Object tmp = assign.getExpression();
			
			if(tmp != null){
				if(tmp instanceof Expression){
					Object exp = ((Expression) tmp).visit(this, arg);
					if(ast instanceof IntVariableDefinition){
						if(((IntVariableDefinition)ast).getTipo().equals(((Expression)exp).getTipo())){
							id = new Identifier(((IntVariableDefinition)ast).getIdentifier().getValue());
							id.setPosition(1);
							id.setTipo("INT");
							id.setGlobal(((IntVariableDefinition)ast).global);
						}
						else{
							throw new SemanticException("AssignmentCommand => Trying to assign different types of data");
						}
					}
					else if(ast instanceof BoolVariableDefinition){
						if(((BoolVariableDefinition)ast).getTipo().equals(((Expression)exp).getTipo())){
							id = new Identifier(((BoolVariableDefinition)ast).getIdentifier().getValue());
							id.setPosition(1);
							id.setTipo("BOOL");
							id.setGlobal(((BoolVariableDefinition)ast).global);
						}
						else{
							throw new SemanticException("AssignmentCommand => Trying to assign different types of data");
						}
					}
					else if(ast instanceof Identifier){
						if(((Identifier)ast).getTipo().equals(((Expression)exp).getTipo())){
							id = new Identifier(((Identifier)ast).getValue());
							id.setPosition(((Identifier)ast).getPosition());
							id.setTipo(((Identifier)ast).getTipo());
							id.setGlobal(((Identifier)ast).getGlobal());
							id.setParameter(((Identifier)ast).getParameter());
						}
						else{
							throw new SemanticException("AssignmentCommand => Trying to assign different types of data");
						}
					}
					else{
						throw new SemanticException("AssignmentCommand => Unknown data type");
					}
					if(id.getTipo().equals(((Expression)exp).getTipo())){
						asgn = new AssignmentCommand(id, (Expression) exp);
						asgn.setTipo(id.getTipo());
						
						return asgn;
					}
					else{
						throw new SemanticException("AssignmentCommand => Trying to assign different types of data");
					}
					
					
				}
			}else if(assign.getCallCommand() != null){
				CallCommand cmd = assign.getCallCommand();
				if(cmd != null){
					tmp = cmd.visit(this, arg);
					String idfunc = (String)((CallCommand)cmd).getIdentifier().getValue();
					Object callcmdtmp = this.identificationTable.retrieve(idfunc);
					if(callcmdtmp != null){
						if(callcmdtmp instanceof FunctionDefinition){
							String tipo = ((FunctionDefinition)(callcmdtmp)).getTipo();
							String tipo2="";
							if(ast instanceof BoolVariableDefinition){
								ast = (BoolVariableDefinition)ast;
								tipo2 = ((BoolVariableDefinition) ast).getTipo();
							}
							else if(ast instanceof IntVariableDefinition){
								ast = (IntVariableDefinition)ast;
								tipo2 = ((IntVariableDefinition) ast).getTipo();
							}
							else if(ast instanceof Identifier){
								ast = (Identifier)ast;
								tipo2 = ((Identifier) ast).getTipo();
							}
							
							if(tipo.equals(tipo2)){
								cmd.getIdentifier().setPosition(1);
								asgn = new AssignmentCommand(cmd.getIdentifier(), (CallCommand)tmp );
								asgn.setTipo(tipo2);
							
								return asgn;
							}
							else{
								throw new SemanticException("visitAssignmentCommand => Trying to attribute different data types");
							}
						}
						else{
							throw new SemanticException("visitAssignmentCommand => Trying to attribute a VOID function as a value");
						}
					}
				}
			}
			else{
				throw new SemanticException("");
				
			}
			
		return null;
		}
		else{
			throw new SemanticException("AssignmentCommand => Variable \'" + assign.getIdentifier().getValue() + "\' not Declared");
		}
	}

	public Object visitCallCommand(CallCommand callCmd, Object arg)	throws SemanticException {	
		
		AST cmd = this.identificationTable.retrieve(callCmd.getIdentifier().getValue());
		String tipo = "";
		System.out.println(callCmd.getIdentifier().getValue());
		if(cmd != null){
			if((ParametersCallCommand) callCmd.getParams() != null){
				ParametersCallCommand paramsTemp = (ParametersCallCommand) callCmd.getParams().visit(this, arg);
				
				ArrayList<Identifier> paramsProto = null;
				
				if(cmd instanceof ProcedureDefinition){
					paramsProto = ((ProcedureDefinition) cmd).getParams().getIdentifier();
					tipo = ((ProcedureDefinition)cmd).getTipo();
				}else if(cmd instanceof FunctionDefinition){
					paramsProto = ((FunctionDefinition) cmd).getParams().getIdentifier();
					tipo = ((FunctionDefinition)cmd).getTipo();
				}
				if(paramsProto.size() == paramsTemp.getParams().size()){
					for(int i = 0; i < paramsProto.size() ; i++){
						if(paramsProto.get(i).getTipo().equals(paramsTemp.getParams().get(i).getTipo())){
							continue;
						}
						else{
							throw new SemanticException("visitCallCommand => Passing a \'" + paramsTemp.getParams().get(i).getTipo() + "\' value instead of"
									+ "\' " + paramsProto.get(i).getTipo() + "\' value.");
						}
					}
				}
				else{
					throw new SemanticException("visitCallCommand => Number of arguments wrong. You have passed " + paramsTemp.getParams().size() + " arguments"
							+ "instead of passing " +paramsProto.size() + "arguments." );
				}
				return new CallCommand( tipo, callCmd.getIdentifier(), paramsTemp);
				
			}
			else{
				
				if(cmd instanceof ProcedureDefinition){
				
					tipo = ((ProcedureDefinition)cmd).getTipo();
				}else if(cmd instanceof FunctionDefinition){
				
					tipo = ((FunctionDefinition)cmd).getTipo();
				}
				
				return new CallCommand(tipo, callCmd.getIdentifier());
			}
			
		}
		else{
			throw new SemanticException("visitCallCommand => Function/Procedure '" + callCmd.getIdentifier().getValue() + "' not declared.");
		}
			
			
		
		//return null;
	}

	public Object visitContinueCommand(ContinueCommand continueCmd, Object arg) throws SemanticException {
		if(arg instanceof WhileCommand){
			return continueCmd;
		}
		
		else if(arg instanceof Vector){
			
			
			if(((Vector) arg).isEmpty() == false){
				for(int i = ((Vector)arg).size()-1; i > 0 ; i--){
					Object temp = ((Vector) arg).get(i);
					if(temp instanceof WhileCommand){
						return continueCmd;
					}
					
				}
			}
		}
		else{
			throw new SemanticException("visitContinueCommand => CONTINUE MUST be used within loop command");
		}
		return null;
	}

	public Object visitBreakCommand(BreakCommand breakCmd, Object arg)	throws SemanticException {
		
		if(arg instanceof WhileCommand){
			
			return breakCmd;
			
		}
		else if(arg instanceof Vector){
			
			
			if(((Vector) arg).isEmpty() == false){
				for(int i = ((Vector)arg).size()-1; i > 0 ; i--){
					Object temp = ((Vector) arg).get(i);
					if(temp instanceof WhileCommand){
						return breakCmd;
					}
					
				}
			}
		}
		
		else{
			throw new SemanticException("visitBreakCommand => 'BREAK' MUST be used within loop command");
		}
		return arg;
		
		
	}

	public Object visitPrintCommand(PrintCommand printCmd, Object arg)	throws SemanticException {
		Expression exp;
		Identifier identifier;
		Object id;
		PrintCommand pAST;
		
		
		if(printCmd.getIdentifier() != null){
			
			id =  this.identificationTable.retrieve(printCmd.getIdentifier().getValue());
			
			
			if(id != null){
			
				if(id instanceof Identifier){
					pAST = new PrintCommand((Identifier)id);
					return pAST;
					
				}
				else if(id instanceof IntVariableDefinition){
					pAST = new PrintCommand(((IntVariableDefinition)id).getIdentifier());
					return pAST;
				}
				else if(id instanceof BoolVariableDefinition){
					pAST = new PrintCommand(((BoolVariableDefinition)id).getIdentifier());
					return pAST;
				}
				//identifier = (Identifier) id.visit(this, arg);
				 
			}
			else{
				throw new SemanticException("visitPrintCommand => '" + printCmd.getIdentifier().getValue() + "' not declared! ");
			}
		}
		else if(printCmd.getExpression() != null){
			exp = (Expression) printCmd.getExpression().visit(this, arg);
			pAST = new PrintCommand(exp);
			return pAST;
		}
		
		return null;
	}

	public Object visitIfCommand(IfCommand ifCmd, Object arg) 	throws SemanticException {
		//this.identificationTable.openScope();
		Object exp = ifCmd.getExpression();
		Expression e;
		ElseCommand elseCommand = ifCmd.getElse();
		ArrayList<Command> cmd =  new ArrayList<Command>();
		IfCommand ic; 
		if(arg instanceof Vector){
			((Vector)arg).add(ifCmd);
		}
		if(exp != null){
			e = (Expression) ((Expression)exp).visit(this, arg);
		}
		else{
			throw new SemanticException("visitIfCommand => You MUST write an expression");
		}
		
		Object commands = ifCmd.getCommand();
		int currentScope = this.identificationTable.getCurrentScope();
		this.identificationTable.openScope();
		if(commands != null){
			for(Command c : ifCmd.getCommand()){
				if(c instanceof IfCommand){
					cmd.add( (Command) ((IfCommand)c).visit(this, arg));
				}
				else if(c instanceof AssignmentCommand){
					cmd.add( (Command) ((AssignmentCommand)c).visit(this, arg));
				}
				else if(c instanceof WhileCommand){
					cmd.add( (Command) ((WhileCommand)c).visit(this,arg));
				}
				else if(c instanceof CallCommand){
					cmd.add( (Command) ((CallCommand)c).visit(this,arg));
				}
				else if(c instanceof ContinueCommand){
					cmd.add( (Command) ((ContinueCommand)c).visit(this, arg));
				}
				else if(c instanceof BreakCommand){
					cmd.add( (Command) ((BreakCommand)c).visit(this, arg));
					break;
				}
				else if(c instanceof ResultIsCommand){
					cmd.add( (Command) ((ResultIsCommand)c).visit(this, arg));
				}
				else if(c instanceof ElseCommand){
					cmd.add( (Command)  ((ElseCommand)c).visit(this, arg));
				}
				else if(c instanceof PrintCommand){
					cmd.add( (Command) ((PrintCommand)c).visit(this, arg));
				}
				
			}
			
			this.identificationTable.closeScope();
			
		}
		if(elseCommand != null){
			elseCommand = (ElseCommand) elseCommand.visit(this, arg);
			elseCommand.setScope(currentScope);
		
		}
		if(arg instanceof Vector){
			((Vector)arg).remove(ifCmd);
		}
		ic = new IfCommand(e, cmd, elseCommand);
		ic.setScope(currentScope);
		return ic;
	}

	public Object visitElseCommand(ElseCommand elseCmd, Object arg) 	throws SemanticException {
		
		ArrayList<Command> command = elseCmd.getCommand();
		ArrayList<Command> cmd = new ArrayList<Command>();
		
		int currentScope;
		if(arg instanceof IfCommand){
			
			
			
			currentScope = ((IfCommand)arg).getScope();
			this.identificationTable.openScope();
			if(command != null){
				for(Command c : command){
					if(c instanceof IfCommand){
						cmd.add( (Command) ((IfCommand)c).visit(this, arg));
					}
					else if(c instanceof AssignmentCommand){
						cmd.add( (Command) ((AssignmentCommand)c).visit(this, arg));
					}
					else if(c instanceof WhileCommand){
						cmd.add( (Command) ((WhileCommand)c).visit(this,arg));
					}
					else if(c instanceof CallCommand){
						cmd.add( (Command) ((CallCommand)c).visit(this,arg));
					}
					else if(c instanceof ContinueCommand){
						cmd.add( (Command) ((ContinueCommand)c).visit(this, arg));
					}
					else if(c instanceof BreakCommand){
						cmd.add( (Command) ((BreakCommand)c).visit(this, arg));
						break;
					}
					else if(c instanceof ResultIsCommand){
						cmd.add( (Command) ((ResultIsCommand)c).visit(this, arg));
					}
					else if(c instanceof ElseCommand){
						cmd.add( (Command)  ((ElseCommand)c).visit(this, arg));
					}
					else if(c instanceof PrintCommand){
						cmd.add( (Command) ((PrintCommand)c).visit(this, arg));
					}
				}
				this.identificationTable.closeScope();
			}
			
			
			
			ElseCommand ecmd = new ElseCommand(cmd);
			ecmd.setScope(currentScope);
			return ecmd;
		}
		
		else if(arg instanceof Vector){
			
			if(((Vector)arg).lastElement() instanceof IfCommand){
				IfCommand ifcmd = (IfCommand)((Vector)arg).lastElement();
				 currentScope = ifcmd.getScope();
			}
			else{
				throw new SemanticException("visitElseCommand => You MUST declare an IF Statement before using an ELSE Statement");
			}
			
			((Vector)arg).add(elseCmd);
			
			this.identificationTable.openScope();
			if(command != null){
				for(Command c : command){
					if(c instanceof IfCommand){
						cmd.add( (Command) ((IfCommand)c).visit(this, arg));
					}
					else if(c instanceof AssignmentCommand){
						cmd.add( (Command) ((AssignmentCommand)c).visit(this, arg));
					}
					else if(c instanceof WhileCommand){
						cmd.add( (Command) ((WhileCommand)c).visit(this,arg));
					}
					else if(c instanceof CallCommand){
						cmd.add( (Command) ((CallCommand)c).visit(this,arg));
					}
					else if(c instanceof ContinueCommand){
						cmd.add( (Command) ((ContinueCommand)c).visit(this, arg));
					}
					else if(c instanceof BreakCommand){
						cmd.add( (Command) ((BreakCommand)c).visit(this, arg));
						break;
					}
					else if(c instanceof ResultIsCommand){
						cmd.add( (Command) ((ResultIsCommand)c).visit(this, arg));
					}
					else if(c instanceof ElseCommand){
						cmd.add( (Command)  ((ElseCommand)c).visit(this, arg));
					}
					else if(c instanceof PrintCommand){
						cmd.add( (Command) ((PrintCommand)c).visit(this, arg));
					}
				}
				this.identificationTable.closeScope();
			}
			
			if(arg instanceof Vector){
				((Vector)arg).remove(elseCmd);
			}
			
			ElseCommand ecmd = new ElseCommand(cmd);
			ecmd.setScope(currentScope);
			return ecmd;
			
		}
		
		
		else{
			throw new SemanticException("visitElseCommand => You MUST declare an IF Statement before using an ELSE Statement");
		}
		
	}

	public Object visitParametersCallCommand(ParametersCallCommand params,	Object arg) throws SemanticException {
		
		ArrayList<Factor> newPar = new ArrayList<Factor>();
		ArrayList<Factor> oldPar = params.getParams();
		for(Factor x : oldPar){
			newPar.add((Factor) x.visit(this, arg));
		}
				
		return new ParametersCallCommand(newPar);
	}

	public Object visitResultIsCommand(ResultIsCommand resultCmd, Object arg) throws SemanticException {
		Object funcProc = null;
		
		if(arg instanceof Vector){
			funcProc = ((Vector<Object>)arg).firstElement();
		}
				
		
		if(funcProc instanceof ProcedureDefinition){
			throw new SemanticException("visitResultIsCommand => You cannot return anything inside a Procedure (VOID)");
		}else if(funcProc instanceof FunctionDefinition){
			
			FunctionDefinition def = (FunctionDefinition) this.identificationTable.retrieve(((FunctionDefinition) funcProc).getIdentifier().getValue());
			Object ex = resultCmd.getExpression().visit(this, arg);
			if(((Expression)ex).getTipo().equals(def.getTipo())){
				((FunctionDefinition) funcProc).setHasReturn();
				((Vector<Object>)arg).insertElementAt(funcProc, 0);
				ResultIsCommand rc = new ResultIsCommand(((Expression)ex).getTipo(),(Expression)ex);
				return rc;
			}
			else{
				throw new SemanticException("visitResultIsCommand => You cannot return a \'" + ((Expression)ex).getTipo() +"\' while your function is \'" + def.getTipo() + "\'.");
			}
		}else{
			throw new SemanticException("visitResultIsCommand => Unknown Type");
		}
		
	}

	public Object visitWhileCommand(WhileCommand whileCmd, Object arg)	throws SemanticException {
		Expression exp = whileCmd.getExpression();
		WhileCommand wcmd;
		ArrayList<Command> command = new ArrayList<Command>();
		Object temp = exp.visit(this, arg);
		int scope = this.identificationTable.getCurrentScope();
		if(temp instanceof Expression){
			exp = ((Expression)temp);
		}
		else{
			throw new SemanticException("visitWhileCommand => Expression failed");
		}
		
		if(arg instanceof Vector){
			((Vector) arg).add(whileCmd);
		}
		
		Object cmd = whileCmd.getCommand();
		if(cmd != null){
			this.identificationTable.openScope();
			for(Command c : whileCmd.getCommand()){
				if(c instanceof BreakCommand){
					command.add((Command) ((BreakCommand)c).visit(this, arg));
					break;
				}
				else if(c instanceof ResultIsCommand){
					command.add((Command) ((ResultIsCommand)c).visit(this, arg));
					break;
				}
				else if(c instanceof AssignmentCommand){
					command.add((Command) ((AssignmentCommand)c).visit(this, arg));
				}
				else if(c instanceof IfCommand){
					command.add((Command) ((IfCommand)c).visit(this, arg));
				}
				else if(c instanceof PrintCommand){
					command.add((Command) ((PrintCommand)c).visit(this, arg));
				}
				else if(c instanceof CallCommand){
					command.add((Command) ((CallCommand)c).visit(this, arg));
				}
				else if(c instanceof WhileCommand){
					command.add((Command) ((WhileCommand)c).visit(this, arg));
				}
			}
			this.identificationTable.closeScope();
		}
		if(arg instanceof Vector){
			((Vector) arg).remove(whileCmd);
		}
		wcmd = new WhileCommand(exp, command);
		wcmd.setScope(scope);
		
		return wcmd;
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
			if(!direita.getTipo().equals(esquerdo.getTipo())){
				throw new SemanticException("visitExpression => Trying to compare different types of data");
			}
		}
		
		if(operador != null ){
			if(direita.getTipo().equals(esquerdo.getTipo()) && esquerdo.getTipo().equals("INT") &&
					direita.getTipo().equals("INT")){
				if(operador.value.equals(">") || operador.value.equals("<") || 
						operador.value.equals(">=") || operador.value.equals("<=") ||
						operador.value.equals("==") || operador.value.equals("!=")){
					expressionAST = new Expression("BOOL",esquerdo, operador, direita);
					
					return expressionAST;
				}
				
				
			}else if(direita.getTipo().equals(esquerdo.getTipo()) && esquerdo.getTipo().equals("BOOL") &&
					direita.getTipo().equals("BOOL")){
				if(operador.value.equals("==") || operador.value.equals("!=")){
					expressionAST = new Expression("BOOL",esquerdo, operador, direita);
					
					return expressionAST;
				}
				else{
					throw new SemanticException("visitExpression => Invalid Relational Operator");
				}
				
			}
			else{
				throw new SemanticException("visitExpression => Mismatch types");
			}
			
		}
			
		
		
		expressionAST = new Expression(esquerdo.getTipo(),esquerdo, operador, direita);
		
		return expressionAST;
	}

	public Object visitExpressionArithmetic(ExpressionArithmetic expAri, Object arg) throws SemanticException {
		ExpressionArithmetic expAST;
		ExpressionMultiplication esquerdo = null;
		ArrayList<ExpressionMultiplication> others = new ArrayList<ExpressionMultiplication>();
		ArrayList<Operator> operadores = expAri.getOperadores();
		int operadorIndex = 0;
		Object temp = expAri.getExpressionMultiplicationLeft();
		
		Object type1 = null;
		Object type2 = null;
		if(temp != null){
			type1 = ((ExpressionMultiplication)temp).visit(this, arg);
			if(type1 instanceof ExpressionMultiplication){
				esquerdo = ((ExpressionMultiplication)type1);
				esquerdo.setTipo(((ExpressionMultiplication)type1).getTipo());
			}
			
		
		}else{
			throw new SemanticException("visitExpressionArithmetic: error!");
		}
		
		Object right = expAri.getExpressionMultiplicationOthers();
		
		if(right != null && operadores != null){
			for(ExpressionMultiplication exp : expAri.getExpressionMultiplicationOthers()){
				type2 = exp.visit(this, arg);
							
				
				if(esquerdo.equals(type2) && esquerdo.getTipo().equals("INT")){
					((ExpressionMultiplication)type2).setTipo("INT");
					others.add(((ExpressionMultiplication)type2));
				}else{
					throw new SemanticException("visitExpressionArithmetic => Mismacth type");
				}
			}
		}
		else{
			return ((Factor)type1).getTipo();
		}
		
		expAST = new ExpressionArithmetic(esquerdo, operadores, others);
		expAST.setTipo(esquerdo.getTipo());
		return expAST;
	}

	public Object visitExpressionMultiplication(ExpressionMultiplication expMul, Object arg) throws SemanticException {
		ExpressionMultiplication expAST;
		ArrayList<Factor> others = new ArrayList<Factor>();
		Factor esquerdo = null;
		Object temp = expMul.getFactorLeft();
		Object f1;
		Object right = expMul.getFactorOthers();
		ArrayList<Operator> operadores = expMul.getOperadores();
		int operadorIndex = 0;
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
			 else if(f1 instanceof Expression){
				 esquerdo = ((Expression) f1);
			 }
		}
		else{
			throw new SemanticException("visitExpressionMultiplication: error!");
		}
			
		
		
		
		if(right != null){
			for(Factor f : expMul.getFactorOthers()){
				Object ftemp = f.visit(this, arg);
				String tipo_f = ((Factor)ftemp).getTipo().toString();
				if(esquerdo.getTipo().equals(tipo_f)){
					if(f instanceof Number){
						others.add(((Number)ftemp));
					}else if(f instanceof Bool){
						if(esquerdo.getTipo().equals("BOOL") && 
								(operadores != null && (!operadores.get(operadorIndex).value.equals("*") || !operadores.get(operadorIndex).value.equals("*")))){
							others.add(((Bool)ftemp));
						}else{
							throw new SemanticException("visitExpressionMultiplication => You cannot operate boolean values");
						}
					}
					else if(f instanceof Expression){
						others.add(((Expression)ftemp));
					 }
					else if(f instanceof Identifier){
						others.add(((Identifier)ftemp));
					}

					
				}
				else{
					
					throw new SemanticException("visitExpressionMultiplication => You cannot operate different types of data");
				}
				
			}
		}

		expAST = new ExpressionMultiplication(esquerdo, expMul.getOperadores(), others);
		expAST.setTipo (esquerdo.getTipo());
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
		
		return null;
	}

	public Object visitIdentifier(Identifier identifier, Object obj) throws SemanticException {
		Identifier idAST;
		AST ast = this.identificationTable.retrieve(identifier.getValue());
		if(ast != null){
		//System.out.println(identifier.getValue() + "\n");
			if(ast instanceof IntVariableDefinition){
				idAST = new Identifier(((IntVariableDefinition)ast).getIdentifier().getValue());
				idAST.setTipo("INT");
				idAST.setGlobal(((IntVariableDefinition)ast).getIdentifier().getGlobal());
				//idAST.setParameter(((BoolVariableDefinition)ast).getParameter());
				idAST.setPosition(((IntVariableDefinition)ast).getIdentifier().getPosition());
				
			}else if(ast instanceof BoolVariableDefinition){
				idAST = new Identifier(((BoolVariableDefinition)ast).getIdentifier().getValue());
				idAST.setTipo("BOOL");
				idAST.setGlobal(((BoolVariableDefinition)ast).getIdentifier().getGlobal());
				//idAST.setParameter(((BoolVariableDefinition)ast).getParameter());
				idAST.setPosition(((BoolVariableDefinition)ast).getIdentifier().getPosition());
			}
			else if(ast instanceof Identifier){
				idAST = new Identifier(((Identifier)ast).getValue());
				idAST.setTipo(((Identifier)ast).getTipo());
				idAST.setGlobal(((Identifier)ast).getGlobal());
				idAST.setParameter(((Identifier)ast).getParameter());
				idAST.setPosition(((Identifier)ast).getPosition());
			}
			else{
				throw new SemanticException("visitIdentifier => Variable has different type");
			}
			
			return idAST;
		}else{
			throw new SemanticException("visitIdentifier => Variable \'" + identifier.getValue() + "\' Not declared");
		}
	}

	public Object visitNumber(Number number, Object obj) throws SemanticException {

		//System.out.println(number.getValue() + "\n");
		number.setTipo("INT");
		return number;
	}

	public Object visitBoolean(Bool bool, Object obj) throws SemanticException {
		//System.out.println(bool.getValue());
		bool.setTipo("BOOL");
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
