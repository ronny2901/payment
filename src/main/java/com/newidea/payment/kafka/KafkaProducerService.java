package com.newidea.payment.kafka;

import com.newidea.payment.persistence.entity.PaymentEntity;
import com.newidea.payment.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, PaymentEntity payment) {
        try {
            String json = GsonUtil.toJson(payment);

            kafkaTemplate.send(topic, json);
        } catch (Exception e) {
            log.error("Falha ao realizar envio de mensagem ao topico {}", topic, e);
        }

    }
}
