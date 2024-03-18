package com.newidea.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newidea.payment.api.controller.PaymentController;
import com.newidea.payment.api.dto.ChargeDto;
import com.newidea.payment.api.dto.PaymentsDto;
import com.newidea.payment.exceptions.EntityNotFoundException;
import com.newidea.payment.service.ChargeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ChargeService chargeService;

    @InjectMocks
    private PaymentController paymentController;

    private ChargeDto chargeDto;

    private PaymentsDto paymentsDto;

    @BeforeEach
    public void setup() {
        openMocks(this); // Inicializa os mocks

        // Inicializa os objetos
        paymentsDto = new PaymentsDto();
        paymentsDto.setPaymentId(1L);
        paymentsDto.setPaymentStatus("PENDENTE");
        paymentsDto.setAmountPaid(new BigDecimal(10.00));

        chargeDto = new ChargeDto();
        chargeDto.setSellerId(123L);

        List<PaymentsDto> listPayments = new ArrayList<>();
        listPayments.add(paymentsDto);
        chargeDto.setPayments(listPayments);

        // Inicializa o MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void testProcessCharge_Success() throws Exception {
        // Configura o comportamento do mock
        given(chargeService.processCharge(any(ChargeDto.class))).willReturn(chargeDto);

        // Executa a requisição e verifica o status
        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(chargeDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testProcessCharge_EntityNotFoundException() throws Exception {
        // Configura o comportamento do mock
        given(chargeService.processCharge(any(ChargeDto.class))).willThrow(new EntityNotFoundException("Seller not found"));

        // Executa a requisição e verifica o status
        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(chargeDto)))
                .andExpect(status().isNotFound());
    }
}