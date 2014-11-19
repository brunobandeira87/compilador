
package scanner;

import compiler.Properties;

import util.Arquivo;

/**
 * Scanner class
 *
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Scanner {

	/**
	 * Default constructor
	 */
	public Scanner() {
		this.file = new Arquivo(Properties.sourceCodeLocation);
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}

	/**
	 * Returns the next token
	 *
	 * @return
	 */
	// TODO

	public Token getNextToken() throws LexicalException {

		// Initializes the string buffer
		// Ignores separators
		// Clears the string buffer
		// Scans the next token
		// Creates and returns a token for the lexema identified

		return null;
	}

	/**
	 * Gets the next char
	 */
	protected void getNextChar() {

		// Appends the current char to the string buffer

		this.currentSpelling.append(this.currentChar);

		// Reads the next one

		this.currentChar = this.file.readChar();

		// Increments the line and column

		this.incrementLineColumn();
	}

	/**
	 * Increments line and column
	 */
	protected void incrementLineColumn() {

		// If the char read is a '\n', increments the line variable and assigns
		// 0 to the column

		if (this.currentChar == '\n') {
			this.line++;
			this.column = 0;

			// If the char read is not a '\n'

		}
		else {

			// If it is a '\t', increments the column by 4

			if (this.currentChar == '\t') {
				this.column = this.column + 4;

				// If it is not a '\t', increments the column by 1

			}
			else {
				this.column++;
			}
		}
	}

	/**
	 * Returns if a char is a digit (between 0 and 9)
	 *
	 * @param c
	 * @return
	 */
	protected boolean isDigit(char c) {
		if ((c >= '0') && (c <= '9')) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns if a char is a graphic (any ASCII visible character)
	 *
	 * @param c
	 * @return
	 */
	protected boolean isGraphic(char c) {
		if ((c >= ' ') && (c <= '~')) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns if a char is a letter (between a and z or between A and Z)
	 *
	 * @param c
	 * @return
	 */
	protected boolean isLetter(char c) {
		if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns if a character is a separator
	 *
	 * @param c
	 * @return
	 */
	protected boolean isSeparator(char c) {
		if ((c == '#') || (c == ' ') || (c == '\n') || (c == '\t')) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Reads (and ignores) a separator
	 *
	 * @throws LexicalException
	 */
	// TODO

	protected void scanSeparator() throws LexicalException {

		// If it is a comment line
		// Gets next char
		// Reads characters while they are graphics or '\t'
		// A command line should finish with a \n

	}

	/**
	 * Scans the next token Simulates the DFA that recognizes the language
	 * described by the lexical grammar
	 *
	 * @return
	 * @throws LexicalException
	 */
	// TODO

	protected TokenKind scanToken() throws LexicalException {

		// The initial automata state is 0
		// While loop to simulate the automata

		return TokenKind.UNKNOWN;
	}

	// Current line and column in the source file

	protected int line, column;

	// The last char read from the source code

	protected char currentChar;

	// The kind of the current token

	protected TokenKind currentKind;

	// Buffer to append characters read from file

	protected StringBuffer currentSpelling;

	// The file object that will be used to read the source code

	protected Arquivo file;

}