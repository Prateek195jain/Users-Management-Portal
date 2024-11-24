/*
 this is an entity class.
 This class represents a user in the system and maps to the ourusers table in the database.
*/

package com.UserLink.UserManagementSystem.entity;

import jakarta.persistence.*;// It includes JPA annotations
import lombok.Data; // it generates boiler plate code like getters and setters
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity // indicates that this class is a JPA entity.
@Table(name = "ourusers") // this is the table to which this entity maps
//@Data // from Lombok generates getters and setters
public class OurUsers implements UserDetails { // this implements UserDetails from spring security for authentication and authorization

    @Id // primary key of entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)// specifies that the ID should be generated automatically by the database.

    //These fields represent the user's email, name, password, city, and role.
    private Integer id;
    private String email;
    private String name;
    private String password;
    private String city;
    private String role;

    // this method returns the role granted to the users
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    // return user's pass
    @Override
    public String getPassword() {
        return password;
    }

    // returns username for authentication - email(here)
    @Override
    public String getUsername() {
        return email;
    }

    // checks if the user's account is expired or not
    @Override
    public boolean isAccountNonExpired() {
        return true; // true means that account is never expired
    }

    // it indicates whether the user's account is locked or not
    @Override
    public boolean isAccountNonLocked() {
        return true; // true means never locked
    }

    // checks if the pass is expired or not
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // never expired
    }

    //checks if user is enabled
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
