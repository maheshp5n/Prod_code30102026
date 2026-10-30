package com.pratikshat.exicom.model.response;

public class UINavigationModuleResponse {
	long uiModulesId;
	String name;
	String url;
	String icon;
	boolean title;
	String variant;
	long parentId;
	public UINavigationModuleResponse() {}
	public UINavigationModuleResponse(com.pratikshat.exicom.entity.UINavigationModules module) {
		this.uiModulesId=module.getUiModulesId();
		this.name=module.getName();
		this.url=module.getUrl();
		this.icon=module.getIcon();
		this.title=module.isTitle();
		this.variant=module.getVariant();
		if(module.getParent()!=null) {
			this.parentId=module.getParent().getUiModulesId();
		}
	}
	
	public UINavigationModuleResponse(long uiModulesId, String name, String url, String icon, boolean title,
			String variant,long parentId) {
		this.uiModulesId = uiModulesId;
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.title = title;
		this.variant = variant;
		this.parentId=parentId;
	}
	public long getUiModulesId() {
		return uiModulesId;
	}
	public void setUiModulesId(long uiModulesId) {
		this.uiModulesId = uiModulesId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isTitle() {
		return title;
	}
	public void setTitle(boolean title) {
		this.title = title;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
}
