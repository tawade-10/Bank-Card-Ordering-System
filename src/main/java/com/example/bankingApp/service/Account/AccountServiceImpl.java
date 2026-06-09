package com.example.bankingApp.service.Account;

import com.example.bankingApp.dto.AccountDto.Creation.CreationRequestDto;
import com.example.bankingApp.dto.AccountDto.Creation.CreationResponseDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountResponseDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.entity.Bank.*;
import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.CustomUserDetails;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.AccountStatus;
import com.example.bankingApp.repository.Bank.*;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.AccountNumberGenerator;
import com.example.bankingApp.service.Notifications.NotificationsService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private final CustomersRepo customersRepo;

    private final AccountCreationRepo accountCreationRepo;

    private final AccountTypeRepo accountTypeRepo;

    private final BranchRepo branchRepo;

    private final AccountRequestRepo accountRequestRepo;

    private final BankRepo bankRepo;

    private final NotificationsService notificationsService;

    private final AccountNumberGenerator accountNumberGenerator;

    public AccountServiceImpl(CustomersRepo customersRepo, AccountCreationRepo accountCreationRepo, AccountTypeRepo accountTypeRepo, BranchRepo branchRepo, AccountRequestRepo accountRequestRepo, BankRepo bankRepo, NotificationsService notificationsService, AccountNumberGenerator accountNumberGenerator) {
        this.customersRepo = customersRepo;
        this.accountCreationRepo = accountCreationRepo;
        this.accountTypeRepo = accountTypeRepo;
        this.branchRepo = branchRepo;
        this.accountRequestRepo = accountRequestRepo;
        this.bankRepo = bankRepo;
        this.notificationsService = notificationsService;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Override
    public AccountResponseDto createAccountRequest(AccountRequestDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("User is not authenticated");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Customers customer = customersRepo.findById(userDetails.getCustomers().getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        AccountType accountType = accountTypeRepo.findById(dto.getAccountTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid Account Type"));

        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Invalid Branch"));

//        if (!branch.getBank().getBankId().equals(dto.getBankId())) {
//            throw new RuntimeException(
//                    "Selected branch does not belong to selected bank");
//        }

        Bank bank = bankRepo.findById(dto.getBankId())
                .orElseThrow(() -> new RuntimeException("Invalid Bank"));

        AccountRequest request = new AccountRequest();

        request.setCustomer(customer);
        request.setAccountType(accountType);
        request.setBranch(branch);
        request.setStatus(AccountStatus.PENDING_REVIEW);
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());

        AccountRequest saved = accountRequestRepo.save(request);

        NotificationsRequestDto notificationDto = new NotificationsRequestDto();

        notificationDto.setCustomerId(customer.getCustomerId());
        notificationDto.setTitle("Account Request Submitted");
        notificationDto.setMessage(
                "Your request for a "
                        + accountType.getTypeName()
                        + " account has been submitted successfully."
        );
        notificationDto.setType("ACCOUNT_REQUEST");
        notificationDto.setReferenceId(saved.getRequestId());

        notificationsService.createNotifications(notificationDto);

        AccountResponseDto response = new AccountResponseDto(saved);

        response.setMessage("Account request submitted successfully.");

        return response;
    }

    @Override
    public List<AccountResponseDto> getPendingRequests() {
        List<AccountRequest> requests = accountRequestRepo.findByStatus(AccountStatus.PENDING_REVIEW);
        return requests.stream().map(AccountResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public CreationResponseDto createAccount(CreationRequestDto creationRequestDto) {

        AccountRequest request = accountRequestRepo.findById(creationRequestDto.getRequestId())
                        .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != AccountStatus.PENDING_REVIEW) {
            throw new RuntimeException("Request already processed");
        }

        String accountNumber;

        do {
            accountNumber = accountNumberGenerator.generate();
        } while (
                accountCreationRepo.existsByAccountNumber(accountNumber)
        );

        Account account = new Account();

        account.setCustomer(request.getCustomer());
        account.setBranch(request.getBranch());
        account.setAccountType(request.getAccountType());
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.valueOf(0.0));
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setOpenedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setMessage(creationRequestDto.getMessage());

        Account savedAccount = accountCreationRepo.save(account);

        request.setStatus(AccountStatus.ACTIVE);
        request.setUpdatedAt(LocalDateTime.now());

        accountRequestRepo.save(request);

        return new CreationResponseDto(savedAccount);
    }

    @Override
    public List<CreationResponseDto> getAllAccounts() {
        List<Account> accounts = accountCreationRepo.findAll();
        return accounts.stream().map(CreationResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public CreationResponseDto getAccountById(Long accountId) {

        Account account = accountCreationRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account Not found"));

        return new CreationResponseDto(account);
    }

    @Override
    public AccountResponseDto getRequestById(Long requestId) {

        AccountRequest accountRequest = accountRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Account Not found"));

        return new AccountResponseDto(accountRequest);
    }


}
