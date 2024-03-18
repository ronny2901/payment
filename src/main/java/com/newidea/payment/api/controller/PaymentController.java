package com.newidea.payment.api.controller;

import com.newidea.payment.api.dto.ChargeDto;
import com.newidea.payment.documentation.ProcessCharge;
import com.newidea.payment.exceptions.EntityNotFoundException;
import com.newidea.payment.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private ChargeService service;

    @ProcessCharge
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processCharge(@RequestBody ChargeDto charge) {
        try {
            var responseData = service.processCharge(charge);
            var resourceURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                    .buildAndExpand(responseData.getSellerId()).toUri();
            return ResponseEntity.created(resourceURI).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }
}
