package com.stocksip.orderoperationandmonitoring.infrastructure.dto;

public class AccountDTO {
    private Long accountId;
    private String email;
    private Long userOwnerId;
    private String role;
    private String businessName;

    // Getters y setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(Long userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
