package com.pranit.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaProducerExample {

	public static void main(String[] args) {
		
		String topicName = "testTopic";
		
		Producer<String, String> producer = createProducer();
		
		ProducerRecord<String, String> record;
		
		//Fire and Forget
		for (int i = 0; i < 10; i++) {
			record = new ProducerRecord<String, String>(topicName, "Key-"+i, "value-"+i);
			System.out.println(producer.send(record));
		}
		
		//Sync Send
		double keyRandomNumber = Math.random();
		try {
			record = new ProducerRecord<String, String>(topicName, "Key-"+keyRandomNumber, "value-"+keyRandomNumber);
			RecordMetadata metadata = producer.send(record).get();
			System.out.println("Sync Send Succesful. Partion No. - " + metadata.partition() + " and offset - " + metadata.offset());
		} catch (Exception e) {
			System.out.println("Sync Send Failed.");
			e.printStackTrace();
		}
		
		//Async Send
		keyRandomNumber = Math.random();
		record = new ProducerRecord<String, String>(topicName, "Key-"+keyRandomNumber, "value-"+keyRandomNumber);
		producer.send(record, new ProducerCallback(record.key()));
		
		producer.close();
	}

	private static Producer<String, String> createProducer() {
		Properties props = new Properties();
		
		props.setProperty("bootstrap.servers", "localhost:9092,localhost:9093");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> producer = new KafkaProducer<>(props);
		return producer;
	}

}

class ProducerCallback implements Callback{
	
	private String messageKey;
	
	public ProducerCallback(String messageKey) {
		this.messageKey = messageKey;
	}

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if(exception != null) {
			System.out.println("Async Send Falied for " + this.messageKey);
			exception.printStackTrace();
		}
		
		System.out.println("Async Send Successful for " + this.messageKey);
	}
}
