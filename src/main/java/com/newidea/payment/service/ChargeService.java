package com.newidea.payment.service;

import com.newidea.payment.api.dto.PaymentsDto;
import com.newidea.payment.kafka.KafkaProducerService;
import com.newidea.payment.persistence.entity.PaymentEntity;
import com.newidea.payment.persistence.entity.SellerEntity;
import com.newidea.payment.persistence.repositories.PaymentRepository;
import com.newidea.payment.api.dto.ChargeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ChargeService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    SellerService sellerService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    KafkaProducerService kafkaService;

    public ChargeDto processCharge(ChargeDto charge) {
        log.info("Processing Charges!");

        var seller = checkSeller(charge.getSellerId());

        if (seller != null) {
            var paymentIds = charge.getPayments()
                    .stream().map(PaymentsDto::getPaymentId).toList();
            var paymentsList = checkPayments(paymentIds, seller.getId());

            if (!paymentsList.isEmpty()) {
                charge.getPayments().forEach(paymentDto -> {
                    PaymentEntity payment = paymentsList.stream()
                            .filter(p -> p.getPaymentId().equals(paymentDto.getPaymentId()))
                            .findFirst()
                            .orElse(null);

                    if (payment != null && paymentDto != null) {

                        var calculatedBalance = calculateBalance(payment, paymentDto);
                        sendToProcessBalance(calculatedBalance);

                    } else {
                        log.error("Payment not found for ID: " + paymentDto.getPaymentId());
                    }
                });
            } else {
                log.error("No payments found for the seller: " + seller.getId());
            }
        } else {
            log.error("Seller not found for ID: " + charge.getSellerId());
        }
        return charge;
    }

    private SellerEntity checkSeller(Long id) {
        log.info("Checking seller id: " + id);
        return sellerService.getById(id);
    }

    private List<PaymentEntity> checkPayments(List<Long> idsList, Long id) {
        log.info("Checking payments ids: " + idsList);
        return paymentService.findPaymentsBySellerId(idsList, id);
    }
    private PaymentEntity calculateBalance(PaymentEntity payment, PaymentsDto paymentDto) {
        var result =  payment.getBalance().add(paymentDto.getAmountPaid()) ;
        payment.setBalance(result);
        return payment;
    }
    private void sendToProcessBalance( PaymentEntity payment) {
        log.info("Sending Balance to Process!");
        String topic=null;
        if (null != payment.getBalance()) {
            if (payment.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                topic = "charge-total";
            } else if (payment.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                topic = "charge-partial";
            } else {
                topic = "charge-surplus";
            }
        }
        kafkaService.sendMessage(topic, payment);
    }
}
