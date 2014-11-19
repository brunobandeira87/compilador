package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class Program extends AST{
	private String id = "Program";
	private ArrayList<VariableGlobalDefinition> variableGlobalDefinition ;
	private FunctionProcedureDefinitionList functionProcedureDefinitionList;
	
	/*
	 * Construtores
	 * */
	
	public Program(FunctionProcedureDefinitionList functionProcedureDefinitionList){
		this.functionProcedureDefinitionList = functionProcedureDefinitionList;
	}
	
	public Program(ArrayList<VariableGlobalDefinition> variableGlobalDefinition, FunctionProcedureDefinitionList functionProcedureDefinitionList){
		this.functionProcedureDefinitionList = functionProcedureDefinitionList;
		this.variableGlobalDefinition = variableGlobalDefinition;
	}
	
	
	
	/*
	 * Getters
	 * */
	 
	public ArrayList<VariableGlobalDefinition> getVariableGlobalDefinition(){
		return this.variableGlobalDefinition;
	}
	
	public FunctionProcedureDefinitionList getFunctionProcedureDefinitionList(){
		return this.functionProcedureDefinitionList;
	}
	
	public String getId(){
		return this.id;
	}
	
	
	@Override
	public String toString(int level) {
		StringBuffer vg = new StringBuffer();
		StringBuffer fpl = new StringBuffer();
		
		if(this.variableGlobalDefinition != null){
			for(VariableGlobalDefinition vgd : this.variableGlobalDefinition){
				vg.append(vgd.toString(level));
				vg.append(" ");
			}
		}
		if(this.functionProcedureDefinitionList != null){
			fpl.append(this.functionProcedureDefinitionList.toString(level));
			fpl.append(" ");
		}
		
		
		return (super.getSpaces(level) + id + "\n" + vg + "\t" + fpl);
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitProgram(this, arg);
	}

}
