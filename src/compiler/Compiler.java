
package compiler;

import checker.Checker;
import checker.SemanticException;
import encoder.Encoder;
import parser.Parser;
import parser.SyntacticException;
import util.AST.AST;
import util.symbolsTable.IdentificationTable;

/**
 * Compiler driver
 *
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Compiler {

	// Compiler identification table

	public static IdentificationTable identificationTable = null;

	/**
	 * Compiler start point
	 *
	 * @param args
	 *            - none
	 */
	public static void main(String[] args) throws SyntacticException {

		// Initializes the identification table with the reserved words

		Compiler.initIdentificationTable();

		// Creates the parser object

		
		
		
		Parser p = new Parser();
		System.out.println("1 - Lexical Step [OK]"); 
		// Creates the AST object

		AST astRoot = null;
	
		try {

			// Parses the source code

			astRoot = p.parse();
			System.out.println("2 - Syntatic Step [OK]");
			//System.out.println("\n-- AST STRUCTURE --");

			
		
			Checker checker = new Checker();
			try{
				AST astRoot2 = checker.check(astRoot);
				System.out.println("3 - Semantic Step [OK]");
				Encoder encoder = new Encoder();
				encoder.encode(astRoot2);
			} catch(SemanticException e){
				System.out.println(((SemanticException)e).getMessage().toString());
			}
		}
		catch (SyntacticException e) {

			// Shows the syntactic/lexical error stack trace

			e.printStackTrace();
		}
	
	
	}

	/**
	 * Initializes the identification table with the reserved words
	 */
	private static void initIdentificationTable() {

		// Calls the initializer methods

		Compiler.identificationTable = new IdentificationTable();
	}

}