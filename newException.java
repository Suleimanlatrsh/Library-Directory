// -----------------------------------------------------
// Assignment (2)
// Question: (1)
// Written by: (Sohaib Daami 40260605)
// -----------------------------------------------------



/**
* The newException class contains all the new exception classes needed for the invalid movie records
* 
*
* @author Sohaib Daami
* @version 1.0
* @see String
*/
public class newException extends Exception {

	public newException(String message) {
		super(message);
	}
}


class BadYearException extends newException{
	public BadYearException() {
		// TODO Auto-generated constructor stub
		super("BadYearException Semantic error");
	}
}

class BadTitleException extends newException {
	public BadTitleException() {
		// TODO Auto-generated constructor stub
		super("BadTitleException Semantic error");
	}
}

class BadDurationException extends newException{

	public BadDurationException() {
		super("BadDurationException Semantic error");
	}
}

class BadGenreException extends newException{

	public BadGenreException() {
		super("BadGenreException Semantic error");
	}
}

class BadScoreException extends newException{

	public BadScoreException() {
		super("BadScoreException Semantic error");
	}
}

class BadRatingException extends newException{

	public BadRatingException() {
		super("BadRatingException Semantic error");
	}
}

class BadNameException extends newException{

	public BadNameException() {
		super("BadNameException Semantic error");
	}
}

class MissingQuotesException extends newException{

	public MissingQuotesException() {
		super("MissingQuotesException Syntax error");
	}
}

class ExcessFieldsException extends newException{

	public ExcessFieldsException() {
		super("ExcessFieldsException Syntax error");
	}
}

class MissingFieldsException extends newException{

	public MissingFieldsException() {
		super("MissingFieldsException Syntax error");
	}
}