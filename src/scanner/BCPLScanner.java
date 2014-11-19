
package scanner;

import util.Arquivo;
public class BCPLScanner extends Scanner {

	public BCPLScanner() {
		super();
	}

	public BCPLScanner(String inputFile) {
		this.file = new Arquivo(inputFile);
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}

	public String getCurrentSpelling() {
		return this.currentSpelling.toString();
	}

	@Override
	public Token getNextToken() throws LexicalException {
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
			token = new Token(currentKind, getCurrentSpelling(), line, column);
		}

		return token;
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

	@Override
	protected void scanSeparator() {
		switch (currentChar) {
			case ' ':
			case '\r':
			case '\t':
			case '\n':
				getNextChar();

				break;
		}
	}

	@Override
	protected TokenKind scanToken() throws LexicalException {
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
					getCurrentSpelling());

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
			case '>':
			case '!':
				getNextChar();

				if (currentChar == '=') {
					getNextChar();

					return TokenKind.OP_RELATION;
				}
				else {
					return TokenKind.OP_RELATION;
				}

			case '=':
				getNextChar();

				if (currentChar == '=') {
					getNextChar();

					return TokenKind.OP_RELATION;
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
	}

}