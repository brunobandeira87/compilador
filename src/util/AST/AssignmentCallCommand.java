package util.AST;

import checker.SemanticException;

public class AssignmentCallCommand extends Factor{

	private CallCommand callCommand;
	
	
	public AssignmentCallCommand(CallCommand callCommand) {
		// TODO Auto-generated constructor stub
		
		this.callCommand = callCommand;
	}
	
	public CallCommand getCallCommand(){
		return this.callCommand;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitAssignmentCallCommand(this, arg);
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AssignmentCallCommand){
			return this.tipo.equals(((AssignmentCallCommand)obj).tipo);
		}else{
			return false;
		}
	}
}
