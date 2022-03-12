package com.akmal.codefood.entity.dto;

public interface DtoAble<E> {
    E fromDto();
    void toDto(E entity);
}
