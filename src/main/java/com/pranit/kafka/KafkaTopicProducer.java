package com.pranit.kafka;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.pranit.kafka.dto.KafkaMessage;
import com.pranit.kafka.dto.TestMessage;
import com.pranit.kafka.util.Topics;

@Component
public class KafkaTopicProducer {
	
	private static final String TOPIC = "testTopic";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, KafkaMessage> kafkaTemplateJSON;
	
	@PostConstruct
	public void produceMessages() {
		
		new Thread() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					kafkaTemplate.send(TOPIC ,String.valueOf(i) , "This is an automated message no. "+ i);
					kafkaTemplateJSON.send(Topics.TOPIC_CUSTOM.name() ,String.valueOf(i) , new TestMessage("This is an automated message no. "+ i, i));
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}.start();
	}
}
