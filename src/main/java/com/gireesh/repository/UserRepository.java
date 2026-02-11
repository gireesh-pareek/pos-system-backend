package com.gireesh.repository;

import com.gireesh.modal.Branch;
import com.gireesh.modal.Store;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByStore(Store store);

    List<User> findByBranch(Branch branch);
}
