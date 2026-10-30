package com.pratikshat.exicom.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomeClassLoader {

	
	@Value("${app.workflow.location}")
	String workflowpath;
	@Value("${app.bt.approvalflow.path}")
	String approvalpath;
	
	ClassLoader clsLoader;
	
	@PostConstruct
	public void init() {
		initLoadClass();
	}
	
	public Class getClass(String className) throws ClassNotFoundException {
		return this.clsLoader.loadClass(className);
	}
	public <T> T loadClass(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		return (T)this.clsLoader.loadClass(className).newInstance();
		
	}

	public void reload(){
		initLoadClass();
	}

	private void initLoadClass(){
		try {
			clsLoader=URLClassLoader.newInstance(new URL[] {new File(workflowpath).toURI().toURL(),new File(approvalpath).toURI().toURL()}, this.getClass().getClassLoader());
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
	}

	
}
