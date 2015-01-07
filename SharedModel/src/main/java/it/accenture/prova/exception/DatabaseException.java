package it.accenture.prova.exception;

public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 4808787327166467752L;

	public final static String RECORD_GIA_PRESENTE = "RECORD_GIA_PRESENTE";
	
	public DatabaseException() {
		super();
	}

	public DatabaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DatabaseException(String arg0) {
		super(arg0);
	}

	public DatabaseException(Throwable arg0) {
		super(arg0);
	}
	
	

}
