package com.pratikshat.exicom.model.response;

public class ObjectAccessResponse {
	long objectAccessId;
	long profileId;
	String objectName;
	String userProfileName;
	String objectFullUrl;
	String userProfileDescription;
	boolean viewRights;
	boolean viewAllRights;
	boolean createRights;
	boolean editRights;
	boolean deleteRights;
	
	String objectLableName;
	
	public ObjectAccessResponse() {}
	
	public ObjectAccessResponse(long objectAccessId, long profileId, String objectName, String userProfileName,
			String objectFullUrl, String userProfileDescription, boolean viewRights, boolean viewAllRights,
			boolean createRights, boolean editRights, boolean deleteRights) {
		
		this.objectAccessId = objectAccessId;
		this.profileId = profileId;
		this.objectName = objectName;
		this.userProfileName = userProfileName;
		this.objectFullUrl = objectFullUrl;
		this.userProfileDescription = userProfileDescription;
		this.viewRights = viewRights;
		this.viewAllRights = viewAllRights;
		this.createRights = createRights;
		this.editRights = editRights;
		this.deleteRights = deleteRights;
	}
	
	public ObjectAccessResponse(long objectAccessId, long profileId, String objectName, String userProfileName,
			String objectFullUrl, String userProfileDescription, boolean viewRights, boolean viewAllRights,
			boolean createRights, boolean editRights, boolean deleteRights,String objectLableName) {
		
		this.objectAccessId = objectAccessId;
		this.profileId = profileId;
		this.objectName = objectName;
		this.userProfileName = userProfileName;
		this.objectFullUrl = objectFullUrl;
		this.userProfileDescription = userProfileDescription;
		this.viewRights = viewRights;
		this.viewAllRights = viewAllRights;
		this.createRights = createRights;
		this.editRights = editRights;
		this.deleteRights = deleteRights;
		this.objectLableName=objectLableName;
	}
	public long getObjectAccessId() {
		return objectAccessId;
	}
	public void setObjectAccessId(long objectAccessId) {
		this.objectAccessId = objectAccessId;
	}
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getUserProfileName() {
		return userProfileName;
	}
	public void setUserProfileName(String userProfileName) {
		this.userProfileName = userProfileName;
	}
	public String getObjectFullUrl() {
		return objectFullUrl;
	}
	public void setObjectFullUrl(String objectFullUrl) {
		this.objectFullUrl = objectFullUrl;
	}
	public String getUserProfileDescription() {
		return userProfileDescription;
	}
	public void setUserProfileDescription(String userProfileDescription) {
		this.userProfileDescription = userProfileDescription;
	}
	public boolean isViewRights() {
		return viewRights;
	}
	public void setViewRights(boolean viewRights) {
		this.viewRights = viewRights;
	}
	public boolean isViewAllRights() {
		return viewAllRights;
	}
	public void setViewAllRights(boolean viewAllRights) {
		this.viewAllRights = viewAllRights;
	}
	public boolean isCreateRights() {
		return createRights;
	}
	public void setCreateRights(boolean createRights) {
		this.createRights = createRights;
	}
	public boolean isEditRights() {
		return editRights;
	}
	public void setEditRights(boolean editRights) {
		this.editRights = editRights;
	}
	public boolean isDeleteRights() {
		return deleteRights;
	}
	public void setDeleteRights(boolean deleteRights) {
		this.deleteRights = deleteRights;
	}

	public String getObjectLableName() {
		return objectLableName;
	}

	public void setObjectLableName(String objectLableName) {
		this.objectLableName = objectLableName;
	}
	
	
}
