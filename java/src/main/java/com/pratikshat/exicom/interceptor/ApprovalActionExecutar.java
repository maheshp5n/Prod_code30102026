package com.pratikshat.exicom.interceptor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pratikshat.exicom.config.ApprovalWorkFlowAction;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@Component
public class ApprovalActionExecutar {

	@Value("${app.workflow.classpath}")
	String classPath;
	
	public void executeAction(EntityManager em,Object id,LoginUser user,BtApprovalStapes stape,String actionName) throws BadRequestException,UnauthoriseException {
		try {
			URLClassLoader classLoader=null;
			try {
				 classLoader=URLClassLoader.newInstance(new URL[] {new File(stape.getClassLoaderUrl()).toURI().toURL()},this.getClass().getClassLoader());
				 Class classz=classLoader.loadClass(stape.getActionClassName());
				 ApprovalWorkFlowAction apr=(ApprovalWorkFlowAction)classz.newInstance();
				 if(actionName.equalsIgnoreCase("approved"))
					 apr.onApproval(stape, id, actionName, user, em);
			} 
			catch(Exception ex) {
				ex.printStackTrace();
				throw new BadRequestException(ex.getMessage());
			}
		}catch(Exception ext) {
			throw new BadRequestException(ext.getMessage());
		}
		
	}
}
