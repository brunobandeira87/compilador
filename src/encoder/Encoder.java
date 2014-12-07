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

	public Encoder() {
		this.file = new File("/home/bandeira/Desktop/teste.asm");
		this.asm = new Arquivo(this.file.toString(), this.file.toString());
		this.buffer = new StringBuffer();
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
			
		}
		
		return null;
	}

	@Override
	public Object visitFunctionDefintion(FunctionDefinition funcDef, Object arg) throws SemanticException {
		
		int variablesNumber;
		ArrayList<VariableDefinition> vars = funcDef.getVariable();
		
		if(funcDef.getIdentifier().getValue().equals("main")){
			this.buffer.append("\n_WinMain@16:\n");
		}
		else{
			this.buffer.append("_" + funcDef.getIdentifier().getValue() + ":\n");
		}
		
		this.buffer.append("\n\n\tpush ebp\n\tmov ebp, esp\n");
		
		
		variablesNumber = vars.size();
		
		if(variablesNumber > 0){
			variablesNumber *= 4;
			this.buffer.append("\n\tsub ebp, " + variablesNumber + "\n");
		}
		
		if(vars != null){
			for(VariableDefinition temp : vars){
				if(temp instanceof IntVariableDefinition){
					((IntVariableDefinition)temp).visit(this,arg);
				}
				else if(temp instanceof BoolVariableDefinition){
					((BoolVariableDefinition)temp).visit(this,arg);
				}
					
			}
		}
		
		/*
		 * 
		 * 	private String tipo;
	private ArrayList<Terminal> terminal = new ArrayList<Terminal>();
	private Identifier identifier;
	private ParametersPrototype parameters; 
	private ArrayList<Command> command;
	private ArrayList<VariableDefinition> variableDefinition;
	private boolean hasReturn;
	
		 * 
		 * */
		
		// TODO Auto-generated method stub
		
		
		this.buffer.append("\n\n\tmov esp, ebp");
        this.buffer.append("\n\tpop ebp");
        this.buffer.append("\n\tret\n");
		return null;
	}

	@Override
	public Object visitProcedureDefinition(ProcedureDefinition procDef, Object arg) throws SemanticException {
		

		int variablesNumber;
		ArrayList<VariableDefinition> vars = procDef.getVariableDefinition();
		
		if(procDef.getIdentifier().getValue().equals("main")){
			this.buffer.append("\n_WinMain@16:\n");
		}
		else{
			this.buffer.append("_" + procDef.getIdentifier().getValue() + ":\n");
		}
		
		this.buffer.append("\n\n\tpush ebp\n\tmov ebp, esp\n");
		
		
		variablesNumber = vars.size();
		
		if(variablesNumber > 0){
			variablesNumber *= 4;
			this.buffer.append("\n\tsub ebp, " + variablesNumber + "\n");
		}
		
		if(vars != null){
			for(VariableDefinition temp : vars){
				if(temp instanceof IntVariableDefinition){
					((IntVariableDefinition)temp).visit(this,arg);
				}
				else if(temp instanceof BoolVariableDefinition){
					((BoolVariableDefinition)temp).visit(this,arg);
				}
					
			}
		}
		
		/*
		 * 
		 * 	private String tipo;
			private ArrayList<Terminal> terminal = new ArrayList<Terminal>();
			private Identifier identifier;
			private ParametersPrototype parameters; 
			private ArrayList<Command> command;
			private ArrayList<VariableDefinition> variableDefinition;
			private boolean hasReturn;
	
		 * 
		 * */
		
		// TODO Auto-generated method stub
		
		
		this.buffer.append("\n\n\tmov esp, ebp");
        this.buffer.append("\n\tpop ebp");
        this.buffer.append("\n\tret\n");
		
		return null;
	}

	@Override
	public Object visitParametersPrototype(ParametersPrototype params,	Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitAssignmentCallCommand(AssignmentCallCommand assign,	Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitAssignmentCommand(AssignmentCommand assign, Object arg)	throws SemanticException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitIfCommand(IfCommand ifCmd, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitElseCommand(ElseCommand elseCmd, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitParametersCallCommand(ParametersCallCommand params,	Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitResultIsCommand(ResultIsCommand resultCmd, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
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
		
		if(left != null){
			left.visit(this, arg);
		}
		
		if(right != null){
			right.visit(this, arg);
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
				for(ExpressionMultiplication temp : right){
					Object valorRight = temp.visit(this, arg);
					//this.buffer.append("\n\tpush dword " + ((String)valorRight));
					
					if(numFactor+1 < right.size()){
						Object valorNext = right.get(numFactor+1).visit(this, arg);
						this.buffer.append("\n\tpush dword " + ((String)valorRight));
						this.buffer.append("\n\tpush dword " + ((String)valorNext));
						Operator opTemp = operadores.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						this.buffer.append("\n\tpop ebx");
						this.buffer.append("\n\tpop eax");
						this.buffer.append("\n\t" +lastOp +" eax, ebx");
						this.buffer.append("\n\tpush dword eax");
						numOp++;
						numFactor++;
					}else{
						break;
					}
					lastOp = (String)operadores.get(0).visit(this, arg);
					/*
					if(operators != null && operators.size() >= numOp){
						Operator opTemp = operators.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						
						numOp++;
					}
					*/
				}
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
				for(Factor temp : others){
					Object valorRight = temp.visit(this, arg);
					//this.buffer.append("\n\tpush dword " + ((String)valorRight));
					
					if(numFactor+1 < others.size()){
						Object valorNext = others.get(numFactor+1).visit(this, arg);
						this.buffer.append("\n\tpush dword " + ((String)valorRight));
						this.buffer.append("\n\tpush dword " + ((String)valorNext));
						Operator opTemp = operators.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						this.buffer.append("\n\tpop ebx");
						this.buffer.append("\n\tpop eax");
						this.buffer.append("\n\t" +lastOp +" eax, ebx");
						this.buffer.append("\n\tpush dword eax");
						numOp++;
						numFactor++;
					}else{
						break;
					}
					lastOp = (String)operators.get(0).visit(this, arg);
					/*
					if(operators != null && operators.size() >= numOp){
						Operator opTemp = operators.get(numOp);
						lastOp = (String)(opTemp.visit(this, arg));
						
						numOp++;
					}
					*/
				}
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
		// TODO Auto-generated method stub
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
			return "mult";
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
