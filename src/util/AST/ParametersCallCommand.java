package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class ParametersCallCommand extends AST {

	private ArrayList<Factor> identifier;
	private ArrayList<Operator> terminal;
	

	public ParametersCallCommand(ArrayList<Factor> identifier,
			ArrayList<Operator> terminal) {
		this.identifier = identifier;
		this.terminal = terminal;
	}

	public ParametersCallCommand(ArrayList<Factor> identifier) {
		this.identifier = identifier;
	}
	
	public ArrayList<Factor> getParams(){
		return this.identifier;
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
