package com.ellianna.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
