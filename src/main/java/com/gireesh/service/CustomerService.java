package com.gireesh.service;

import com.gireesh.modal.Customer;
import com.gireesh.payload.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomer(Long id);
    CustomerDTO getCustomerById(Long id);
    List<CustomerDTO> getAllCustomers();
    List<CustomerDTO> searchCustomer(String keyword);
}
