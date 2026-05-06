package com.example.bankingApp.dto.NetworkDto;

public class NetworkResponseDto {

    private String bin;

    public NetworkResponseDto(String bin) {
        this.bin = bin;
    }

    public NetworkResponseDto() {
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }
}
