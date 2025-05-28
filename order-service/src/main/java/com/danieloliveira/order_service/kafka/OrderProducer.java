package com.danieloliveira.order_service.kafka;

import com.danieloliveira.base_domains.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderEvent event) {
        // 1. Registra o evento de pedido no log, convertendo o objeto 'event' para String.
        LOGGER.info("Order event => {}", event.toString());

        // 2. Constrói a mensagem Kafka.
        // 'MessageBuilder' cria uma mensagem encapsulando o payload e metadados.
        Message<OrderEvent> message = MessageBuilder
                // 3. Define o conteúdo principal (payload) da mensagem como o objeto 'event' (OrderEvent).
                .withPayload(event)
                // 4. Define um cabeçalho Kafka para especificar o tópico de destino da mensagem.
                // O nome do tópico é obtido do bean 'NewTopic' injetado (topic.name()).
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                // 5. Finaliza a construção do objeto 'Message'.
                .build();

        // 6. Envia a mensagem construída para o Kafka.
        // O 'kafkaTemplate' usa os serializadores configurados (StringSerializer para chave, JsonSerializer para valor)
        // para converter a mensagem em bytes antes de publicá-la no tópico especificado no cabeçalho.
        kafkaTemplate.send(message);
    }
}
