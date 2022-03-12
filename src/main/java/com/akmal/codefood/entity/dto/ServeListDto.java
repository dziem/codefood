package com.akmal.codefood.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServeListDto {
    private Integer total;
    private List<ServeDto> history;
}
