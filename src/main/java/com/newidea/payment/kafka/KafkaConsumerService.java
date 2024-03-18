package com.newidea.payment.kafka;

import com.newidea.payment.persistence.entity.PaymentEntity;
import com.newidea.payment.service.PaymentService;
import com.newidea.payment.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private PaymentService service;

    @KafkaListener(topics = "charge-total", groupId = "req-charge")
    public void consumerMessage1(ConsumerRecord<String, String> messageReceived) {

        PaymentEntity payment = GsonUtil.fromJson(messageReceived.value(), PaymentEntity.class);
        payment.setPaymentStatus("TOTAL");

        service.updatePayment(payment);
    }

    @KafkaListener(topics = "charge-partial", groupId = "req-charge")
    public void consumerMessage2(ConsumerRecord<String, String> messageReceived) {

        PaymentEntity payment = GsonUtil.fromJson(messageReceived.value(), PaymentEntity.class);
        payment.setPaymentStatus("PARTIAL");

        service.updatePayment(payment);
    }

    @KafkaListener(topics = "charge-surplus", groupId = "req-charge")
    public void consumerMessage3(ConsumerRecord<String, String> messageReceived) {

        PaymentEntity payment = GsonUtil.fromJson(messageReceived.value(), PaymentEntity.class);
        payment.setPaymentStatus("SURPLUS");

        service.updatePayment(payment);
    }
}
