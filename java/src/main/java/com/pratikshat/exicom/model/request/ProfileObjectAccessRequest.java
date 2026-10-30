package com.pratikshat.exicom.model.request;

public class ProfileObjectAccessRequest {
	long objectAccessId;
	long profileId;
	String objectName;
	boolean createRights;
	boolean editRights;
	boolean viewRights;
	boolean viewAllRights;
	boolean deleteRights;
	
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
	public boolean isDeleteRights() {
		return deleteRights;
	}
	public void setDeleteRights(boolean deleteRights) {
		this.deleteRights = deleteRights;
	}
	
}
