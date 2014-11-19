package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class FunctionProcedureDefinitionList extends AST{
	private ArrayList<ReservedWord> reservedWord;
	private ArrayList<CallableDefinition> callabledefinition;
	
	public FunctionProcedureDefinitionList(ArrayList<ReservedWord> reservedWord,
			ArrayList<CallableDefinition> callabledefinition) {
		
		this.reservedWord = reservedWord;
		this.callabledefinition = callabledefinition;
	}
	
	public ArrayList<ReservedWord> getReservedWords(){
		return this.reservedWord;
	}
	
	public ArrayList<CallableDefinition> getCallableDefinition(){
		return this.callabledefinition;
	}
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		
		StringBuffer cd = new StringBuffer();
		StringBuffer rw = new StringBuffer();
		for(ReservedWord x : this.reservedWord ){
			cd.append(x.toString(level+1));
			cd.append(" ");
		}
		
		for(CallableDefinition y : this.callabledefinition){
			rw.append(y.toString(level+1));
			rw.append(" ");
		}
		
		return rw.toString() + "\n" + cd.toString();
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitFunctionProcedureDefinitionList(this, arg);
	}
	
	
	
}
