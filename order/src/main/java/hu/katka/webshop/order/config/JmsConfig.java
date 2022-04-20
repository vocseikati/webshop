package hu.katka.webshop.order.config;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.apache.activemq.broker.BrokerService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JmsConfig {

  @Bean
  public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(objectMapper);
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  public JmsTemplate jmsTemplate(ObjectMapper objectMapper, ConnectionFactory connectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(connectionFactory);
    jmsTemplate.setMessageConverter(jacksonJmsMessageConverter(objectMapper));
    jmsTemplate.setPubSubDomain(true);
    return jmsTemplate;
  }

  @Bean
  public BrokerService broker() throws Exception {
    BrokerService brokerService = new BrokerService();
    brokerService.addConnector("tcp://localhost:9998");
    return brokerService;
  }
}
