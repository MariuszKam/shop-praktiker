package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        Optional<Role> found = roleRepository.findByName("ROLE_USER");
        assertTrue(found.isPresent(), "Role not found by name");
        Role actual = found.get();
        assertEquals("ROLE_USER", actual.getName(), "Role name is different!");
        assertEquals("ROLE_USER", actual.getAuthority(), "Authority is different!");
    }

}
