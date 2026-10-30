package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UINavigationModules implements java.io.Serializable,OldPresist<UINavigationModules>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long uiModulesId;
	String name;
	String url;
	String icon;
	boolean title;
	int orderNumber;
	@OneToMany(mappedBy = "parent",fetch=FetchType.LAZY)
	java.util.Set<UINavigationModules> children;
	String variant;
	
	@ManyToOne(fetch=FetchType.LAZY)
	UINavigationModules parent;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public UINavigationModules() {}
	public UINavigationModules(long id) {
		
		this.uiModulesId=id;
	}
	
	/**
	 * @return the uiModulesId
	 */
	public long getUiModulesId() {
		return uiModulesId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @return the title
	 */
	public boolean isTitle() {
		return title;
	}
	/**
	 * @return the children
	 */
	public java.util.Set<UINavigationModules> getChildren() {
		return children;
	}
	/**
	 * @return the variant
	 */
	public String getVariant() {
		return variant;
	}
	/**
	 * @param uiModulesId the uiModulesId to set
	 */
	public void setUiModulesId(long uiModulesId) {
		this.uiModulesId = uiModulesId;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(boolean title) {
		this.title = title;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(java.util.Set<UINavigationModules> children) {
		this.children = children;
	}
	/**
	 * @param variant the variant to set
	 */
	public void setVariant(String variant) {
		this.variant = variant;
	}
	/**
	 * @return the orderNumber
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @return the parent
	 */
	public UINavigationModules getParent() {
		return parent;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(UINavigationModules parent) {
		this.parent = parent;
	}
	
	

	@javax.persistence.Transient transient UINavigationModules old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UINavigationModules)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UINavigationModules gOld() {
		return old;
	}
	public String toString() {
			return ""+uiModulesId;
	}
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	
}
