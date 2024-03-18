package com.newidea.payment.repositoriesTest;

import com.newidea.payment.persistence.entity.PaymentEntity;
import com.newidea.payment.persistence.entity.SellerEntity;
import com.newidea.payment.persistence.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository repository;
    private PaymentEntity payment;
    private SellerEntity seller;

    @BeforeEach()
    public void setup() {

        // Given
        seller = new SellerEntity();
        seller.setId(1L);


        payment = new PaymentEntity();
        payment.setPaymentId(1L);
        payment.setPaymentStatus("PENDENTE");
        payment.setBalance(new BigDecimal(10.00));
        payment.setSeller(seller);

        repository.save(payment);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setPaymentId(2L);
        payment2.setPaymentStatus("PENDENTE");
        payment2.setBalance(new BigDecimal(10.00));
        payment2.setSeller(seller);

        repository.save(payment2);
    }

    @Test
    void testFindPaymentsBySellerId_WhenPaymentsExist_ReturnPaymentsList2() {

        // Given
        List<Long> paymentIds = Arrays.asList(1L, 2L);

        // When
        List<PaymentEntity> payments = repository.findPaymentsBySellerId(paymentIds, 1L);

        // Then
        assertNotNull(payments);
        assertEquals(2, payments.size());
    }

    @Test
    void testFindPaymentsBySellerId_WhenNoPaymentsExist_ReturnEmptyList() {
        // Given
        Long sellerId = 2L;
        List<Long> paymentIds = Arrays.asList(1L, 2L);

        // When
        List<PaymentEntity> payments = repository.findPaymentsBySellerId(paymentIds, sellerId);

        // Then
        assertNotNull(payments);
        assertTrue(payments.isEmpty());
    }
}