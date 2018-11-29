package com.apirest.apirest.test.model.entity;

import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_name"})})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleid;

    @Column(name = "role_name")
    private String roleName;

    public Role() {
    }


    public Role( String roleName) {
        this.roleName = roleName;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

   /* @PrePersist
    void preInsert() {
        if (this.roleName == null)
            this.roleName = "ROLE_STANDAR";
    }*/
}
