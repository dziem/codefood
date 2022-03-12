package com.akmal.codefood.entity.dto;

import com.akmal.codefood.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseDto<E> implements DtoAble<E> {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private Date createdAt;
    private Date updatedAt;

    abstract E fromDto(E entity);

    @Override
    public void toDto(E e) {
        BaseEntity entity = (BaseEntity) e;
        this.setName(entity.getName());
        this.setId(entity.getId());
        this.setCreatedAt(entity.getCreatedAt());
        this.setUpdatedAt(entity.getUpdatedAt());
    }
}
