
package parser;

import java.util.ArrayList;

import com.sun.corba.se.spi.ior.IdentifiableContainerBase;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import scanner.*;
import util.AST.*;
import util.AST.Number;


public class Parser {

	private BCPLScanner scanner = null;
	private Token currentToken = null;
	
	
	/**
	 * Parser constructor
	 */
	public Parser() {
		this.scanner = new BCPLScanner();
		

		try {
			this.currentToken = this.scanner.getNextToken();
		}
		catch (LexicalException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifies if the current token kind is the expected one
	 *
	 * @param kind
	 * @throws SyntacticException
	 */
	
	private void accept(TokenKind kind) throws SyntacticException {
		if (this.currentToken.getKind() == kind) {
			try {
				this.currentToken = this.scanner.getNextToken();
			}
			catch (LexicalException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new SyntacticException(
				"Expected token " + kind, currentToken);
		}
	}

	/**
	 * Gets next token
	 */
	private void acceptIt() {
		try {
			this.currentToken = this.scanner.getNextToken();
		}
		catch (LexicalException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Verifies if the source program is syntactically correct
	 *
	 * @throws SyntacticException
	 */
	
	public AST parse() throws SyntacticException{
		
		AST ast = parseProgram();
		return ast;
	}
	
	private Program parseProgram() throws SyntacticException{
		Program program ;
		FunctionProcedureDefinitionList funcProcAST = null;
		ArrayList<VariableGlobalDefinition> variableGlobalDefintion = new ArrayList<VariableGlobalDefinition>();
		boolean hasGlobalVariable = false;
		while(this.currentToken.getKind() == TokenKind.GLOBAL){
			variableGlobalDefintion.add(parseVariableGlobalDefinition());
			hasGlobalVariable = true;
		}
		if(this.currentToken.getKind() == TokenKind.LET){
			funcProcAST  = parseFuncProcDefinitionList();
		}
		else{
			//throw new SyntacticException("HAUH", currentToken);
		}
		if(hasGlobalVariable){
			program = new Program(variableGlobalDefintion, funcProcAST);
		}else{
			program = new Program(funcProcAST);
		}
		//System.out.println(program.toString());
		
		return program;
	}
	
	private VariableGlobalDefinition parseVariableGlobalDefinition() throws SyntacticException{
		VariableGlobalDefinition variableGlobalDefinition;
		VariableDefinition variableDefinition;
		accept(TokenKind.GLOBAL);
		variableDefinition = parseVariableDefinition();
		variableGlobalDefinition = new VariableGlobalDefinition(variableDefinition);
		return variableGlobalDefinition;
	}
	
	private VariableDefinition parseVariableDefinition() throws SyntacticException{
		VariableDefinition variableDefinitionAST = null;
		accept(TokenKind.LET);
		if(this.currentToken.getKind() == TokenKind.INT){
			variableDefinitionAST = parseIntVariableDefinition();
		}else if(this.currentToken.getKind() == TokenKind.BOOL){
			variableDefinitionAST = parseBoolVariableDefinition();
		}
		else{
			System.out.println("Treta");
		}
		return variableDefinitionAST;
	}
	
	
	private IntVariableDefinition parseIntVariableDefinition() throws SyntacticException{
		IntVariableDefinition intVariableDefinitionAST;
		Expression expression;
		Identifier identifier;
		Terminal equalSign;
		
		
		if(this.currentToken.getKind() == TokenKind.INT){
			
			accept(TokenKind.INT);
			identifier = new Identifier(this.currentToken.getSpelling());
			accept(TokenKind.IDENTIFIER);
			equalSign = new Operator(this.currentToken.getSpelling());
			accept(TokenKind.OP_ATTR);
			expression = parseExpression();
		}else{
			throw new SyntacticException("ERROR!", currentToken);
		}
		if(this.currentToken.getKind() == TokenKind.SEMICOL)
			accept(TokenKind.SEMICOL);
		intVariableDefinitionAST = new IntVariableDefinition(identifier, equalSign, expression);
		return intVariableDefinitionAST;
		
	}
	
	private BoolVariableDefinition parseBoolVariableDefinition() throws SyntacticException{
		
		BoolVariableDefinition boolVariableDefinitionAST;
		
		Identifier identifier;
		Expression expression;
		
		if(this.currentToken.getKind() == TokenKind.BOOL){
			
			accept(TokenKind.BOOL);
			identifier = new Identifier(this.currentToken.getSpelling());
			accept(TokenKind.IDENTIFIER);
			accept(TokenKind.OP_ATTR);
			expression = parseExpression();
		}
		else{
			throw new SyntacticException("ERROR!", currentToken);
		}
		if(this.currentToken.getKind() == TokenKind.SEMICOL)
			accept(TokenKind.SEMICOL);
		boolVariableDefinitionAST = new BoolVariableDefinition(identifier, expression);
		return boolVariableDefinitionAST;
	}
	
	private FunctionProcedureDefinitionList parseFuncProcDefinitionList() throws SyntacticException{
		ArrayList<ReservedWord> reservedWord = new ArrayList<ReservedWord>();
		ArrayList<CallableDefinition> callableDefinition = new ArrayList<CallableDefinition>();
		FunctionProcedureDefinitionList funcprocAST = null;
		
		if(this.currentToken.getKind() == TokenKind.LET){
			reservedWord.add(new ReservedWord(TokenKind.LET.toString()));
			accept(TokenKind.LET);
			callableDefinition.add(parseCallableDefinition());
			while(this.currentToken.getKind() == TokenKind.AND){
				reservedWord.add(new ReservedWord(TokenKind.AND.toString()));
				accept(TokenKind.AND);
				callableDefinition.add(parseCallableDefinition());
			}
			accept(TokenKind.EOF);
		}
					
		else{
			throw new SyntacticException("ERROR! " + currentToken.toString(), currentToken);
		}
		funcprocAST = new FunctionProcedureDefinitionList(reservedWord, callableDefinition);
		
		return funcprocAST;
	}
	
	private CallableDefinition parseCallableDefinition() throws SyntacticException{
		
		CallableDefinition callAST = null;
		
		if(this.currentToken.getKind() == TokenKind.INT ||
				this.currentToken.getKind() == TokenKind.BOOL){
			callAST = parseFunctionDefinition();

		}else if(this.currentToken.getKind() == TokenKind.VOID){
			
			callAST = parseProcedureDefinition();
		}
		
		return callAST;
		
	}
	//functionDefinition       ::=    ('INT' | 'BOOL') Identifier '(' (parametersPrototype)? ')' '=' 'VALOF' '{' varDefinition* command* '}'
	private FunctionDefinition parseFunctionDefinition() throws SyntacticException{
		FunctionDefinition funcAST;
		Tipo tipo;
		Identifier identifier;
		ParametersPrototype parametersPrototype = null;
		ArrayList<VariableDefinition> variableDefinition = new ArrayList<VariableDefinition>();
		ArrayList<Command> command = new ArrayList<Command>();
		boolean hasVariable, hasCommand;
		hasVariable = hasCommand = false;
		
		switch(this.currentToken.getKind()){
			case INT:
			case BOOL:
				tipo = new Tipo(this.currentToken.getKind().toString(), this.currentToken.getSpelling());
				acceptIt();
				identifier = new Identifier(this.currentToken.getSpelling());
				accept(TokenKind.IDENTIFIER);
				accept(TokenKind.LPAR);
				if(this.currentToken.getKind() == TokenKind.INT || this.currentToken.getKind() == TokenKind.BOOL){
					parametersPrototype = parseParametersPrototype();
				}
				accept(TokenKind.RPAR);
				accept(TokenKind.OP_ATTR);
				accept(TokenKind.VALOF);
				accept(TokenKind.LCURL);
				while(this.currentToken.getKind() == TokenKind.LET){
					hasVariable = true;
					variableDefinition.add(parseVariableDefinition());
				}
				while(this.currentToken.getKind() == TokenKind.WHILE || this.currentToken.getKind() == TokenKind.IF || this.currentToken.getKind() == TokenKind.BREAK ||
						this.currentToken.getKind() == TokenKind.WRITEF || this.currentToken.getKind() == TokenKind.CONTINUE || this.currentToken.getKind() == TokenKind.IDENTIFIER || this.currentToken.getKind() == TokenKind.RESULTIS){
					hasCommand = true;
					command.add(parseCommand());
				}
				
				accept(TokenKind.RCURL);
				funcAST = new FunctionDefinition(identifier, parametersPrototype, variableDefinition , command);
				
			break;
			
			default:
				throw new SyntacticException("ERROR!", currentToken);
		}
		
		return funcAST;
	}
	
	private ProcedureDefinition parseProcedureDefinition() throws SyntacticException{
		ProcedureDefinition procedureDefinitionAST;
		Tipo tipo;
		Identifier identifier;
		ParametersPrototype params = null;
		ArrayList<VariableDefinition> variableDefinition = new ArrayList<VariableDefinition>();
		ArrayList<Command> command = new ArrayList<Command>();
		
		boolean hasParams, hasVar, hasCommand;
		hasParams = hasVar = hasCommand = false;
		
		tipo = new Tipo(this.currentToken.getKind().toString(), this.currentToken.getSpelling());
		accept(TokenKind.VOID);
		identifier = new Identifier(this.currentToken.getSpelling());
		accept(TokenKind.IDENTIFIER);
		accept(TokenKind.LPAR);
		if(this.currentToken.getKind() == TokenKind.INT ||
				this.currentToken.getKind() == TokenKind.BOOL	){
			hasParams = true;
			params = parseParametersPrototype();
		}
		accept(TokenKind.RPAR);
		accept(TokenKind.BE);
		accept(TokenKind.LCURL);
		while(this.currentToken.getKind() == TokenKind.LET){
			variableDefinition.add(parseVariableDefinition());
			hasVar = true;
		}
		while(this.currentToken.getKind() == TokenKind.WHILE || this.currentToken.getKind() == TokenKind.IF || this.currentToken.getKind() == TokenKind.BREAK ||
				this.currentToken.getKind() == TokenKind.WRITEF || this.currentToken.getKind() == TokenKind.CONTINUE || this.currentToken.getKind() == TokenKind.IDENTIFIER || this.currentToken.getKind() == TokenKind.RESULTIS){
			hasCommand = true;
			command.add(parseCommand());
		}
		accept(TokenKind.RCURL);
		
		if(hasParams){
			if(hasVar){
				if(hasCommand){
					procedureDefinitionAST = new ProcedureDefinition(identifier, variableDefinition, command, params);
				}
				else{
					procedureDefinitionAST = new ProcedureDefinition(identifier, variableDefinition, null, params);
				}
			}
			else{
				if(hasCommand){
					procedureDefinitionAST = new ProcedureDefinition(identifier, null, command, params);
				}
				else{
					procedureDefinitionAST = new ProcedureDefinition(identifier, null, null, params);
				}
			}
		}
		else{
			if(hasVar){
				if(hasCommand){
					procedureDefinitionAST = new ProcedureDefinition(identifier, variableDefinition, command, null);
				}
				else{
					procedureDefinitionAST = new ProcedureDefinition(identifier, variableDefinition, null, null);
				}
			}else{
				if(hasCommand){
					procedureDefinitionAST = new ProcedureDefinition(identifier, null, command, null);
				}
				else{
					procedureDefinitionAST = new ProcedureDefinition(identifier, null, null, null);
				}
			}
		}
		
		return procedureDefinitionAST;

	}
	
	private ParametersPrototype parseParametersPrototype() throws SyntacticException{
		ParametersPrototype parametersPrototype = null;
		ArrayList<Identifier> identifier = new ArrayList<Identifier>();
		ArrayList<Operator> virg = new ArrayList<Operator>();
		ArrayList<Tipo> tipo = new ArrayList<Tipo>();
		
		
		switch(this.currentToken.getKind()){
			case INT:
			case BOOL:
				tipo.add(new Tipo(this.currentToken.getKind().toString(), this.currentToken.getSpelling()));
				acceptIt();
				identifier.add(new Identifier(this.currentToken.getSpelling()));
				accept(TokenKind.IDENTIFIER);
				while(this.currentToken.getKind() == TokenKind.VIRG){
					virg.add(new Operator(this.currentToken.getSpelling()));
					acceptIt();
					if(this.currentToken.getKind() == TokenKind.INT ||
							this.currentToken.getKind() == TokenKind.BOOL){
						tipo.add(new Tipo(this.currentToken.getKind().toString(), this.currentToken.getSpelling()));
						acceptIt();
						identifier.add(new Identifier(this.currentToken.getSpelling()));
						accept(TokenKind.IDENTIFIER);

					}else{
						SyntacticException e = new SyntacticException("ERROR", currentToken);
						System.out.println(e.toString());
						throw e;
					}
				}
				
				break;
		default:
			SyntacticException e = new SyntacticException("ERROR", currentToken);
			System.out.println(e.toString());
			throw e;
			
		}
		
		parametersPrototype = new ParametersPrototype(tipo, identifier, virg);
		return parametersPrototype;
		
	}
	
	private Command parseCommand() throws SyntacticException{
		Command cmd = null;
		
		switch(this.currentToken.getKind()){
		
			case CALL:
				cmd = parseCallCommand();
				break;
			case IDENTIFIER:
				//System.out.println(this.currentToken.getKind());
				cmd = parseAssignmentCommand();
				break;
			case WHILE:
				cmd = parseWhileCommand();
				break;
				
			case IF:
				cmd = parseIfCommand();
				break;
			case BREAK:
				cmd = parseBreakCommand();
				break;
			case CONTINUE:
				cmd = parseContinueCommand();
				break;
			case WRITEF:
				cmd = parsePrintCommand();
				break;
			case RESULTIS:
				cmd = parseResultIsCommand();				
				break;
			default:
				SyntacticException e = new SyntacticException(null, currentToken);
				System.out.println(e.toString());
				throw e;
		}
		return cmd;
		
	}
	
	private AssignmentCommand parseAssignmentCommand() throws SyntacticException{
		AssignmentCommand assignmentCommandAST = null;
		Identifier identifier = null;
		Expression expression = null;
		CallCommand callCommand = null;
		boolean hasCallCommand = false;
		
		identifier = new Identifier(this.currentToken.getSpelling());
		accept(TokenKind.IDENTIFIER);
		accept(TokenKind.OP_ATTR);
		if(this.currentToken.getKind() == TokenKind.CALL){
			callCommand = parseCallCommand();
			hasCallCommand = true;
		}
		else{
			expression = parseExpression();
		}
		if(this.currentToken.getKind() == TokenKind.SEMICOL)
			accept(TokenKind.SEMICOL);
		
		if(hasCallCommand){
			assignmentCommandAST = new AssignmentCommand(identifier, callCommand);
		}else{
			assignmentCommandAST = new AssignmentCommand(identifier, expression);
		}
		return assignmentCommandAST;
	}
	
	private ResultIsCommand parseResultIsCommand() throws SyntacticException{
		ResultIsCommand resultIsCommandAST;
		Expression expression;
		accept(TokenKind.RESULTIS);
		expression = parseExpression();
		if(this.currentToken.getKind() == TokenKind.SEMICOL)
			accept(TokenKind.SEMICOL);
		resultIsCommandAST = new ResultIsCommand(expression);
		return resultIsCommandAST;
	}
	
	private WhileCommand parseWhileCommand() throws SyntacticException{
		WhileCommand whileCommand ; 
		Expression expression;
		ArrayList<Command> command = new ArrayList<Command>();
		boolean hasCommand = false;
		
		accept(TokenKind.WHILE);
		accept(TokenKind.LPAR);
		expression = parseExpression();
		accept(TokenKind.RPAR);
		accept(TokenKind.LCURL);
		while(this.currentToken.getKind() == TokenKind.IF || this.currentToken.getKind() == TokenKind.WHILE ||
				this.currentToken.getKind() == TokenKind.WRITEF || this.currentToken.getKind() == TokenKind.IDENTIFIER ||
				this.currentToken.getKind() == TokenKind.CALL  || this.currentToken.getKind() == TokenKind.BREAK ||
				this.currentToken.getKind() == TokenKind.CONTINUE) {
			command.add(parseCommand());
			hasCommand = true;
		}
		accept(TokenKind.RCURL);
		
		if(hasCommand){
			whileCommand = new WhileCommand(expression, command);
		}else{
			whileCommand = new WhileCommand(expression);
		}
		
		return whileCommand;
			
		
	}
		
	private BreakCommand parseBreakCommand() throws SyntacticException{
		BreakCommand breakCommandAST; 
		breakCommandAST = new BreakCommand(new ReservedWord(currentToken.getSpelling()));
		accept(TokenKind.BREAK);
		accept(TokenKind.SEMICOL);
		
		return breakCommandAST;
		
	}
	
	private ContinueCommand parseContinueCommand() throws SyntacticException{
		ContinueCommand continueCommandAST; 
		continueCommandAST = new ContinueCommand(new ReservedWord(this.currentToken.getSpelling()));
		accept(TokenKind.CONTINUE);
		accept(TokenKind.SEMICOL);
		
		return continueCommandAST;
		
	}
	
	private PrintCommand parsePrintCommand() throws SyntacticException{
		
		PrintCommand printCommandAST; 
		Expression expression;
		
		accept(TokenKind.WRITEF);
		accept(TokenKind.QUOTE);
		expression = parseExpression();
		accept(TokenKind.QUOTE);
		accept(TokenKind.SEMICOL);
		
		printCommandAST = new PrintCommand(expression);
		
		return printCommandAST;
		
	}
	
	private CallCommand parseCallCommand() throws SyntacticException{
		CallCommand callCommandAST = null;
		Identifier identifier = null; 
		ParametersCallCommand params = null ;
		boolean hasParams = false;
		
		accept(TokenKind.CALL);
		identifier = new Identifier(this.currentToken.getSpelling());
		accept(TokenKind.IDENTIFIER);
		accept(TokenKind.LPAR);		
		while(this.currentToken.getKind() == TokenKind.IDENTIFIER || this.currentToken.getKind() == TokenKind.NUMBER){
			params = (parseParametersCallCommand());
			hasParams = true;
		}
		accept(TokenKind.RPAR);
		accept(TokenKind.SEMICOL);
		if(hasParams){
			callCommandAST = new CallCommand(identifier, params);

		}else{
			callCommandAST = new CallCommand(identifier);			
		}
		return callCommandAST;
	}
	
	private ParametersCallCommand parseParametersCallCommand() throws SyntacticException{
		ParametersCallCommand params ;
		ArrayList<Factor> identifier = new ArrayList<Factor>();
		ArrayList<Operator> virg = new ArrayList<Operator>();
		if(this.currentToken.getKind() == TokenKind.IDENTIFIER){
			//identifier.add(new Identifier(this.currentToken.getSpelling()));
			//accept(TokenKind.IDENTIFIER);
			identifier.add(parseExpression());
		}
		else if(this.currentToken.getKind() == TokenKind.NUMBER){
			identifier.add(parseExpression());
			//accept(TokenKind.NUMBER);
		}
		else if(this.currentToken.getKind() == TokenKind.TRUE || this.currentToken.getKind() == TokenKind.FALSE){
			identifier.add(parseExpression());
		}
		while(this.currentToken.getKind() == TokenKind.VIRG){
			virg.add(new Operator(this.currentToken.getSpelling()));
			accept(TokenKind.VIRG);
			identifier.add(parseExpression());
			//acceptIt();
			
			/*
			if(this.currentToken.getKind() == TokenKind.IDENTIFIER){
				//identifier.add(new Identifier(this.currentToken.getSpelling()));
				;
				accept(TokenKind.IDENTIFIER);
			}else if(this.currentToken.getKind() == TokenKind.NUMBER){
				identifier.add(new Number(this.currentToken.getSpelling()));
				accept(TokenKind.NUMBER);
			}else if(this.currentToken.getKind() == TokenKind.TRUE || 
					this.currentToken.getKind() == TokenKind.FALSE ){
				identifier.add(new Bool(this.currentToken.getSpelling()));
				acceptIt();
			}
			*/
			
		}
		
		params = new ParametersCallCommand(identifier, virg);
		return params;

	}
	
	private IfCommand parseIfCommand() throws SyntacticException{
		IfCommand ifCommandAST;
		ArrayList<Command> command = new ArrayList<Command>();
		Expression expression;
		ElseCommand elseCommand = null;
		ArrayList<Command> commandElse = new ArrayList<Command>();
		boolean hasElse = false;
		boolean hasCommandElse = false;
		boolean hasCommand = false;
		
		accept(TokenKind.IF);
		accept(TokenKind.LPAR);
		expression = parseExpression();
		accept(TokenKind.RPAR);
		accept(TokenKind.LCURL);
		while(this.currentToken.getKind() == TokenKind.IF || this.currentToken.getKind() == TokenKind.WHILE ||
				this.currentToken.getKind() == TokenKind.WRITEF || this.currentToken.getKind() == TokenKind.IDENTIFIER || 
				this.currentToken.getKind() == TokenKind.CALL || this.currentToken.getKind() == TokenKind.BREAK ||
				this.currentToken.getKind() == TokenKind.CONTINUE){
			command.add(parseCommand());
			hasCommand = true;
		}
		accept(TokenKind.RCURL);
		if(this.currentToken.getKind() == TokenKind.ELSE){
			hasElse = true;
			accept(TokenKind.ELSE);
			accept(TokenKind.LCURL);
			while(this.currentToken.getKind() == TokenKind.IF || this.currentToken.getKind() == TokenKind.WHILE ||
					this.currentToken.getKind() == TokenKind.WRITEF || this.currentToken.getKind() == TokenKind.IDENTIFIER || 
					this.currentToken.getKind() == TokenKind.CALL || this.currentToken.getKind() == TokenKind.BREAK ||
					this.currentToken.getKind() == TokenKind.CONTINUE || this.currentToken.getKind() == TokenKind.RESULTIS){
				commandElse.add(parseCommand());
				hasCommandElse = true;
			}
			accept(TokenKind.RCURL);
		}
		
		if(hasCommand){
			if(hasElse){
				if(hasCommandElse){
					elseCommand = new ElseCommand(commandElse); 
					ifCommandAST = new IfCommand(expression, command, elseCommand);
				}
				else{
					
					ifCommandAST = new IfCommand(expression, command, null);
				}
			}
			else{
				ifCommandAST = new IfCommand(expression, command);
			}
		}
		else{
			if(hasElse){
				if(hasCommandElse){
					elseCommand = new ElseCommand(commandElse);
					ifCommandAST = new IfCommand(expression, elseCommand);
				}
				else{
					elseCommand = new ElseCommand(null);
					ifCommandAST = new IfCommand(expression, null, elseCommand);
				}
			}
			else{
				ifCommandAST = new IfCommand(expression);
			}
		}
		
		return ifCommandAST;
		
		
	}
	
	//expression               ::=  expArit (Op_Rel expArit)?
	private Expression parseExpression() throws SyntacticException{
		
		ExpressionArithmetic left;
		Operator operador = null;
		ExpressionArithmetic right = null;
		Expression expressionAST;
		left = parseExpArit();
		
		switch(this.currentToken.getKind()){
			case EQUALLOGICAL:
			case LT:
			case LTE:
			case GT:
			case GTE:
			case NOTEQUALLOGICAL:
			case OP_RELATION:
				operador = new Operator(this.currentToken.getSpelling());
				acceptIt();
				right = parseExpArit();
				
			break;
			
			default:
		}
		if(this.currentToken.getKind() == TokenKind.SEMICOL)
			accept(TokenKind.SEMICOL);
		expressionAST = new Expression(left, operador, right);
		
		return expressionAST;
	}
	
	private ExpressionArithmetic parseExpArit () throws SyntacticException{
		ExpressionArithmetic expressionArithmeticAST;
		ExpressionMultiplication expressionMultiplicationLeft;
		ArrayList<Operator> operadores = new ArrayList<Operator>() ;
		ArrayList<ExpressionMultiplication> expressionMultiplicationOthers = new ArrayList<ExpressionMultiplication>();
		boolean hasMore = false;
		
		expressionMultiplicationLeft = parseExpMult();
		
		while(this.currentToken.getKind() == TokenKind.PLUSSIGN ||
				this.currentToken.getKind() == TokenKind.MINUSSIGN){
			operadores.add(new Operator(this.currentToken.getSpelling()));
			acceptIt();
			expressionMultiplicationOthers.add(parseExpMult());
		}
		
		expressionArithmeticAST = new ExpressionArithmetic(expressionMultiplicationLeft, operadores, expressionMultiplicationOthers);
		
		return expressionArithmeticAST;
		
		
/*		
		expressionMultiplicationLeft = parseExpMult();
		expressionMultiplicationOthers.add(parseExpMult());
		expressionArithmeticAST = new ExpressionArithmetic(expressionMultiplicationLeft, operadores, expressionMultiplicationOthers);
 * 
 * while(this.currentToken.getKind() == TokenKind.PLUSSIGN ||
				this.currentToken.getKind() == TokenKind.MINUSSIGN){
			operadores.add(new Operator(this.currentToken.getKind().toString(), this.currentToken.getSpelling()));
			acceptIt();
			hasMore = true;
		}
		if(hasMore == true){
		}else{
			expressionArithmeticAST = new ExpressionArithmetic(expressionMultiplicationLeft);
		}*/
		
		//return expressionArithmeticAST;
	}
	
	private ExpressionMultiplication parseExpMult() throws SyntacticException{
		Factor left = null;
		ArrayList<Factor> factor = new ArrayList<Factor>();
		ArrayList<ExpressionMultiplication> expMult;
		ArrayList<Operator> operator = new ArrayList<Operator>(); 
		left = parseFactor();
		
		while(this.currentToken.getKind() == TokenKind.DIVSSIGN ||
				this.currentToken.getKind() == TokenKind.MULTSIGN)	{
			operator.add(new Operator(this.currentToken.getSpelling()));
			acceptIt();
			factor.add(parseFactor());
			
		
			
		}
		
		ExpressionMultiplication exp = new ExpressionMultiplication(left, operator, factor);
		return exp;
		
		
		
		/*
		ExpressionMultiplication expressionMultiplicationAST;
		Factor factorLeft;
		ArrayList<Operator> operadores = new ArrayList<Operator>();
		ArrayList<Factor> factorOthers = new ArrayList<Factor>();
		factorLeft = parseFator();
		boolean hasMore = false;
			operadores.add(new Operator(this.currentToken.getKind().toString(), this.currentToken.getSpelling()));
			acceptIt();
			factorOthers.add(parseFator());
			hasMore = true;
		}
		
		if(hasMore == true){
			expressionMultiplicationAST = new ExpressionMultiplication(factorLeft, operadores, factorOthers); 
		}else{
			expressionMultiplicationAST = new ExpressionMultiplication(factorLeft);
		}
		
		return expressionMultiplicationAST;
		*/
	}
	
	/*
	 * Fator             ::=     Identifier (e-vazio | '(' parametersCallCommand? ')')
                         |       Number
                         |       Boolean
                         |       '(' expression ')'

	 * 
	 * */
	
	private Factor parseFactor() throws SyntacticException{
		Factor factorAST = null;
		
		switch(this.currentToken.getKind()){
			case IDENTIFIER: 
				//factorAST.setIdentifier(new Identifier(this.currentToken.getKind().toString(), this.currentToken.getSpelling()));
				factorAST = new Identifier(this.currentToken.getSpelling());
				accept(TokenKind.IDENTIFIER);
				
			break;
			
			case NUMBER:
				//factorAST.setNumber(new Number(this.currentToken.getKind().toString(), this.currentToken.getSpelling()));
				factorAST = new Number(this.currentToken.getSpelling());
				accept(TokenKind.NUMBER);
			break;
			
			case TRUE:
			case FALSE:
				//factorAST.setBool(new Bool(this.currentToken.getKind().toString(), this.currentToken.getSpelling()));
				factorAST = new Bool(this.currentToken.getSpelling());
				acceptIt();
			break;
			
			case CALL:
				AssignmentCallCommand ass = new AssignmentCallCommand(parseCallCommand());
				return ass;	
			case LPAR:
				acceptIt();
				factorAST = (parseExpression());
				accept(TokenKind.RPAR);
			break;
			
			default:
				factorAST = null;
			break;
		}
		
		return factorAST;
		
		/*
		
		CallCommand callCommand;
		
		switch(this.currentToken.getKind()){
			
			case IDENTIFIER:
				Identifier identifier = new Identifier(this.currentToken.getKind().toString(), this.currentToken.getSpelling());
				accept(TokenKind.IDENTIFIER);
				factorAST = new Factor(null, null, null, identifier);

			break;
		
			case NUMBER:
				Number number = new Number(this.currentToken.getKind().toString(), this.currentToken.getSpelling());
				accept(TokenKind.NUMBER);
				factorAST = new Factor(null, null, null, number);
			break;
			
			case TRUE:
			case FALSE:
				Bool bool = new Bool(this.currentToken.getKind().toString(), this.currentToken.getSpelling());
				acceptIt();
				factorAST = new Factor(null, null, null, bool);
			break;
						
			case LPAR:
				accept(TokenKind.LPAR);
				Expression expression = parseExpression();
				accept(TokenKind.RPAR);
				factorAST = new Factor(null, null, null, expression);
			break;
			
			case CALL:
				//LET INT X = CALL m(y, z);
				callCommand = parseCallCommand();
				factorAST = new Factor(null, null, null, callCommand);
				
			default:
	
				break;
				
			
		}
		return factorAST;
		 */
	}
		
}
	
