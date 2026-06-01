package com.example.bankingApp.service.Account;

import com.example.bankingApp.dto.AccountDto.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.entity.CustomUserDetails;
import com.example.bankingApp.entity.Customers.Account;
import com.example.bankingApp.entity.Customers.AccountType;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Status;
import com.example.bankingApp.repository.Customers.AccountRepo;
import com.example.bankingApp.repository.Customers.AccountTypeRepo;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Notifications.NotificationsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;

public class AccountServiceImpl implements AccountService{

    private final CustomersRepo customersRepo;

    private final AccountRepo accountRepo;

    private final AccountTypeRepo accountTypeRepo;

    private final NotificationsService notificationsService;

    public AccountServiceImpl(CustomersRepo customersRepo, AccountRepo accountRepo, AccountTypeRepo accountTypeRepo, NotificationsService notificationsService) {
        this.customersRepo = customersRepo;
        this.accountRepo = accountRepo;
        this.accountTypeRepo = accountTypeRepo;
        this.notificationsService = notificationsService;
    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long customerId = userDetails.getCustomers().getCustomerId();

        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        AccountType accountType = accountTypeRepo.findById(dto.getAccountTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid Account Type"));

        Account account = new Account();

        account.setCustomer(customer);
        account.setAccountType(accountType);

        account.setPurpose(dto.getPurpose());

        account.setStatus(Status.PENDING_REVIEW);

        account.setCreatedDate(LocalDate.now());
        account.setCreatedTime(LocalTime.now());

        Account savedAccount = accountRepo.save(account);

        NotificationsRequestDto notificationDto = new NotificationsRequestDto();

        notificationDto.setCustomerId(customer.getCustomerId());
        notificationDto.setTitle("Account Request Submitted");
        notificationDto.setMessage(
                "Your request for a "
                        + accountType.getTypeName()
                        + " account has been submitted successfully and is pending review."
        );
        notificationDto.setType("ACCOUNT_REQUEST");
        notificationDto.setReferenceId(savedAccount.getAccountId());

        notificationsService.createNotifications(notificationDto);

        return new AccountResponseDto(savedAccount);
    }
}
