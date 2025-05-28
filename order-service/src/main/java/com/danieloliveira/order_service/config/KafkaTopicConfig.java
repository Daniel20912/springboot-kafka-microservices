package com.danieloliveira.order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    // spring bean for kafka topic
    // Define um bean 'NewTopic' que o Spring for Kafka usará para criar ou atualizar o tópico 'order_topics' no cluster Kafka durante a inicialização da aplicação.
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName)
                // .partitions(3) // pode ser usado para criar e especificar quantas partições eu quero usar no tópico
                .build();
    }
}
