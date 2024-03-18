package com.newidea.payment.api.dto;

import java.math.BigDecimal;

public class PaymentsDto {

    private Long paymentId;
    private BigDecimal paymentValue;

    private String paymentStatus;

    public PaymentsDto() {
    }

    public PaymentsDto(Long paymentId, BigDecimal amountPaid, String paymentStatus) {
        this.paymentId = paymentId;
        this.paymentValue = amountPaid;
        this.paymentStatus = paymentStatus;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.paymentValue = amountPaid;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmountPaid() {
        return paymentValue;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
