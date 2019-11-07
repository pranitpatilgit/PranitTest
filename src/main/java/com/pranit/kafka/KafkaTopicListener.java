package com.pranit.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.pranit.kafka.dto.KafkaMessage;
import com.pranit.kafka.dto.TestMessage;

@Component
public class KafkaTopicListener {

	
	@KafkaListener(topics = "testTopic", groupId = "G1")
	public void listen(
			@Payload String message, 
			@Header(value = KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		/*System.out.println(message);
		System.out.println(partition);
		System.out.println("------------------");*/
	}
	
	@KafkaListener(containerFactory = "kafkaListenerContainerFactoryJSON", topics = "TOPIC_CUSTOM", groupId = "G2")
	public void listenOnCustomTopic(
			@Payload KafkaMessage message,
			@Header(value = KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		
		System.out.println((TestMessage) message);
		System.out.println(partition);
		System.out.println("------------------");
	}
}

