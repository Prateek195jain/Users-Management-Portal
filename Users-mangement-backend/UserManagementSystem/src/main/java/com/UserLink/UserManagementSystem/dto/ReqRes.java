/*
    This is a Data Transfer Object (DTO) that puts together different pieces of data into one object
    and moves it between different parts of the application.
    It means we can gather various pieces of information into one object that's easy to pass around.
    For example, in this ReqRes class, the DTO bundles up user details, authentication tokens, status codes,
    and error messages into one neat package.
*/

package com.UserLink.UserManagementSystem.dto;

import com.UserLink.UserManagementSystem.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;


//@Data // Lombok generates all the boilerplate code like getters, setters, etc. for us.
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null properties in the JSON output.
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any properties in the JSON input that don't have a corresponding field in this class.
public class ReqRes {

    // HTTP status code for the response.
    private int statusCode;

    // Error message, if there's an error.
    private String error;

    // General message, like "Success" or "Error occurred".
    private String message;

    // Authentication token for the user.
    private String token;

    // Token to get a new authentication token when the current one expires.
    private String refreshToken;

    // Time when the token expires.
    private String expirationTime;

    // User's name.
    private String name;

    // User's city.
    private String city;

    // User's role, like "admin" or "user".
    private String role;

    // User's email address.
    private String email;

    // User's password.
    private String password;

    // Single user details.
    private OurUsers ourUsers;

    // List of multiple users.
    private List<OurUsers> ourUsersList;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OurUsers getOurUsers() {
        return ourUsers;
    }

    public void setOurUsers(OurUsers ourUsers) {
        this.ourUsers = ourUsers;
    }

    public List<OurUsers> getOurUsersList() {
        return ourUsersList;
    }

    public void setOurUsersList(List<OurUsers> ourUsersList) {
        this.ourUsersList = ourUsersList;
    }
}
