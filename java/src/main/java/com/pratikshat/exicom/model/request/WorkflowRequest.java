package com.pratikshat.exicom.model.request;

public class WorkflowRequest {
	Long id;
	String object;
	String onEntityManager;
	String description;
	String dmlCommand;
	String on;
	
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	
	public String getOnEntityManager() {
		return onEntityManager;
	}
	public void setOnEntityManager(String onEntityManager) {
		this.onEntityManager = onEntityManager;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDmlCommand() {
		return dmlCommand;
	}
	public void setDmlCommand(String dmlCommand) {
		this.dmlCommand = dmlCommand;
	}
	public String getOn() {
		return on;
	}
	public void setOn(String on) {
		this.on = on;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
