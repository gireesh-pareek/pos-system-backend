package com.gireesh.service.impl;

import com.gireesh.mapper.CustomerMapper;
import com.gireesh.modal.Customer;
import com.gireesh.payload.dto.CustomerDTO;
import com.gireesh.repository.CustomerRepository;
import com.gireesh.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        return CustomerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find customer with id " + id));

        existingCustomer.setFullName(customerDTO.getFullName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        return CustomerMapper.toDTO(customerRepository.save(existingCustomer));
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find customer with id " + id));
        customerRepository.delete(existingCustomer);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return CustomerMapper.toDTO(customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find customer with id " + id)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {

        return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword).stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }
}
