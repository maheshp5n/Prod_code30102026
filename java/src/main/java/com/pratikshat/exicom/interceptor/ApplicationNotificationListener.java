package com.pratikshat.exicom.interceptor;

import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.services.PushNotificationRequest;
import com.pratikshat.exicom.services.PushNotificationService;

public class ApplicationNotificationListener {
	
	private final static Logger LOGGER=LogManager.getLogger(ApplicationNotificationDetails.class); 
	
	@PostPersist
	private void DoNotifyFCM(Object object) {
		
		ApplicationNotificationDetails d=(ApplicationNotificationDetails)object;
		if(!d.isNotificationRead()&&d.getUserEmailId()!=null&&!d.getUserEmailId().equalsIgnoreCase("")) {
			EntityManager em=BeanUtil.getBean(EntityManager.class);
			EmployeeDetails emp=em.find(EmployeeDetails.class, d.getUserEmailId());
			
			SimpMessagingTemplate template=BeanUtil.getBean(SimpMessagingTemplate.class);

			template.convertAndSend("/topic/"+d.getUserEmailId(), d);
			
			if(emp.getFcmToken()!=null&&emp.getDeviceId()!=null) {
				PushNotificationService pService=BeanUtil.getBean(PushNotificationService.class);
				PushNotificationRequest pReq=new PushNotificationRequest();
				pReq.setMessage(d.getNotificationMessage());
				pReq.setTitle("Exicom Notification");
				pReq.setToken(emp.getFcmToken());
				pService.sendPushNotificationToToken(pReq);
			}
		}
	}

}
