package com.apirest.apirest.test.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
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

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private List<Role> roles;

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, List<Role> roles) {
        this.name = name;
        this.roles = roles;
    }

    public User(String name, String password) {

        this.name = name;
        this.password = password;
    }

    public User(String name, String password, List<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
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


