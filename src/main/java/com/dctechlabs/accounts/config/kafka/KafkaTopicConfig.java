package com.dctechlabs.accounts.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic accountsTopic() {
       return new NewTopic("accounts", 3, (short) 1);
//        return TopicBuilder.name("accounts").build();
    }
}
