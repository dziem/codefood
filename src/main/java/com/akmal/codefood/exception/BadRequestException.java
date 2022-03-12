package com.akmal.codefood.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -1660172094856444899L;

    private String msg;

    private HttpStatus httpStatus;

    public static BadRequestException create(String msg) {
        return new BadRequestException(msg);
    }

    public BadRequestException(String msg) {
        super();
        this.msg = msg;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BadRequestException(String msg, HttpStatus httpStatus) {
        super();
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
