package fuzzysystem.exceptions;

public class MultipleMembershipException extends Throwable {
	
	private static final long serialVersionUID = 8770294911902182702L;
	
	
	
	public MultipleMembershipException() {
		super("Multiple membership values at a single point exist.", new InvalidShapeException());
	}
	
	
	
	public MultipleMembershipException(String message) {
		super(message);
	}
	
	
	
	public MultipleMembershipException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	public MultipleMembershipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
	
	public MultipleMembershipException(Throwable cause) {
		super(cause);
	}
	
}
