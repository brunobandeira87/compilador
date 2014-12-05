package util.AST;

import checker.SemanticException;
import scanner.TokenKind;

public class AssignmentCommand extends Command{
	
	private Identifier identifier;
	private Operator equalSign;
	private Expression expression;
	private CallCommand callCommand;
	private String tipo;
	

	private void setEqualSign(){
		this.equalSign = new Operator("=");
	}

	public AssignmentCommand(Identifier identifier, Expression expression) {
		this.identifier = identifier;
		this.expression = expression;
		this.setEqualSign();
		this.tipo = this.expression.getTipo();
	}
	
	public AssignmentCommand(Identifier identifier, CallCommand callCommand){
		this.identifier = identifier;
		this.callCommand = callCommand;
		this.setEqualSign();
	}
	
	public Identifier getIdentifier(){
		return this.identifier;
	}
	
	public Expression getExpression(){
		return this.expression;
	}
	
	public CallCommand getCallCommand(){
		return this.callCommand;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitAssignmentCommand(this, arg);
	}
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
