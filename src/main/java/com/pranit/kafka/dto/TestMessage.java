package com.pranit.kafka.dto;

public class TestMessage extends KafkaMessage{

	private String message;
	private int messageId;
	
	public TestMessage() {}
	
	public TestMessage(String message, int messageId) {
		super();
		this.message = message;
		this.messageId = messageId;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "TestMessage [message=" + message + ", messageId=" + messageId + "]";
	}
	
}
