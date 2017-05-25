package fuzzysystem.exceptions;

public class InvalidShapeException extends RuntimeException {
	
	private static final long serialVersionUID = -8117666073876794839L;
	
	
	
	public InvalidShapeException(String shapeRequired, String shapeFound) {
		this("Invalid shape for the given fuzzy set.\nShape required " + shapeRequired + ", shape found " + shapeFound + ".\n");
	}
	
	
	
	public InvalidShapeException() {
		super("Invalid shape for the given fuzzy set\n");
	}
	
	
	
	public InvalidShapeException(String message) {
		super(message);
	}
	
	
	
	public InvalidShapeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	public InvalidShapeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
	
	public InvalidShapeException(Throwable cause) {
		super(cause);
	}
	
}
