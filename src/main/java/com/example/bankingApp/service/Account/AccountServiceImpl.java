package com.example.bankingApp.service.Account;

import com.example.bankingApp.dto.AccountDto.Creation.CreationRequestDto;
import com.example.bankingApp.dto.AccountDto.Creation.CreationResponseDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.entity.Bank.Account;
import com.example.bankingApp.entity.Bank.AccountRequest;
import com.example.bankingApp.entity.Bank.Branch;
import com.example.bankingApp.entity.CustomUserDetails;
import com.example.bankingApp.entity.Bank.AccountType;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.AccountStatus;
import com.example.bankingApp.repository.Bank.AccountCreationRepo;
import com.example.bankingApp.repository.Bank.AccountRequestRepo;
import com.example.bankingApp.repository.Bank.AccountTypeRepo;
import com.example.bankingApp.repository.Bank.BranchRepo;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Notifications.NotificationsService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    private final NotificationsService notificationsService;

    public AccountServiceImpl(CustomersRepo customersRepo, AccountCreationRepo accountCreationRepo, AccountTypeRepo accountTypeRepo, BranchRepo branchRepo, AccountRequestRepo accountRequestRepo, NotificationsService notificationsService) {
        this.customersRepo = customersRepo;
        this.accountCreationRepo = accountCreationRepo;
        this.accountTypeRepo = accountTypeRepo;
        this.branchRepo = branchRepo;
        this.accountRequestRepo = accountRequestRepo;
        this.notificationsService = notificationsService;
    }

    @Override
    public AccountResponseDto createAccountRequest(AccountRequestDto dto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

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

        AccountRequest request = new AccountRequest();

        request.setCustomer(customer);
        request.setAccountType(accountType);
        request.setBranch(branch);
        request.setStatus(AccountStatus.PENDING_REVIEW);
        request.setCreatedAt(LocalDateTime.now());
//        request.setUpdatedAt(LocalDateTime.now());

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
        List<AccountRequest> requests = accountRequestRepo.findAll(Sort.by(Sort.Direction.ASC, "requestId"));
        return requests.stream().map(AccountResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public CreationResponseDto createAccount(CreationRequestDto creationRequestDto) {

        AccountRequest request = accountRequestRepo.findById(creationRequestDto.getRequestId())
                        .orElseThrow(() -> new RuntimeException("Request not found"));

        if(request.getStatus() != AccountStatus.PENDING_REVIEW){
            throw new RuntimeException(
                    "Request already processed");
        }

        Account account = new Account();
        account.setCustomer(request.getCustomer());
        account.setBranch(request.getBranch());
        account.setAccountType(request.getAccountType());
        account.setBalance(0.0);
        account.setAccountStatus(AccountStatus.ACTIVE);
//        account.setAccountNumber(accountNumberGenerator());
        account.setOpenedAt(LocalDateTime.now());

        Account savedAccount = accountCreationRepo.save(account);

        request.setStatus(AccountStatus.ACTIVE);
//        request.setUpdatedDate(LocalDateTime.now());
//        request.setUpdatedTime(LocalTime.now());
        accountRequestRepo.save(request);

        return new CreationResponseDto(savedAccount);
    }
}
