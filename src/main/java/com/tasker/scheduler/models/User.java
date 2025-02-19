package com.tasker.scheduler.models;

import jakarta.persistence.*;
import lombok.*;


import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first", nullable = false)
    private String firstName;

    @Column(name = "last", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "enabled")
    private boolean enabled = true;
}
