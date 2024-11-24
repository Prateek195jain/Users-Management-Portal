/*
    This class if to perform CRUD operations.
    ex -> when a user registers its data is saved in the database using the save method provided by the JpaRepository
    if users logs in we can access their information using their email
 */

package com.UserLink.UserManagementSystem.repository;

import com.UserLink.UserManagementSystem.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<OurUsers, Integer> {
    /*
    below line declares a method to find a user by their email. The method returns an Optional<OurUsers>,
     which means it might return an OurUsers object or be empty if no user with the given email is found.
     */
    Optional<OurUsers> findByEmail(String email);
}
