package com.pratikshat.exicom.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

@WebListener
public class CustomeHttpSessionListener implements HttpSessionListener{
	@Autowired
	com.pratikshat.exicom.repository.UserLoginUpdateRepository dbUpdate;
	
	public void sessionCreated(HttpSessionEvent event) {
		
	}
	public void sessionDestroyed(HttpSessionEvent event) {
		//dbUpdate.updateActiveByLastSessionId(false, event.getSession().getId());
	}
}
