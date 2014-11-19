package parser;

/**
 * This class contains codes for each grammar terminal
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */

/*
program ::= declarationList;

declarationList ::= 'LET' definition ('AND' definition)*;

definition ::= callableDefinition
		| variableDefinition;

identifierList ::= Identifier (',' Identifier)*;

callableDefinition ::= Identifier '(' parametersPrototype ')' (functionDefinitionTail | procedureDefinitionTail);

functionDefinitionTail ::= '=' 'VALOF' functionBlock;

procedureDefinitionTail ::= 'BE' commandBlock;

variableDefinition ::= ('GLOBAL')? (intVariableDefinition | boolVariableDefinition);

parametersPrototype ::= (('INT' | 'BOOL')Identifier ((, 'INT' | 'BOOL')Identifier)*)*

functionBlock ::= '{' commandList resultisCommand '}';

intVariableDefinition ::= ('INT' Identifier '=' numberExpression)*;

boolVariableDefinition ::= ('BOOL' Identifier '=' booleanExpression)*;

commandBlock ::= '{' commandList '}';

parametersCallCommand ::= (Identifier (, Identifier)*)*

commandList ::= command (';' command)*;

command ::= assignmentCommand
			| callCommand;
			| variableDecCommand
			| whileCommand
			| ifCommand
			| breakCommand
			| continueCommand
			| printCommand

variableDecCommand ::= intVariableDefinition
					| boolVariableDefinition

callCommand ::= Identifier '('(parametersCallCommand)*')'

assignmentCommand ::= Identifier ':=' (number | booleanValue | numberExpression | functionCallCommand | Identifier);

ifCommand ::= 'IF' '(' booleanExpression | booleanValue ')' commandBlock ('ELSE' commandBlock)?;

whileCommand ::= 'WHILE' '(' ( booleanExpression | booleanValue ')' commandBlock;

resultisCommand ::= 'RESULTIS' expression;

breakCommand ::= 'BREAK';

continueCommand ::= 'CONTINUE';

printCommand ::= 'WRITEF' '(' (Identifier)* ')';

numberBoolExpression ::= (number | Identifier)Op_relation ( number | Identifier)

arithBoolExpression ::= arithmeticExpression | numberBoolExpression

booleanExpression ::= ('!')? ( andExpression | orExpression | equalExpresion | notEqualExpression )

numberExpression ::= (multDivExpression)? arithmeticExpression
arithmeticExpression ::= multDivExpression
						| addSubExpression

multDivExpression ::= (Identifier | number) ('/' | '*') (Identifier | number)

addSubExpression ::= (Identifier | number) ('+' | '-') (Identifier | number)

andExpression ::= ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue ) '&&' ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue)

orExpression ::= ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) '||' ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )

equalExpression ::= (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) '==' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )

notEqualExpression ::= (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) '!=' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )

booleanValue ::= 'TRUE'
				| 'FALSE'
*/

public class GrammarSymbols {

	//TODO
	public static final int EOT = 1000;

	// Language terminals (starts from 0)

	public static final int ID = 0;

}