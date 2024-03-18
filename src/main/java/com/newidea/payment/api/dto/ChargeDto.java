package com.newidea.payment.api.dto;

import java.util.List;

public class ChargeDto {

    private  Long sellerId;

    private List<PaymentsDto> payments;

    public ChargeDto() {
    }

    public ChargeDto(Long sellerId, List<PaymentsDto> payments) {
        this.sellerId = sellerId;
        this.payments = payments;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public List<PaymentsDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentsDto> payments) {
        this.payments = payments;
    }
}
