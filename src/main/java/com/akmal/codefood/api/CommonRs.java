package com.akmal.codefood.api;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CommonRs {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public CommonRs() {
        super();
    }

    public CommonRs(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public CommonRs(boolean success, String message, Object data) {
        super();
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
