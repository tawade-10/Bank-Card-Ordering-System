package com.example.bankingApp.entity.Bank;

import jakarta.persistence.*;

@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", unique = true)
    private Long branchId;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Column(name = "IFSC_Code", unique = true)
    private String ifscCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(unique = true)
    private String branchCode;

    @Column(name = "city")
    private String city;

    @Column(name = "next_account_sequence", nullable = false)
    private Long nextAccountSequence = 1L;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() { return branchCode; }

    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getNextAccountSequence() { return nextAccountSequence; }

    public void setNextAccountSequence(Long nextAccountSequence) { this.nextAccountSequence = nextAccountSequence; }
}
