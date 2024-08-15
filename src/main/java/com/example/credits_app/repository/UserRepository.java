package com.example.credits_app.repository;

import com.example.credits_app.model.Role;
import com.example.credits_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(
            value = "SELECT * FROM USERS",
            nativeQuery = true
    )
    List<User> findAllUsers();

    @Query(
            value = "SELECT CASE WHEN COUNT(ID) > 0 THEN TRUE ELSE FALSE END FROM USERS WHERE USERNAME = ?",
            nativeQuery = true
    )
    boolean existsByUsernameUsers(String username);

    @Query(
            value = "SELECT CASE WHEN COUNT(ID) > 0 THEN TRUE ELSE FALSE END FROM USERS WHERE EMAIL = ?",
            nativeQuery = true
    )
    boolean existsByEmailUsers(String email);

    @Query(
            value = "SELECT * FROM USERS WHERE USERNAME = ?",
            nativeQuery = true
    )
    Optional<User> findByUsernameUsers(String username);

    @Query(
            value = "SELECT * FROM USERS WHERE ID = ?",
            nativeQuery = true
    )
    User findByIdUsers(Integer id);

    @Modifying
    @Query(
            value = "INSERT INTO USERS (USERNAME,PASSWORD,EMAIL,ROLE,CREDIT_SCORE) VALUES" +
                    "(?1,?2,?3,?4,?5)",
            nativeQuery = true
    )
    @Transactional
    void saveUsers(String username, String password, String email, String role, Integer credit_score);

    @Modifying
    @Query(
            value = "UPDATE USERS SET USERNAME = ?1 ,PASSWORD = ?2, EMAIL = ?3 WHERE ID = ?4",
            nativeQuery = true
    )
    @Transactional
    void updateUsers(String username, String password, String email, Integer id);

    @Modifying
    @Query(
            value = "DELETE FROM USERS WHERE ID = ?",
            nativeQuery = true
    )
    @Transactional
    void deleteUsers(Integer id);

    @Modifying
    @Query(
            value = "UPDATE USERS SET CREDIT_SCORE = ?1 WHERE ID = ?2",
            nativeQuery = true
    )
    @Transactional
    void updateUsersCreditScore(Integer credit_score, Integer id);

}
