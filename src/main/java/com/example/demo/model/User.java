package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;;

@Entity
@Table(name = "users")
@ApiModel(description = "Model to create a new User")
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    @ApiModelProperty(notes = "User name for a user", example = "devesh_91")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @ApiModelProperty(notes = "ROLE for a user", example = "ADMIN/MANAGER")
    private String role;

    public User(Long userId, String userName, String password, String role) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public User() {
        super();
    }

    public User(Long userId, String userName, String password) {
        super();

        this.userName = userName;
        this.password = password;
        this.userId = userId;

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {

        return this.userId.intValue();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof User))
            return false;
        return this.userId == ((User) obj).getUserId();
    }

}
