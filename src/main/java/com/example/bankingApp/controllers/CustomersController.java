package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.facade.CustomersFacade.CustomersFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    private final CustomersFacade customersFacade;

    public CustomersController(CustomersFacade customersFacade) {
        this.customersFacade = customersFacade;
    }

    @GetMapping
    public ResponseEntity<List<CustomersResponseDto>> getAllCustomers(){
        List<CustomersResponseDto> allCustomers = customersFacade.getAllCustomers();
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomersResponseDto> getCustomerById(@PathVariable Long customerId){
        CustomersResponseDto customerById = customersFacade.getCustomerById(customerId);
        return ResponseEntity.ok(customerById);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomersResponseDto> updateCustomer(@PathVariable Long customerId, @RequestBody CustomersRequestDto customersRequestDto){
        CustomersResponseDto updatedCustomer = customersFacade.updateCustomer(customerId,customersRequestDto);
        return ResponseEntity.ok(updatedCustomer);
    }
}
