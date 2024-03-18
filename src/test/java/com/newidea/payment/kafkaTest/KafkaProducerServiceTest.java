package com.newidea.payment.kafkaTest;

import com.newidea.payment.kafka.KafkaProducerService;
import com.newidea.payment.persistence.entity.PaymentEntity;
import com.newidea.payment.persistence.entity.SellerEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(
        topics = "charge-total",
        partitions = 1,
        bootstrapServersProperty = "kafka.bootstrap.servers"
)
public class KafkaProducerServiceTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private KafkaProducerService producer;

    private PaymentEntity payment;

    @BeforeEach
    public void setup() {
        SellerEntity seller = new SellerEntity();

        PaymentEntity payment = new PaymentEntity();
        // Given
        seller = new SellerEntity();
        seller.setId(1L);

        payment = new PaymentEntity();
        payment.setPaymentId(1L);
        payment.setPaymentStatus("PENDENTE");
        payment.setBalance(new BigDecimal(10.00));
        payment.setSeller(seller);
    }

    @Test
    @DisplayName("Deve enviar uma mensagem para o tópico 'charge-total'")
    void testSendMessageToChargeTotalTopic() {
        // Enviar a mensagem para o produtor
        producer.sendMessage("charge-total", payment);

        // Verificar se a mensagem foi enviada para o tópico
        assertTrue(broker.getTopics().contains("charge-total"));
    }





}
