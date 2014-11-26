package util.AST;

import checker.SemanticException;
import scanner.TokenKind;

public class AssignmentCommand extends Command{
	
	private Identifier identifier;
	private Operator equalSign;
	private Expression expression;
	private CallCommand callCommand;
	
	

	private void setEqualSign(){
		this.equalSign = new Operator("=");
	}

	public AssignmentCommand(Identifier identifier, Expression expression) {
		this.identifier = identifier;
		this.expression = expression;
		this.setEqualSign();
		super.tipo = this.expression.tipo;
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
