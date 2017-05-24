package fuzzysystem.exceptions;

public final class MembershipOutOfRangeException extends Exception {
	
	private static final long serialVersionUID = 698613791623169240L;
	
	
	
	public MembershipOutOfRangeException() {
		super("Membership value cannot be beyound [0,1].\n");
	}
	
	
	
	public MembershipOutOfRangeException(String message) {
		super(message);
	}
	
	
	
	public MembershipOutOfRangeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	public MembershipOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
	
	public MembershipOutOfRangeException(Throwable cause) {
		super(cause);
	}
}
