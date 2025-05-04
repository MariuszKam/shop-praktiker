package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.user.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldFindRoleByName() {
        Role role = new Role();
        role.setName("ROLE_USER");

        testEntityManager.persist(role);
        testEntityManager.flush();

        Role found = roleRepository.findByName("ROLE_USER")
                                   .orElseThrow(() -> new AssertionError("Role not found"));

        assertThat(found.getName()).isEqualTo("ROLE_USER");
        assertThat(found.getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("should return empty when role name does not exist")
    void shouldReturnEmptyWhenRoleNotFound() {
        Optional<Role> result = roleRepository.findByName("ROLE_UNKNOWN");
        assertThat(result).isEmpty();
    }

}
