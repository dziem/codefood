package com.akmal.codefood.exception;

public class ResourceNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5896860997454921991L;

    private String msg;

    public static ResourceNotFoundException create(String msg) {
        return new ResourceNotFoundException(msg);
    }

    public ResourceNotFoundException(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
