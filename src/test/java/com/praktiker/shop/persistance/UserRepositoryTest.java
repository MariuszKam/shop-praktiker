package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.utilis.factories.UserTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldFindUserByUsername() {
        User user = UserTestFactory.createUser();

        Role role = new Role();
        role.setName("ROLE_USER");

        testEntityManager.persist(role);

        user.getRoles().add(role);

        testEntityManager.persist(user);
        testEntityManager.flush();

        User found = userRepository.findByUsername(user.getUsername())
                                   .orElseThrow(() -> new AssertionError("User not found"));

        assertThat(found.getUsername()).isEqualTo(user.getUsername());
        assertThat(found.getPassword()).isEqualTo(user.getPassword());
        assertThat(found.getEmail()).isEqualTo(user.getEmail());

        assertThat(found.getRoles())
                .hasSize(1)
                .extracting(Role::getName)
                .containsExactly(role.getName());
    }

    @Test
    @DisplayName("should return empty when username not found")
    public void shouldReturnEmptyWhenUserNotFound() {
        Optional<User> result = userRepository.findByUsername("ghost");
        assertThat(result).isEmpty();
    }
}
