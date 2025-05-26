package com.mx.email.exception;

public class TemplateException extends Exception {

    private static final long serialVersionUID = 8278559735375601111L;

    public TemplateException() {
        super();
    }

    public TemplateException(String message) {
        super(message);
    }

    public TemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateException(Throwable cause) {
        super(cause);
    }
}
