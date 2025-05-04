package com.ferdi.workflow_panel_backend.repository;

import com.ferdi.workflow_panel_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySystemUserId(UUID systemUserId);
}
