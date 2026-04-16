package com.example.bankingApp.config;

import com.example.bankingApp.entity.CustomUserDetails;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomersRepo customersRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new CustomUserDetails(customer);
    }
}