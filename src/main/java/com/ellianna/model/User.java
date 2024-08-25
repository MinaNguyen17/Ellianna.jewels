package com.ellianna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    @Column(unique = true)
    private String userName;
    private String password;
    private USER_ROLE role;

    @JsonIgnore // when fetch the user, not fetch order list!
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<Order>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // when user deleted, all his addresses will be deleted too
    private List<Address> addresses = new ArrayList<Address>();
}
