package com.newidea.payment.service;


import com.newidea.payment.exceptions.EntityNotFoundException;
import com.newidea.payment.persistence.entity.SellerEntity;
import com.newidea.payment.persistence.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    SellerRepository repository;

    public SellerEntity getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
    }
}
