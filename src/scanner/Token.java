
package scanner;

/**
 * Token class
 *
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Token {

	/**
	 * Default constructor
	 *
	 * @param kind
	 * @param spelling
	 * @param line
	 * @param column
	 */
	public Token(TokenKind kind, String spelling, int line, int column) {

		this.kind = kind;
		this.spelling = spelling;
		this.line = line;
		this.column = column;
	}

	/**
	 * Returns the column where the token was found
	 *
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns token kind
	 *
	 * @return
	 */
	public TokenKind getKind() {
		return kind;
	}

	/**
	 * Returns the line where the token was found
	 *
	 * @return
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Returns token spelling
	 *
	 * @return
	 */
	public String getSpelling() {
		return spelling;
	}
	
	public String toString(){
		return spelling;
		
	}

	// The line and column that the token was found

	private int line, column;

	// The token kind

	private TokenKind kind;

	// The token spelling

	private String spelling;

}