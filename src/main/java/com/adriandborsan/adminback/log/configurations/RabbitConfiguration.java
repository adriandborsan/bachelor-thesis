package com.adriandborsan.adminback.log.configurations;

import com.adriandborsan.adminback.log.documents.LogEntry;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfiguration {
    @Bean
    public Queue queue(@Value("${spring.rabbitmq.queue}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    public MessageConverter jsonMessageConverter(DefaultClassMapper classMapper) {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(classMapper);
        return jackson2JsonMessageConverter;
    }

    @Bean
    public DefaultClassMapper classMapper(@Value("${rabbit_class_name}") String className) {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put(className, LogEntry.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}
