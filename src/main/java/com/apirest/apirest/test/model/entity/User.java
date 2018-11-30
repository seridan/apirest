package com.apirest.apirest.test.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(name = "APP_USER_UK", columnNames = "user_name")})
public class  User implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(length = 60)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Id")
    private UserRole roles;

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    /*public User(String name, List<Role> roles) {
        this.name = name;
        this.roles = roles;
    }*/

    public User(String name, String password) {

        this.name = name;
        this.password = password;
    }

    /*public User(String name, String password, List<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id, String name, String password, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }*/

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRoles() {
        return roles;
    }

    public void setRoles(User role) {
        this.roles = roles;
    }

/*
    @PrePersist
     private void preInsert() {

        if (this.roles == null) {
            List<Role> defaultRole = new ArrayList<>();
            defaultRole.add(new Role("ROLE_STANDARD"));
            this.roles = defaultRole;
        }
    }*/
}


