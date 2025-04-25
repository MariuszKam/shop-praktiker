package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.utilis.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  TestEntityManager testEntityManager;

    @Test
    public void shouldFindUserByUsername() {
        User user = UserTestFactory.createUser();

        testEntityManager.persist(user);
        testEntityManager.flush();

        Optional<User> found = userRepository.findByUsername("Adam");
        assertTrue(found.isPresent(), "User not found by username");
        User actual = found.get();
        assertEquals("Adam", actual.getUsername(), "Username is different!");
        assertEquals("Password123", actual.getPassword(), "Password is different!");
        assertEquals("adam@mail.com", actual.getEmail(), "Email is different!");
    }
}
