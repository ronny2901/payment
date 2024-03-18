package com.newidea.payment.service;

import com.newidea.payment.exceptions.EntityNotFoundException;
import com.newidea.payment.exceptions.ResourceNotFoundException;
import com.newidea.payment.exceptions.UpdatePaymentException;
import com.newidea.payment.persistence.entity.PaymentEntity;
import com.newidea.payment.persistence.repositories.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    PaymentRepository repository;

    public List<PaymentEntity> findPaymentsBySellerId(List<Long> idsList, Long id) {
        log.info("Finding payments by ID!, id= {}", id);
        List<PaymentEntity> payments = repository.findPaymentsBySellerId(idsList, id);

        if (payments.isEmpty()) {
            throw new EntityNotFoundException("Payment not found for id:" + id);
        }
        return payments;
    }

    public void updatePayment(PaymentEntity payment) {
        var entity = repository.findById(payment.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for ID: " + payment.getPaymentId()));

        entity.setBalance(payment.getBalance());
        entity.setPaymentStatus(payment.getPaymentStatus());
       try {
           repository.save(entity);
           log.info("Payment update successfuly!, type-attachment={}", entity);
        } catch (IllegalArgumentException | DataAccessException e) {
            log.error("Balance was not updated!", e);
           throw new UpdatePaymentException("Failed to update payment", e);
        }
    }
}
