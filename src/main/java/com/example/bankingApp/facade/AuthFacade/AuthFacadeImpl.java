package com.example.bankingApp.facade.AuthFacade;

import com.example.bankingApp.config.JwtService;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.CustomersDto.LoginResponse;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.entity.CustomUserDetails;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Auth.AuthService;
import com.example.bankingApp.service.Notifications.NotificationsService;
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
    private final NotificationsService notificationsService;
    private final CustomersRepo customersRepo;

    public AuthFacadeImpl(JwtService jwtService,
                          AuthenticationManager authenticationManager,
                          AuthService authService,
                          BCryptPasswordEncoder encoder,
                          NotificationsService notificationsService,
                          CustomersRepo customersRepo) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.encoder = encoder;
        this.notificationsService = notificationsService;
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

            // Build SUCCESS notification DTO
            NotificationsRequestDto successDto = new NotificationsRequestDto();
            successDto.setCustomerId(userId);
            successDto.setTitle("Login Successful");
            successDto.setMessage("Welcome back, " + customerName + "!");
            successDto.setType("LOGIN_SUCCESS");
            successDto.setReferenceId(userId);

            NotificationsResponseDto notification = notificationsService.createNotification(successDto);

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
                        NotificationsRequestDto failedDto = new NotificationsRequestDto();
                        failedDto.setCustomerId(c.getCustomerId());
                        failedDto.setTitle("Login Failed");
                        failedDto.setMessage("Incorrect Email or Password");
                        failedDto.setType("LOGIN_FAILED");
                        failedDto.setReferenceId(c.getCustomerId());

                        notificationsService.createNotification(failedDto);
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
        return authService.resetPassword(token, newPassword);
    }
}