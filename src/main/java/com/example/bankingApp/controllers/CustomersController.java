package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.CustomersDto.UpdateDto.CustomersUpdateResponseDto;
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
    public ResponseEntity<List<CustomersCreationResponseDto>> getAllCustomers(){
        List<CustomersCreationResponseDto> allCustomers = customersFacade.getAllCustomers();
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomersCreationResponseDto> getCustomerById(@PathVariable Long customerId){
        CustomersCreationResponseDto customerById = customersFacade.getCustomerById(customerId);
        return ResponseEntity.ok(customerById);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomersUpdateResponseDto> updateCustomer(@PathVariable Long customerId, @RequestBody CustomersCreationRequestDto customersCreationRequestDto){
        CustomersUpdateResponseDto updatedCustomer = customersFacade.updateCustomer(customerId, customersCreationRequestDto);
        return ResponseEntity.ok(updatedCustomer);
    }
}
