package com.gireesh.repository;

import com.gireesh.modal.Customer;
import com.gireesh.payload.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String fullName, String keyword);
}
