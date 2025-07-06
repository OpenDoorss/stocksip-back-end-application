package com.stocksip.authentication.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 */
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    /**
     * Default constructor for JPA
     */
    protected User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
