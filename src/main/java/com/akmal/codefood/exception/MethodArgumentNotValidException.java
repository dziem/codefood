package com.akmal.codefood.exception;

import java.util.ArrayList;
import java.util.List;

public class MethodArgumentNotValidException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2692722436255640434L;

    private List<String> errors;

    public static MethodArgumentNotValidException create(List<String> errors) {
        return new MethodArgumentNotValidException(errors);
    }

    public MethodArgumentNotValidException(List<String> errors) {
        super();
        this.errors = errors;
    }

    public List<String> getErrors(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getErrors(ex).forEach((error) -> {
            errors.add(error);
        });
        return errors;
    }

}
