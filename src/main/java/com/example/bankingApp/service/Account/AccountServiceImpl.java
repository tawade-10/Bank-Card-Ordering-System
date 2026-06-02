package com.example.bankingApp.service.Account;

import com.example.bankingApp.dto.AccountDto.AccountCreationRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.entity.Bank.Branch;
import com.example.bankingApp.entity.CustomUserDetails;
import com.example.bankingApp.entity.Bank.AccountCreation;
import com.example.bankingApp.entity.Bank.AccountType;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.AccountStatus;
import com.example.bankingApp.repository.Bank.AccountCreationRepo;
import com.example.bankingApp.repository.Bank.AccountTypeRepo;
import com.example.bankingApp.repository.Bank.BranchRepo;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Notifications.NotificationsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService{

    private final CustomersRepo customersRepo;

    private final AccountCreationRepo accountCreationRepo;

    private final AccountTypeRepo accountTypeRepo;

    private final BranchRepo branchRepo;

    private final NotificationsService notificationsService;

    public AccountServiceImpl(CustomersRepo customersRepo, AccountCreationRepo accountCreationRepo, AccountTypeRepo accountTypeRepo, BranchRepo branchRepo, NotificationsService notificationsService) {
        this.customersRepo = customersRepo;
        this.accountCreationRepo = accountCreationRepo;
        this.accountTypeRepo = accountTypeRepo;
        this.branchRepo = branchRepo;
        this.notificationsService = notificationsService;
    }

    @Override
    public AccountResponseDto createAccountRequest(AccountCreationRequestDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("User is not authenticated");
        }

        CustomUserDetails userDetails;

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } else {
            throw new RuntimeException("Invalid authentication principal: " +
                    authentication.getPrincipal());
        }

        Long customerId = userDetails.getCustomers().getCustomerId();

        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        AccountType accountType = accountTypeRepo.findById(dto.getAccountTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid Account Type"));

        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Invalid Branch"));

        AccountCreation accountCreation = new AccountCreation();

        accountCreation.setCustomer(customer);
        accountCreation.setAccountType(accountType);
        accountCreation.setBranch(branch);

        accountCreation.setStatus(AccountStatus.PENDING_REVIEW);
        accountCreation.setOpenedAt(LocalDateTime.now());
        accountCreation.setBalance(0.0);

        AccountCreation savedAccountCreation = accountCreationRepo.save(accountCreation);

        NotificationsRequestDto notificationDto = new NotificationsRequestDto();

        notificationDto.setCustomerId(customer.getCustomerId());
        notificationDto.setTitle("Account Request Submitted");
        notificationDto.setMessage(
                "Your request for a " +
                        accountType.getTypeName() +
                        " account has been submitted successfully and is pending review."
        );
        notificationDto.setType("ACCOUNT_REQUEST");
        notificationDto.setReferenceId(savedAccountCreation.getAccountId());

        notificationsService.createNotifications(notificationDto);

        return new AccountResponseDto(savedAccountCreation);
    }
}
