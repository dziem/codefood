package com.akmal.codefood.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    @NotBlank(message = "Username is mandatory")
    @Email(message = "Invalid email format")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
