package com.example.bankingApp.service.Auth;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Roles;
import com.example.bankingApp.entity.Token.PasswordResetToken;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.repository.Token.PasswordResetTokenRepo;
import com.example.bankingApp.service.Email.EmailService;
import com.example.bankingApp.service.Notifications.NotificationsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomersRepo customersRepo;
    private final NotificationsService notificationsService;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthServiceImpl(CustomersRepo customersRepo,
                           NotificationsService notificationsService,
                           PasswordResetTokenRepo passwordResetTokenRepo,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService) {
        this.customersRepo = customersRepo;
        this.notificationsService = notificationsService;
        this.passwordResetTokenRepo = passwordResetTokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public CustomersCreationResponseDto registerCustomer(CustomersCreationRequestDto dto) {

        Optional<Customers> existingCustomerByEmail =
                customersRepo.findByEmail(dto.getEmail());

        if (existingCustomerByEmail.isPresent()) {
            return new CustomersCreationResponseDto(existingCustomerByEmail.get());
        }

        Customers customer = new Customers();
        customer.setCustomerName(dto.getCustomerName());
        customer.setEmail(dto.getEmail());
        customer.setRoles(Roles.CUSTOMER);
        customer.setPassword(dto.getPassword());
        customer.setCreatedDate(LocalDate.now());
        customer.setCreatedTime(LocalTime.now());

        Customers savedCustomer = customersRepo.save(customer);

        String registrationMessage = "Welcome to our banking system!";

        NotificationsRequestDto notificationDto = new NotificationsRequestDto();
        notificationDto.setCustomerId(savedCustomer.getCustomerId());
        notificationDto.setTitle("Registration Successful");
        notificationDto.setMessage(registrationMessage);
        notificationDto.setType("REGISTER_SUCCESS");
        notificationDto.setReferenceId(savedCustomer.getCustomerId());

        notificationsService.createNotifications(notificationDto);

        CustomersCreationResponseDto response =
                new CustomersCreationResponseDto(savedCustomer);

        response.setMessage(registrationMessage);

        return response;
    }

    @Transactional
    @Override
    public String generateResetToken(String email) {

        Optional<Customers> optionalCustomer = customersRepo.findByEmail(email);

        if (optionalCustomer.isEmpty()) {
            return "User not found";
        }

        Customers customer = optionalCustomer.get();

        // Delete previous tokens
        passwordResetTokenRepo.deleteByCustomer(customer);

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setCustomer(customer);
        resetToken.setExpiryTime(LocalDateTime.now().plusMinutes(15));

        passwordResetTokenRepo.save(resetToken);

        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        emailService.sendSimpleMessage(
                customer.getEmail(),
                "Reset Your Password",
                "Click the link to reset password:\n" + resetLink
        );

        return "Reset link sent to your email.";
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepo.findByToken(token);
        if (resetToken == null) {
            return "Invalid or used token";
        }
        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "Token expired";
        }

        Customers customer = resetToken.getCustomer();
        customer.setPassword(passwordEncoder.encode(newPassword));
        customersRepo.save(customer);

        passwordResetTokenRepo.delete(resetToken);

        return "Password reset successful";
    }
}