package util.AST;

import checker.SemanticException;

public class ContinueCommand extends Command{
	private ReservedWord reservedWord;
	
	public ContinueCommand(ReservedWord reservedWord) {
		this.reservedWord = reservedWord;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
	
		return v.visitContinueCommand(this, arg);
	}

}
