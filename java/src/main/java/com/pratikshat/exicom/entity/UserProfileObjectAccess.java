package com.pratikshat.exicom.entity;
@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UserProfileObjectAccess implements java.io.Serializable,OldPresist<UserProfileObjectAccess>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)
	long id;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	ObjectDefinition object;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	UserProfile userProfile;
	boolean viewRights;
	boolean viewAllRights;
	boolean createRights;
	boolean editRights;
	boolean deleteRights;
	
	public UserProfileObjectAccess() {}
	
	public UserProfileObjectAccess(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ObjectDefinition getObject() {
		return object;
	}
	public void setObject(ObjectDefinition object) {
		this.object = object;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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

	public UserProfileObjectAccess(long id, ObjectDefinition object, UserProfile userProfile, boolean viewRights,
			boolean viewAllRights, boolean createRights, boolean editRights, boolean deleteRights) {
		
		this.id = id;
		this.object = object;
		this.userProfile = userProfile;
		this.viewRights = viewRights;
		this.viewAllRights = viewAllRights;
		this.createRights = createRights;
		this.editRights = editRights;
		this.deleteRights = deleteRights;
	}
	
	
	

	@javax.persistence.Transient transient UserProfileObjectAccess old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UserProfileObjectAccess)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UserProfileObjectAccess gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
