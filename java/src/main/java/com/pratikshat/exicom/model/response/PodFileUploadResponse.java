package com.pratikshat.exicom.model.response;

public class PodFileUploadResponse {
	
	private long fileId;
	private String fileName;
	
	
	
	
	public PodFileUploadResponse(long fileId, String fileName) {
		this.fileId = fileId;
		this.fileName = fileName;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
