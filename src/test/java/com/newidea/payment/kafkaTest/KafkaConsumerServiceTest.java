package com.newidea.payment.kafkaTest;

import com.newidea.payment.kafka.KafkaConsumerService;
import com.newidea.payment.service.PaymentService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(
        topics = "charge-total",
        partitions = 1,
        bootstrapServersProperty = "kafka.bootstrap-servers"
)
public class KafkaConsumerServiceTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @MockBean // This annotation will replace the real PaymentService with a mock
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        // Reset mock before each test
        Mockito.reset(paymentService);
    }

    @Test
    @DisplayName("Teste do consumidor de mensagem")
    void testConsumerMessage() {
        // Simulate sending a message to the topic
        String message = "{\"paymentId\":1,\"paymentStatus\":\"PENDENTE\"}";
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(producerProps)) {
            kafkaProducer.send(new ProducerRecord<>("charge-total", message));
            kafkaProducer.flush();
        }

        // Process the received message
        kafkaConsumerService.consumerMessage1(new ConsumerRecord<>("charge-total", 0, 0, "key", message));

        // Assert that the updatePayment method was called with the expected PaymentEntity
        verify(paymentService, times(1)).updatePayment(argThat(argument -> argument.getPaymentId() == 1 && argument.getPaymentStatus().equals("TOTAL")));
    }
}
