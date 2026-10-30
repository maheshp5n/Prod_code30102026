package com.pratikshat.exicom.config;

public class FileStorageProperties {

	private String uploadDir;

    public FileStorageProperties(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
