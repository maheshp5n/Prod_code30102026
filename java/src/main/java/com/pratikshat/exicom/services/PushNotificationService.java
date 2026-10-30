package com.pratikshat.exicom.services;

import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
	
	private Logger logger = LogManager.getLogger(PushNotificationService.class);
	
	@Autowired
	FCMService fcmService;
	
	public void sendPushNotificationToToken(PushNotificationRequest request) {
		Thread th=new Thread(()-> {
			 try {
		            fcmService.sendMessageToToken(request);
		        } catch (InterruptedException | ExecutionException e) {
		        	e.printStackTrace();
		            logger.error(e.getMessage());
		        }
		});
		th.start();
    }
}
