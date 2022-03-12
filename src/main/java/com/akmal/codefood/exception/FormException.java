package com.akmal.codefood.exception;

import java.util.Map;

public class FormException extends RuntimeException {
    private Map<String, String> errors;

    public FormException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
