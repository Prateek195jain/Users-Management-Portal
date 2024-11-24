/*
    Each of the methods defined below in the controller is mapped to a specific HTTP endpoint and interacts with
    the UsersManagementService to perform the necessary operations.
    The endpoints use different HTTP methods (GET, POST, PUT, DELETE) to define the type of operations they perform.
 */

package com.UserLink.UserManagementSystem.controller;

import com.UserLink.UserManagementSystem.dto.ReqRes;
import com.UserLink.UserManagementSystem.entity.OurUsers;
import com.UserLink.UserManagementSystem.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// Annotation to define a RESTful controller
@RestController
public class UserManagementController {

    // Dependency injection of UsersManagementService
    @Autowired
    private UsersManagementService usersManagementService;

    // Endpoint to register a new user
    //Handles user registration by accepting a ReqRes object in the request body and returning the registration response.
    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg) {
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    // Endpoint for user login
    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    // Endpoint to refresh authentication token
    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    // Endpoint to get all users, restricted to admin role
    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }

    // Endpoint to get user details by user ID, restricted to admin role
    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUserByID(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
    }

    // Endpoint to update user details by user ID, restricted to admin role
    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres) {
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    // Endpoint for users to get their own profile information
    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Get the email of the authenticated user
        ReqRes response = usersManagementService.getMyInfo(email); // Retrieve user info by email
        return ResponseEntity.status(response.getStatusCode()).body(response); // Return response with appropriate status code
    }

    // Endpoint to delete a user by user ID, restricted to admin role
    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
}
