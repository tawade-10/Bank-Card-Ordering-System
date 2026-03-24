package com.example.bankingApp.facade.AuthFacade;

import com.example.bankingApp.config.JwtService;
import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.dto.CustomersDto.LoginResponse;
import com.example.bankingApp.entity.Customers;
import com.example.bankingApp.service.Customers.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;

    public AuthFacadeImpl(JwtService jwtService,
                          AuthenticationManager authenticationManager,
                          AuthService authService, BCryptPasswordEncoder encoder) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.encoder = encoder;
    }

    @Override
    public CustomersResponseDto registerCustomer(CustomersRequestDto customersRequestDto) {
        customersRequestDto.setPassword(encoder.encode(customersRequestDto.getPassword()));
        return authService.registerCustomer(customersRequestDto);
    }

    @Override
    public Object loginCustomer(CustomersRequestDto customersRequestDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customersRequestDto.getEmail(), customersRequestDto.getPassword())
        );

        Customers customer = (Customers) auth.getPrincipal();
        String token = jwtService.generateToken(customer);

        return new LoginResponse(token, customer.getCustomerName(), customer.getEmail());
    }
}