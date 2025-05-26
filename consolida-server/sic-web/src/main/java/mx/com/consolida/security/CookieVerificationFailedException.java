package mx.com.consolida.security;

public class CookieVerificationFailedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8757091171818794281L;

	public CookieVerificationFailedException(String message) {
		super(message);
	}
}
