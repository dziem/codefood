package com.akmal.codefood.exception;

import com.akmal.codefood.api.CommonRs;
import com.akmal.codefood.util.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(RestExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CommonRs> handleException(Exception ex, WebRequest request) {
        System.out.println(ex.getClass().getCanonicalName());
        if (ex instanceof ResourceNotFoundException || ex instanceof NoSuchElementException)
            return new ResponseEntity<CommonRs>(new CommonRs(false, ex.getMessage()),
                    HttpStatus.NOT_FOUND);
        else if (ex instanceof BadRequestException)
            return new ResponseEntity<CommonRs>(new CommonRs(false, ex.getMessage()),
                    ((BadRequestException) ex).getHttpStatus());
        else if (ex instanceof UnauthorizedException)
            return new ResponseEntity<CommonRs>(new CommonRs(false, ex.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        else if (ex instanceof FormException) {
            FormException err = (FormException) ex;
            return new ResponseEntity<CommonRs>(new CommonRs(false, err.getMessage(), err.getErrors()),
                    HttpStatus.BAD_REQUEST);
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException err = (MethodArgumentNotValidException) ex;
            List<String> errors = new ArrayList<>();
            err.getAllErrors().forEach((error) -> {
                errors.add(error.getDefaultMessage());
            });
            return new ResponseEntity<CommonRs>(new CommonRs(false, errors.get(0)),
                    HttpStatus.BAD_REQUEST);
        } else {
            LOGGER.severe(ErrorUtil.getExceptionStacktrace(ex));

            return new ResponseEntity<CommonRs>(new CommonRs(false, ex.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
