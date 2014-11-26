
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

		while (isSeparator(currentChar)) {
			scanSeparator();
		}

		currentSpelling = new StringBuffer("");

		currentKind = scanToken();

		Token token;

		if (currentKind == TokenKind.COMMENT) {
			token = getNextToken();
		}
		else {
			token = new Token(currentKind, this.currentSpelling.toString(), line, column);
		}

		return token;
	}

	/**
	 * Gets the next char
	 */
	protected void getNextChar() {

		// Appends the current char to the string buffer
		if(!this.isSeparator(currentChar)){
			this.currentSpelling.append(this.currentChar);
		}
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
		
		switch (currentChar) {
			case ' ':
			case '\r':
			case '\t':
			case '\n':
				getNextChar();

			break;
			
			default:
				System.out.println("LOL");
		}

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
		
		switch (currentChar) {
		case 'a':
		case 'A':
		case 'b':
		case 'B':
		case 'c':
		case 'C':
		case 'd':
		case 'D':
		case 'e':
		case 'E':
		case 'f':
		case 'F':
		case 'g':
		case 'G':
		case 'h':
		case 'H':
		case 'i':
		case 'I':
		case 'j':
		case 'J':
		case 'k':
		case 'K':
		case 'l':
		case 'L':
		case 'm':
		case 'M':
		case 'n':
		case 'N':
		case 'o':
		case 'O':
		case 'p':
		case 'P':
		case 'q':
		case 'Q':
		case 'r':
		case 'R':
		case 's':
		case 'S':
		case 't':
		case 'T':
		case 'u':
		case 'U':
		case 'v':
		case 'V':
		case 'w':
		case 'W':
		case 'x':
		case 'X':
		case 'y':
		case 'Y':
		case 'z':
		case 'Z':
			getNextChar();

			while (isLetter(currentChar) || isDigit(currentChar)) {
				getNextChar();
			}

			TokenKind reservedWord = lookupReservedWord(
				this.currentSpelling.toString());

			if (reservedWord != null) {
				return reservedWord;
			}

			return TokenKind.IDENTIFIER;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			getNextChar();

			while (isDigit(currentChar)) {
				getNextChar();
			}

			return TokenKind.NUMBER;
		case '+':
			getNextChar();
			return TokenKind.PLUSSIGN;
		case '-':
			getNextChar();
			return TokenKind.MINUSSIGN;
		case '*':
			getNextChar();
			return TokenKind.MULTSIGN;
		case '/':
			getNextChar();

			if (currentChar == '/') {
				do {
					getNextChar();
				}
				while (currentChar != '\n');

				getNextChar();

				return TokenKind.COMMENT;
			}
			else {
				return TokenKind.DIVSSIGN;
			}

		case ':':
			getNextChar();

			return TokenKind.OP_ATTR;
		case '<':
			getNextChar();
			if (currentChar == '=') {
				getNextChar();

				return TokenKind.LTE;
			}
			else {
				return TokenKind.LT;
			}

		case '>':
			getNextChar();
			if (currentChar == '=') {
				getNextChar();

				return TokenKind.GTE;
			}
			else {
				return TokenKind.GT;
			}

		case '!':
			getNextChar();

			if (currentChar == '=') {
				getNextChar();

				return TokenKind.NOTEQUAL;
			}
			else {
				return TokenKind.NOT;
			}

		case '=':
			getNextChar();

			if (currentChar == '=') {
				getNextChar();

				return TokenKind.EQUAL;
			}
			else {
				return TokenKind.OP_ATTR;
			}

		case '(':
			getNextChar();

			return TokenKind.LPAR;
		case ')':
			getNextChar();

			return TokenKind.RPAR;
		case '{':
			getNextChar();

			return TokenKind.LCURL;
		case '}':
			getNextChar();

			return TokenKind.RCURL;
		case ';':
			getNextChar();

			return TokenKind.SEMICOL;
		case ',':
			getNextChar();

			return TokenKind.VIRG;
			
		case '"':
			getNextChar();
			
			return TokenKind.QUOTE;
		case 0:
			return TokenKind.EOF;
		default:
			throw new LexicalException(
				"Unexpected character.", currentChar, line, column);
	}
		
		
		

		//return TokenKind.UNKNOWN;
	}
	
	
	public TokenKind lookupReservedWord(String word) {
		if (word.equals(ReservedWords.AND)) {
			return TokenKind.AND;
		}
		else if (word.equals(ReservedWords.BOOL)) {
			return TokenKind.BOOL;
		}
		else if (word.equals(ReservedWords.BREAK)) {
			return TokenKind.BREAK;
		}
		else if (word.equals(ReservedWords.CONTINUE)) {
			return TokenKind.CONTINUE;
		}
		else if (word.equals(ReservedWords.ELSE)) {
			return TokenKind.ELSE;
		}
		else if (word.equals(ReservedWords.FALSE)) {
			return TokenKind.FALSE;
		}
		else if (word.equals(ReservedWords.GLOBAL)) {
			return TokenKind.GLOBAL;
		}
		else if (word.equals(ReservedWords.IF)) {
			return TokenKind.IF;
		}
		else if (word.equals(ReservedWords.INT)) {
			return TokenKind.INT;
		}
		else if (word.equals(ReservedWords.LET)) {
			return TokenKind.LET;
		}
		else if (word.equals(ReservedWords.RESULTIS)) {
			return TokenKind.RESULTIS;
		}
		else if (word.equals(ReservedWords.TRUE)) {
			return TokenKind.TRUE;
		}
		else if (word.equals(ReservedWords.WHILE)) {
			return TokenKind.WHILE;
		}
		else if (word.equals(ReservedWords.WRITEF)) {
			return TokenKind.WRITEF;
		}
		else if (word.equals(ReservedWords.VALOF)) {
			return TokenKind.VALOF;
		}
		else if (word.equals(ReservedWords.CALL)) {
			return TokenKind.CALL;
		}
		else if(word.equals(ReservedWords.VOID)){
			return TokenKind.VOID;
		}
		else if(word.equals(ReservedWords.BE)){
			return TokenKind.BE;
		}

		return null;
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