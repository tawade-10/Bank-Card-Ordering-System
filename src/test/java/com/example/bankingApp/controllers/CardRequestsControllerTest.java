package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.repository.CardRequests.CardRequestsRepo;
import com.example.bankingApp.service.CardRequests.CardRequestsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardRequestsControllerTest {

    @Mock
    CardRequestsRepo cardRequestsRepo;

    @InjectMocks
    CardRequestsServiceImpl cardRequestsServiceImpl;

    @Test
    void createdRequestSuccessfully() {
        RequestsDto requests = new RequestsDto();
        cardRequestsServiceImpl.createRequest(requests);
    }

    @Test
    void getAllRequests() {
    }

    @Test
    void getRequestById() {
    }

    @Test
    void getRequestsByEmail() {
    }

    @Test
    void getBinByNetwork() {
    }

    @Test
    void reviewRequest() {
    }

    @Test
    void updateRequestStatus() {
    }
}