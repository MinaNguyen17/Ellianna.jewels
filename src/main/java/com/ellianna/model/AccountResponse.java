package com.ellianna.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse{
    public Long id;
    public String username;
    public String fullName;
    public Boolean isActive;

    public AccountResponse(Long id, String username, String fullname) {
        this.id = id;
        this.username = username;
        this.fullName = fullname;
    }

    public AccountResponse(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.fullName = user.getFullName();
    }
}