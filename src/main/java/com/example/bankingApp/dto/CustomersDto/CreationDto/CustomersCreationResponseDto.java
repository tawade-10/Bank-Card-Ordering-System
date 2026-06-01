    package com.example.bankingApp.dto.CustomersDto.CreationDto;

    import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
    import com.example.bankingApp.entity.Customers.Customers;
    import com.example.bankingApp.entity.Enums.Roles;
    import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    import java.time.LocalDate;
    import java.time.LocalTime;

    @JsonPropertyOrder({
            "customerId",
            "customerName",
            "email",
            "roles",
            "createdDate",
            "createdTime",
            "message"
    })
    public class CustomersCreationResponseDto {

        private Long customerId;

        private String customerName;

        private String email;

        private Roles roles;

        private LocalDate createdDate;

        private LocalTime createdTime;

        private String message;

        public CustomersCreationResponseDto(Customers customers) {
            this.customerId = customers.getCustomerId();
            this.customerName = customers.getCustomerName();
            this.email = customers.getEmail();
            this.roles = customers.getRoles();
            this.createdDate = customers.getCreatedDate();
            this.createdTime = customers.getCreatedTime();
        }

        public CustomersCreationResponseDto(NotificationsResponseDto notificationsResponseDto){
            this.message = notificationsResponseDto.getMessage();
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalDate getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(LocalDate createdDate) {
            this.createdDate = createdDate;
        }

        public LocalTime getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(LocalTime createdTime) {
            this.createdTime = createdTime;
        }

        public Roles getRoles() {
            return roles;
        }

        public void setRoles(Roles roles) {
            this.roles = roles;
        }

        public String getMessage() { return message; }

        public void setMessage(String message) { this.message = message; }
    }
