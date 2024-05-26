package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.Role;
import com.backend.mediConnect.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
