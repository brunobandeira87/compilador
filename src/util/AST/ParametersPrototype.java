package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class ParametersPrototype extends AST {
	private ArrayList<Tipo> tipo = new ArrayList<Tipo>(); 
	private ArrayList<Identifier> identifier = new ArrayList<Identifier>();
	private ArrayList<Operator> virg = new ArrayList<Operator>() ;
	
	
	
	public ParametersPrototype(Tipo tipo, Identifier identifier) {
		this.tipo.add(tipo);
		this.identifier.add(identifier);
		
	}
	
	public ParametersPrototype(ArrayList<Tipo> tipo, ArrayList<Identifier> identifier, ArrayList<Operator> virg) {
		this.tipo = tipo;
		this.identifier = identifier;
		this.virg = virg;
		
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		
		return v.visitParametersPrototype(this, arg);
	}

}
