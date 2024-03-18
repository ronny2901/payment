package com.newidea.payment.persistence.repositories;

import com.newidea.payment.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.paymentId IN ?1 AND p.seller.id = ?2")
    List<PaymentEntity> findPaymentsBySellerId(List<Long> paymentIds, Long sellerId);
}
