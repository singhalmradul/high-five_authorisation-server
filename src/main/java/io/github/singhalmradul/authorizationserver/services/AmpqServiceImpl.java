package io.github.singhalmradul.authorizationserver.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
@Service
public class AmpqServiceImpl implements AmpqService {

	private RabbitTemplate rabbitTemplate;

	@Override
	public void sendMessage(Object message) {

		log.info("Sending message: {}", message);

		rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
	}

}
