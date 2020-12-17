package com.thienhoa.blog.repository;

import com.thienhoa.blog.model.Role;
import com.thienhoa.blog.type.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}