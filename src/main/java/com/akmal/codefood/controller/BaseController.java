package com.akmal.codefood.controller;

import com.akmal.codefood.api.CommonRs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

public class BaseController {
    public ResponseEntity<Object> ok(Object data) {
        return new ResponseEntity<>(new CommonRs(true, "success", data), HttpStatus.OK);
    }

    public ResponseEntity<Object> ok() {
        return new ResponseEntity<>(new CommonRs(true, "success"), HttpStatus.OK);
    }

    public ResponseEntity<Object> error(HttpStatus httpStatus, Object message) {
        return new ResponseEntity<>(new CommonRs(false, "error", message), httpStatus);
    }

    public Sort.Order getSortBy(String sort, Boolean asc) {
        return Boolean.TRUE.equals(asc) ?
                Sort.Order.asc(sort) :
                Sort.Order.desc(sort);
    }
}
