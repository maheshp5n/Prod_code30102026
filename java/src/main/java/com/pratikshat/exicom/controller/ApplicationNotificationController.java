package com.pratikshat.exicom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.repository.ApplicationNotificationDetailsRepository;

@RestController
@RequestMapping("/api/notification")
public class ApplicationNotificationController {
	@Autowired
	private ApplicationNotificationDetailsRepository dbNotification;
	
	@GetMapping
	public Page<ApplicationNotificationDetails> getNotification(@CurrentUser LoginUser user,Pageable page){
		return dbNotification.findByUserEmailIdOrderByNofificationDateDesc(user.getUsername(), page);
	}
	@GetMapping("{id}")
	public ApplicationNotificationDetails getNotification(@CurrentUser LoginUser user,@PathVariable("id")long id) {
		ApplicationNotificationDetails details=dbNotification.findByUserEmailIdAndNotificationId(user.getUsername(),id);
		if(details!=null) {
			details.setNotificationRead(true);
			dbNotification.save(details);	
		}
		return details;
	}
	
	@PostMapping
	public ResponseEntity<?> readAllNotification(@CurrentUser LoginUser user){
		dbNotification.updateAllRead(user.getUsername(), true);
		return ResponseEntity.ok("{\"ok\":1}");
	}
	
	@PostMapping("/demo/nofify")
	public ResponseEntity<?> dummy(@RequestBody ApplicationNotificationDetails nd){
		dbNotification.save(nd);
		return ResponseEntity.ok("OK");
	}
}
