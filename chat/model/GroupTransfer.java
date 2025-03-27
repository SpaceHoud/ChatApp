package fr.chat.model;

public class GroupTransfer {
	private String groupName;
	private String creatorName;
	
	public GroupTransfer(String groupName, String creatorName) {
		super();
		this.groupName = groupName;
		this.creatorName = creatorName;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getCreatorName() {
		return creatorName;
	}
	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
