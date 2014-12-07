package encoder;

import java.io.File;
import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.ExpressionNode;

import checker.SemanticException;
import sun.org.mozilla.javascript.internal.ast.VariableDeclaration;
import util.Arquivo;
import util.AST.*;
import util.AST.Number;


public class Encoder implements Visitor{
	
	
	private File file;
	private Arquivo asm;
	private StringBuffer buffer;
	private int numberLocalVar;

	public Encoder() {
		this.file = new File("/home/bandeira/Desktop/teste.asm");
		this.asm = new Arquivo(this.file.toString(), this.file.toString());
		this.buffer = new StringBuffer();
		this.numberLocalVar = 0;
	}
	
	public void encode(AST ast) throws SemanticException{
		
		ast.visit(this, null);
		this.asm.close();
	}
	
	@Override
	public Object visitProgram(Program program, Object arg) throws SemanticException {
		 ArrayList<VariableGlobalDefinition> variableGlobalDefinition = program.getVariableGlobalDefinition();
		 ArrayList<CallableDefinition> functionsProcedures = program.getFunctionProcedureDefinitionList().getCallableDefinition();
		 /*
		  * this.asm.println("extern _printf");
			 this.asm.println("");
			 this.asm.println("SECTION .data");
		  */
		 
		 this.buffer.append("extern _printf\n\nSECTION .data\n");
		if(variableGlobalDefinition != null){
			 for(VariableGlobalDefinition var : variableGlobalDefinition){
				 Object temp = var.visit(this, arg);
				 
			 }
		}
		
		this.buffer.append("\nintFormat: db \"%d\", 10, 0\n");
		
		this.buffer.append("\nSECTION .text\n\tglobal _WinMain@16\n");
		for(CallableDefinition temp : functionsProcedures){
			if(temp instanceof ProcedureDefinition){
				( (ProcedureDefinition)temp ).visit(this,arg);
			}
			else if(temp instanceof FunctionDefinition){
				( (FunctionDefinition) temp).visit(this, arg);
			}
		}
		
		this.asm.println(this.buffer.toString());
		return null;
	}

	@Override
	public Object visitVariableGlobalDefinition(VariableGlobalDefinition var, Object arg) throws SemanticException {
		VariableDefinition variavel ;
		variavel = var.getVariableDefinition();
		if(variavel != null){
			if(variavel instanceof IntVariableDefinition){
				Object temp = ((IntVariableDefinition)variavel).visit(this, var);
			}
			else{
				Object temp = ((BoolVariableDefinition)variavel).visit(this, var);
			}
				
			//this.asm.println(variavel.getIdentifier() + ": dd ");
		}
		return null;
	}

	@Override
	public Object visitFunctionProcedureDefinitionList(	FunctionProcedureDefinitionList funcProcDef, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitIntVariableDefinition(IntVariableDefinition intVarDef, Object arg) throws SemanticException {
		
		Expression exp = intVarDef.getExpression();
		
		exp.visit(this, arg);
		
		if(arg instanceof VariableGlobalDefinition){
			
		}
		else{
			this.buffer.append("\n\tpop [ebp-" + intVarDef.position*4 + "]");
			
		}
		
		return null;
	}

	@Override
	public Object visitBoolVariableDefinition(	BoolVariableDefinition boolVarDef, Object arg) throws SemanticException {
		
		Expression exp = boolVarDef.getExpression();
		
		exp.visit(this, arg);
		
		if(arg instanceof VariableGlobalDefinition){
			
		}
		else{
			this.buffer.append("\n\tpop [ebp-" + boolVarDef.position*4 + "]");
		}
		
		return null;
	}

	@Override
	public Object visitFunctionDefintion(FunctionDefinition funcDef, Object arg) throws SemanticException {
		
		ArrayList<VariableDefinition> vars = funcDef.getVariable();
		ParametersPrototype params = funcDef.getParams();
		ArrayList<Command> commands = funcDef.getCommand();
		int variablesNumber;
		
		
		if(funcDef.getIdentifier().getValue().equals("main")){
			this.buffer.append("\n_WinMain@16:\n");
		}
		else{
			this.buffer.append("_" + funcDef.getIdentifier().getValue() + ":\n");
		}
		
		this.buffer.append("\n\n\tpush ebp\n\tmov ebp, esp\n");
		
		if(params != null){
			params.visit(this, funcDef);
		}
		
		variablesNumber = vars.size();
		
		if(variablesNumber > 0){
			variablesNumber *= 4;
			this.buffer.append("\n\tsub ebp, " + variablesNumber + "\n");
		}
		
		if(vars != null){
			for(VariableDefinition temp : vars){
				this.numberLocalVar++;
				if(temp instanceof IntVariableDefinition){
					((IntVariableDefinition)temp).visit(this,vars);
				}
				else if(temp instanceof BoolVariableDefinition){
					((BoolVariableDefinition)temp).visit(this,vars);
				}
				
				
					
			}
		}
		
		if(commands.isEmpty() == false){
			
			for(Command cmd : commands){
				if(cmd instanceof AssignmentCommand){
					((AssignmentCommand)cmd).visit(this, funcDef);
				}
				else if(cmd instanceof ResultIsCommand){
					((ResultIsCommand)cmd).visit(this, funcDef);
				}
				else if(cmd instanceof PrintCommand){
					((PrintCommand)cmd).visit(this, funcDef);
				}
				else if(cmd instanceof IfCommand){
					((IfCommand)cmd).visit(this, funcDef);
				}
			}
			
		}
		

		this.buffer.append("\n\n\tmov esp, ebp");
        this.buffer.append("\n\tpop ebp");
        this.buffer.append("\n\tret\n");
        this.numberLocalVar = 0;
		return null;
	}

	@Override
	public Object visitProcedureDefinition(ProcedureDefinition procDef, Object arg) throws SemanticException {
		ParametersPrototype params = procDef.getParams();

		int variablesNumber;
		ArrayList<VariableDefinition> vars = procDef.getVariableDefinition();
		ArrayList<Command> command = procDef.getCommands();
		
		if(procDef.getIdentifier().getValue().equals("main")){
			this.buffer.append("\n_WinMain@16:\n");
		}
		else{
			this.buffer.append("_" + procDef.getIdentifier().getValue() + ":\n");
		}
		
		this.buffer.append("\n\n\tpush ebp\n\tmov ebp, esp\n");
		
		if(params != null){
			params.visit(this, procDef);
		}
		
		variablesNumber = vars.size();
		
		if(variablesNumber > 0){
			variablesNumber *= 4;
			this.buffer.append("\n\tsub ebp, " + variablesNumber + "\n");
		}
		
		if(vars != null){
			for(VariableDefinition temp : vars){
				this.numberLocalVar++;
				if(temp instanceof IntVariableDefinition){
					((IntVariableDefinition)temp).visit(this,arg);
				}
				else if(temp instanceof BoolVariableDefinition){
					((BoolVariableDefinition)temp).visit(this,arg);
				}
					
			}
		}
		
		if(command.isEmpty() == false){
			for(Command com : command){
				if(com instanceof AssignmentCommand){
					((AssignmentCommand)com).visit(this, procDef);
				}
				else if(com instanceof PrintCommand){
					((PrintCommand)com).visit(this, procDef);
				}
				else if(com instanceof IfCommand){
					((IfCommand)com).visit(this, procDef);
				}
			}
		}

		this.buffer.append("\n\n\tmov esp, ebp");
        this.buffer.append("\n\tpop ebp");
        this.buffer.append("\n\tret\n");
        this.numberLocalVar = 0;
		
		return null;
	}

	@Override
	public Object visitParametersPrototype(ParametersPrototype params,	Object arg) throws SemanticException {
		ArrayList<Identifier> identifier = params.getIdentifier();
		
		if(identifier.isEmpty() == false){
		
			for(Identifier id : identifier){
				
				this.buffer.append("\n\tpush dword [ebp+" + (id.getPosition()*4 + 4) + "]");
			}
		}
		
		
		return null;
	}

	@Override
	public Object visitAssignmentCallCommand(AssignmentCallCommand assign,	Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitAssignmentCommand(AssignmentCommand assign, Object arg)	throws SemanticException {
		Identifier identifier =  assign.getIdentifier();
		Expression exp = assign.getExpression();
		//System.out.println(identifier.getPosition());
		
		if(exp != null){
			exp.visit(this, arg);
		}
		if(assign.getIdentifier().getGlobal()){
			
		}
		else if(assign.getIdentifier().getParameter()){
			this.buffer.append("\n\tpop dword [ebp+" + (assign.getIdentifier().getPosition() *4 +4) + "]");
		}
		else{
			this.buffer.append("\n\tpop dword [ebp-" + (assign.getIdentifier().getPosition() *4) + "]");
		}
		
		return null;
	}

	@Override
	public Object visitCallCommand(CallCommand callCmd, Object arg)	 throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitContinueCommand(ContinueCommand continueCmd, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitBreakCommand(BreakCommand breakCmd, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitPrintCommand(PrintCommand printCmd, Object arg)	throws SemanticException {
	
		
		Identifier identifier = printCmd.getIdentifier();
		
		if(identifier != null){
			if(identifier.getGlobal() == false && identifier.getParameter() == false){
				
				this.buffer.append("\n\tpush dword [ebp-" + (identifier.getPosition() * 4) +"]");
			}
			else if(identifier.getGlobal()){
				
			}
			else if(identifier.getParameter()){
				this.buffer.append("\n\tpush dword [ebp+" + (identifier.getPosition() * 4 + 4) +"]");
			}
			
			this.buffer.append("\n\tpush dword intFormat");
			this.buffer.append("\n\tcall _printf");
			this.buffer.append("\n\tadd esp, 8\n\n");
		}
		
		return null;
	}

	@Override
	public Object visitIfCommand(IfCommand ifCmd, Object arg) throws SemanticException {
		Expression exp = ifCmd.getExpression();
		Operator op = exp.getOperator();
		ElseCommand elseCmd = ifCmd.getElse();
		exp.visit(this,arg);
		
		ArrayList<Command> commands = ifCmd.getCommand();
		
		String jump = "\nj";
		if(arg instanceof FunctionDefinition){
			//this.buffer.append("\n\tcmp eax, ebx");
			if(op != null){
				if(op.value.equals("==")){
					jump += "ne ";
				}
				else if(op.value.equals("!=")){
					jump += "e ";
				}
				else if(op.value.equals(">")){
					jump += "le ";
				}
				else if(op.value.equals("<")){
					jump += "ge ";
				}
				else if(op.value.equals(">=")){
					jump += "l ";
				}
				else if(op.value.equals("=<")){
					jump += "g ";
				}
			}
			if(elseCmd != null){
				this.buffer.append("\n\t" + jump + "_" + ((FunctionDefinition)arg).getIdentifier().getValue() + "_Else_Block \n");
			}
			else{
				this.buffer.append("\n\tjmp _" + ((FunctionDefinition)arg).getIdentifier().getValue() + "_End_If \n");
				elseCmd.visit(this, arg);
			}
			
			if(commands != null){
				for(Command com : commands){
					if(com instanceof PrintCommand){
						((PrintCommand)com).visit(this, ifCmd);
					}
				}
			}
		}
		else if(arg instanceof ProcedureDefinition){
			if(op != null){
				if(op.value.equals("==")){
					jump += "ne ";
				}
				else if(op.value.equals("!=")){
					jump += "e ";
				}
				else if(op.value.equals(">")){
					jump += "le ";
				}
				else if(op.value.equals("<")){
					jump += "ge ";
				}
				else if(op.value.equals(">=")){
					jump += "l ";
				}
				else if(op.value.equals("=<")){
					jump += "g ";
				}
				if(elseCmd != null){
					
					this.buffer.append("\n\t" + jump + "_" + ((ProcedureDefinition)arg).getIdentifier().getValue() + "_Else_Block \n");
				}
				else{
					this.buffer.append("\n\t" + jump + "_" + ((ProcedureDefinition)arg).getIdentifier().getValue() + "_End_If \n");
				}
				if(commands != null){
					
					for(Command cmd : commands){
						if(cmd instanceof PrintCommand){
							((PrintCommand)cmd).visit(this, ifCmd);
						}
					}
					//this.buffer.append()
				}
				if(elseCmd != null){
					elseCmd.visit(this, arg);
				}
				
				this.buffer.append("\n\t_" + ((ProcedureDefinition)arg).getIdentifier().getValue() + "_End_If: \n");
			}
		}
		
		
			return null;
	}

	@Override
	public Object visitElseCommand(ElseCommand elseCmd, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		
		ArrayList<Command> commands = elseCmd.getCommand();
		if(arg instanceof ProcedureDefinition){
		
			this.buffer.append("\n\t_" + ((ProcedureDefinition)arg).getIdentifier().getValue() + "_Else_Block: \n");
			
			
		}
		else if(arg instanceof FunctionDefinition){
			
		}
		
		return null;
	}

	@Override
	public Object visitParametersCallCommand(ParametersCallCommand params,	Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitResultIsCommand(ResultIsCommand resultCmd, Object arg) throws SemanticException {
		Expression exp = resultCmd.getExpression();
		if(resultCmd.getTipo().equals("INT")){
			exp.visit(this, arg);
			this.buffer.append("\n\tpop eax\n");
		}
		
		return null;
	}

	@Override
	public Object visitWhileCommand(WhileCommand whileCmd, Object arg)	throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitExpression(Expression expression, Object arg) throws SemanticException {
		
		ExpressionArithmetic left = expression.getLeft();
		ExpressionArithmetic right = expression.getRight();
		Operator op = expression.getOperator();
		
		if(left != null && right != null){
			left.visit(this, arg);
			right.visit(this, arg);
			this.buffer.append("\n\tpop ebx");
			this.buffer.append("\n\tpop eax");
			this.buffer.append("\n\tcmp eax,ebx");
		}
		
		else if(left != null){
			left.visit(this, arg);
		}
		
		return null;
	}

	@Override
	public Object visitExpressionArithmetic(ExpressionArithmetic expAri, Object arg) throws SemanticException {
		
		ExpressionMultiplication left = expAri.getExpressionMultiplicationLeft();
		ArrayList<ExpressionMultiplication> right = expAri.getExpressionMultiplicationOthers();
		ArrayList<Operator> operadores = expAri.getOperadores();
		Object valorLeft = null;
		String lastOp = null;
		
		if(left != null){
			valorLeft = left.visit(this, arg);
			if(((String)valorLeft) != null)
				this.buffer.append("\n\tpush dword " + ((String)valorLeft));
		}
		
		if(right.isEmpty() == false){
			
			int numOp = 1;
			
			if(right.size() == 1){
				ExpressionMultiplication temp = right.get(0);
				Object valorRight = temp.visit(this, arg);
				Operator opTemp = operadores.get(0);
				lastOp = (String) (opTemp.visit(this,arg));
				if(valorRight != null)
					this.buffer.append("\n\tpush dword " + ( (String) valorRight));
			}
			else{
				int numFactor = 0; 
				for(int j = 0 ; j < right.size() ; j += 2){
				//for(ExpressionMultiplication temp : right){
					Object valorRight = right.get(j).visit(this, arg);
					
					//this.buffer.append("\n\tpush dword " + ((String)valorRight));
					
					if(j+1 < right.size()){
						Object valorNext = right.get(j+1).visit(this, arg);
						if(valorRight != null)
							this.buffer.append("\n\tpush dword " + ((String)valorRight));
						if(valorNext != null)
							this.buffer.append("\n\tpush dword " + ((String)valorNext));
						Operator opTemp = operadores.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						this.buffer.append("\n\tpop ebx");
						this.buffer.append("\n\tpop eax");
						this.buffer.append("\n\t" +lastOp +" eax, ebx");
						this.buffer.append("\n\tpush dword eax");
						numOp++;
						numFactor += 2;
					}else{
						Object valorNext = right.get(j).visit(this, arg);
						lastOp = (String) operadores.get(j).visit(this, arg);
						this.buffer.append("\n\tpush dword " + ((String)valorNext));
						this.buffer.append("\n\tpop ebx");
						this.buffer.append("\n\tpop eax");
						this.buffer.append("\n\t" +lastOp +" eax, ebx");
						this.buffer.append("\n\tpush dword eax");
					}
					/*
					if(operators != null && operators.size() >= numOp){
						Operator opTemp = operators.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						
						numOp++;
					}
					*/
				}
				lastOp = (String)operadores.get(0).visit(this, arg);
			}
			
			this.buffer.append("\n\tpop ebx ");
			this.buffer.append("\n\tpop eax ");
			this.buffer.append("\n\t" + lastOp + " eax, ebx");
			this.buffer.append("\n\tpush dword eax");
			
		}
		else{
			return null;
		}
		
		
		// for(ExpressionMultiplication temp : right){
		//temp.visit(this, arg);
		//}
		
		return null;
	}

	@Override
	public Object visitExpressionMultiplication(ExpressionMultiplication expMul, Object arg) throws SemanticException {
		
		Factor left = expMul.getFactorLeft();
		ArrayList<Factor> others = expMul.getFactorOthers();
		ArrayList<Operator> operators = expMul.getOperadores();
		String lastOp = null;
		Object valorLeft = null;
		if(left != null){
			valorLeft = left.visit(this, arg);
			if(((String)valorLeft) != null)
				this.buffer.append("\n\tpush dword " + ((String)valorLeft));
		}
		
		if(others.isEmpty() == false){
/*			if(((String)valorLeft) != null)
				this.buffer.append("\n\tpush dword " + ((String)valorLeft));
				*/
			int numOp = 1;
			
			if(others.size() == 1){
				Factor temp = others.get(0);
				Object valorRight = temp.visit(this, arg);
				Operator opTemp = operators.get(0);
				lastOp = (String) (opTemp.visit(this,arg));
				this.buffer.append("\n\tpush dword " + ( (String) valorRight));
			}
			else{
				int numFactor = 0;
				for(int j = 0 ; j < others.size() ; j += 2){
				//for(Factor temp : others){
					Object valorRight = others.get(j).visit(this, arg);
					//this.buffer.append("\n\tpush dword " + ((String)valorRight));
					
					if(j+1 < others.size()){
						//Object valorNext = others.get(numFactor+1).visit(this, arg);
						Object valorNext = others.get(j+1).visit(this, arg);
						this.buffer.append("\n\tpush dword " + ((String)valorRight));
						this.buffer.append("\n\tpush dword " + ((String)valorNext));
						Operator opTemp = operators.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						this.buffer.append("\n\tpop ebx");
						this.buffer.append("\n\tpop eax");
						this.buffer.append("\n\t" +lastOp +" eax, ebx");
						this.buffer.append("\n\tpush dword eax");
						numOp += 2;
						numFactor += 2;
					}else{
						Object valorNext = others.get(j).visit(this, arg);
						lastOp = (String) operators.get(j).visit(this, arg);
						this.buffer.append("\n\tpush dword " + ((String)valorNext));
						this.buffer.append("\n\tpop ebx");
						this.buffer.append("\n\tpop eax");
						this.buffer.append("\n\t" +lastOp +" eax, ebx");
						this.buffer.append("\n\tpush dword eax");
					}
					
					/*
					if(operators != null && operators.size() >= numOp){
						Operator opTemp = operators.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						
						numOp++;
					}
					*/
				}
				lastOp = (String)operators.get(0).visit(this, arg);
			}
			
			this.buffer.append("\n\tpop ebx ");
			this.buffer.append("\n\tpop eax ");
			this.buffer.append("\n\t" + lastOp + " eax, ebx");
			this.buffer.append("\n\tpush dword eax");
			
		}
		else{
			return null;
		}
		
		return null;
	}

	@Override
	public Object visitFactor(Factor factor, Object arg) throws SemanticException {
		
		if(factor instanceof Number){
			return ((Number) factor).visit(this, arg);
		}
		else if(factor instanceof Bool){
			return ((Bool)factor).visit(this, arg);
					
					
					
		}
		
		return null;
	}

	@Override
	public Object visitIdentifier(Identifier identifier, Object obj) throws SemanticException {
		if(identifier.getTipo().equals("INT")){
			if(identifier.getParameter()){
				this.buffer.append("\n\tpush dword [ebp+" + (identifier.getPosition()*4 +4) + "]");
			}
			else if(identifier.getGlobal() == false && identifier.getParameter() == false){
				this.buffer.append("\n\tpush dword [ebp-" + (identifier.getPosition() * 4) + "]");
			}
			else if(identifier.getGlobal()){
				this.buffer.append("\n\tpush dword [ebp+");
			}
		}
		return null;
	}

	@Override
	public Object visitNumber(Number number, Object obj) throws SemanticException {
		
		
		return ((Number) number).getValue().toString();
		
		
	}

	@Override
	public Object visitBoolean(Bool bool, Object obj) throws SemanticException {
		
		if( ((Bool) bool).getValue().toString().equals("TRUE"))
			return "1";
		else
			return "0";
		
		
	}

	@Override
	public Object visitOperator(Operator operator, Object obj)	throws SemanticException {
		
		if(operator.value.equals("+")){
			return "add";
		}
		else if(operator.value.equals("-")){
			return "sub";
		}
		else if(operator.value.equals("*")){
			return "imul";
		}
		else if(operator.value.equals("/")){
			return "div";
		}
		else if(operator.value.equals(">")){
			
		}
		
		return null;
	}

	@Override
	public Object visitReservedWord(ReservedWord reservedWord, Object obj)	throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitTipo(Tipo tipo, Object obj) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}


	

}
