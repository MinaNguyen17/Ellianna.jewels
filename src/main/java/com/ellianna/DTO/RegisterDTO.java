package com.ellianna.DTO;

import com.ellianna.model.USER_ROLE;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO{
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank(message = "Must not empty")
    private String fullName;
    @NotBlank
    private USER_ROLE role;
}