package com.example.bankingApp.facade.AuthFacade;

import com.example.bankingApp.config.JwtService;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.CustomersDto.LoginResponse;
import com.example.bankingApp.entity.CustomUserDetails;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Auth.AuthService;
import com.example.bankingApp.service.Notification.NotificationService;
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
    private final NotificationService notificationService;
    private final CustomersRepo customersRepo;

    public AuthFacadeImpl(JwtService jwtService,
                          AuthenticationManager authenticationManager,
                          AuthService authService, BCryptPasswordEncoder encoder, NotificationService notificationService, CustomersRepo customersRepo) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.encoder = encoder;
        this.notificationService = notificationService;
        this.customersRepo = customersRepo;
    }

    @Override
    public CustomersCreationResponseDto registerCustomer(CustomersCreationRequestDto customersCreationRequestDto) {
        customersCreationRequestDto.setPassword(encoder.encode(customersCreationRequestDto.getPassword()));
        return authService.registerCustomer(customersCreationRequestDto);
    }

    @Override
    public Object loginCustomer(CustomersCreationRequestDto customersCreationRequestDto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            customersCreationRequestDto.getEmail(),
                            customersCreationRequestDto.getPassword()
                    )
            );
            CustomUserDetails customerDetails = (CustomUserDetails) auth.getPrincipal();
            Long userId = customerDetails.getCustomers().getCustomerId();
            String customerName = customerDetails.getCustomers().getCustomerName();
            notificationService.createNotification(
                    userId,
                    "Login Successful",
                    "Welcome back, " + customerName + "!",
                    "LOGIN_SUCCESS",
                    userId
            );
            String token = jwtService.generateToken(customerDetails);
            return new LoginResponse(
                    token,
                    customerDetails.getCustomers().getCustomerName(),
                    customerDetails.getCustomers().getEmail(),
                    customerDetails.getCustomers().getRoles(),
                    userId
            );
        } catch (Exception ex) {
            customersRepo.findByEmail(customersCreationRequestDto.getEmail())
                    .ifPresent(c -> {
                        notificationService.createNotification(
                                c.getCustomerId(),
                                "Login Failed",
                                "Incorrect Email or Password",
                                "LOGIN_FAILED",
                                c.getCustomerId()
                        );
                    });
            throw new RuntimeException("Invalid Credentials");
        }
    }

    @Override
    public String generateResetToken(String email) {
        return authService.generateResetToken(email);
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        return authService.resetPassword(token,newPassword);
    }
}