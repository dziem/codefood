package com.akmal.codefood.api.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServeSearchForm {
    private String q;
    private Long categoryId;
    private String username;
    private String status;
    private Boolean isFinished;
    private Boolean isReviewed;
}
