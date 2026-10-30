package com.pratikshat.exicom.model.request;

public class ProfileRequest {
	long profileId;
	String profileName;
	String profileDescription;
	java.util.List<ProfileObjectAccessRequest> objectAccessRights;
	java.util.List<Long> uiId;
	long defaultpage;
	
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getProfileDescription() {
		return profileDescription;
	}
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	public java.util.List<ProfileObjectAccessRequest> getObjectAccessRights() {
		return objectAccessRights;
	}
	public void setObjectAccessRights(java.util.List<ProfileObjectAccessRequest> objectAccessRights) {
		this.objectAccessRights = objectAccessRights;
	}
	public java.util.List<Long> getUiId() {
		return uiId;
	}
	public void setUiId(java.util.List<Long> uiId) {
		this.uiId = uiId;
	}
	public long getDefaultpage() {
		return defaultpage;
	}
	public void setDefaultpage(long defaultpage) {
		this.defaultpage = defaultpage;
	}
	
}
