package mx.com.consolida.converter;
/**
 * @author Abel
 */
public class TechnicalException extends Exception {
	
    private static final long serialVersionUID = -1425132300971609586L;

    public TechnicalException() {
        //DO NOTHING
    }

    public TechnicalException(final String message) {
        super(message);
    }

    public TechnicalException(final Throwable cause) {
        super(cause);
    }

    public TechnicalException(final String message, final Throwable cause) {
        super(message, cause);
    }
}