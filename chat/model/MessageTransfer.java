package fr.chat.model;

public class MessageTransfer {
	private String sender_username;
	private int recipient_id;
	private int group_id;
	private String  content;
	private boolean is_private;
	
	public MessageTransfer(String sender_username, String content) {
		super();
		this.sender_username = sender_username;
		this.content = content;
	}
	
	public String getSender_username() {
		return sender_username;
	}
	
	public void setSender_username(String sender_username) {
		this.sender_username = sender_username;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}