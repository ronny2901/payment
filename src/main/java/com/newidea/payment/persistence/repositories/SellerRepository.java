package com.newidea.payment.persistence.repositories;


import com.newidea.payment.persistence.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
}
