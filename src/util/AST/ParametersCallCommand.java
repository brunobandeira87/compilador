package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class ParametersCallCommand extends AST {

	private ArrayList<Identifier> identifier;
	private ArrayList<Operator> terminal;
	

	public ParametersCallCommand(ArrayList<Identifier> identifier,
			ArrayList<Operator> terminal) {
		this.identifier = identifier;
		this.terminal = terminal;
	}

	public ParametersCallCommand(ArrayList<Identifier> identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitParametersCallCommand(this, arg);
	}
	
	
}
