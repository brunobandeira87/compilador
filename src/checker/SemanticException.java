
package checker;

/**
 * Semantic Exception
 *
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class SemanticException extends Exception {

	/**
	 * Default constructor
	 *
	 * @param message
	 */
	public SemanticException(String message) {
		//super(message);
		this.message = message;

		
		//System.out.println(this.toString());
	}

	/**
	 * Creates the error report
	 */
	public String toString() {
		String errorMessage =
			"---- SEMANTIC ERROR REPORT - BEGIN ----\n" + ">> Message: " +
				this.getMessage() + "\n" +
				"----- SEMANTIC ERROR REPORT - END -----\n";

		return errorMessage;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

	private static final long serialVersionUID = 3457448332803077642L;
	private String message;

}