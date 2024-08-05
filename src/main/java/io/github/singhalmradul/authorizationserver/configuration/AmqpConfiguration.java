package io.github.singhalmradul.authorizationserver.configuration;

import static io.github.singhalmradul.authorizationserver.services.AmpqService.EXCHANGE_NAME;
import static io.github.singhalmradul.authorizationserver.services.AmpqService.QUEUE_NAME;
import static io.github.singhalmradul.authorizationserver.services.AmpqService.ROUTING_KEY;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AmqpConfiguration {

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
		rabbitTemplate.setChannelTransacted(true);
		return rabbitTemplate;
	}
}
