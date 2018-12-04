package com.apirest.apirest.test.model.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class  User implements Serializable {


    private static final long serialVersionUID = 1L;

    @Autowired
    private Role role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(length = 60)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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

    @PrePersist
    private void setDefaultRole() {

        for (Role role : this.roles ) {
            if(role.getRoleName().equals("")) {
                List<Role> defaultRole = new ArrayList<>();
                defaultRole.add(new Role("ROLE_STANDARD"));
                this.roles = defaultRole;
            }
        }


    }
}

