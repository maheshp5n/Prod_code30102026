package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.ForgotPass;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.utils.Mailer;
import com.pratikshat.exicom.utils.SmsNotifier;

import static com.pratikshat.exicom.config.MiscellaneousUtils.*;
public class ForgetPassServiecImpl implements ForgetPassServiec {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	BCryptPasswordEncoder ency;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class,SQLException.class})
	public void sendOtp(String userEmailId) throws BadRequestException {
		
		LoginUser user=null;
		ContactDetails contact=null;
		try {
			
			user=em.createQuery("select usr from LoginUser usr where usr.username=?1",LoginUser.class).setParameter(1, userEmailId).getSingleResult();	
			
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Invalid Request");
		}
		try {
			contact =em.createQuery("select cont from ContactDetails cont where cont.emailId=?1",ContactDetails.class).setParameter(1, userEmailId).getSingleResult();	
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Invalid Request");
		}
		
		
		if(!user.isEnabled())
			throw new BadRequestException("Invalid Request");
		
		String randomToken=genOtp(4);
		ForgotPass forgotPass=null;
		try {
			forgotPass=em.createQuery("select fc from ForgotPass fc where fc.userEmailId=?1",ForgotPass.class).setParameter(1, userEmailId).getSingleResult();
		}catch(NoResultException ex) {
			forgotPass=new ForgotPass();
		}
		forgotPass.setLastModifiedDate(new Date());
		forgotPass.setMailSend(false);
		forgotPass.setUserEmailId(userEmailId);
		forgotPass.setOtp(ency.encode(randomToken+"PSL"));
		forgotPass.setOtpVerified(false);
		forgotPass.setOtpSend(true);
		
		
		TemplateDetails	templateDetails = null;
		
		try {
			Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
			templateQuery.setParameter(1,true);
			templateQuery.setParameter(2,TemplateType.SMS);
			templateQuery.setParameter(3,new BusinessDomain(2));
			templateQuery.setParameter(4,"forgot_password_otp");

			templateDetails = (TemplateDetails) templateQuery.getSingleResult();
		}catch(NoResultException nex) {
			nex.printStackTrace();
		}catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		
		String smsMessage = null;
		if(templateDetails!=null) {
			smsMessage = templateDetails.getBody();
			smsMessage = smsMessage.replaceFirst("<var>", randomToken);
		}
		
		if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
			SmsNotifier sNotifier = new SmsNotifier(em, contact.getMobile(),smsMessage, userEmailId,templateDetails.getTemplaeCode());
			sNotifier.sendNotification();
		}
		em.persist(forgotPass);
	}
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class,SQLException.class})
	public void setMailPass(String emailId, String otp) throws BadRequestException {
		ForgotPass fpass=null;
		LoginUser user=null;
		try {
			fpass=em.createQuery("select f from ForgotPass f where f.userEmailId=?1",ForgotPass.class).setParameter(1, emailId).getSingleResult();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Invalid Request");
		}
		try {
			user=em.createQuery("select usr from LoginUser usr where usr.username=?1",LoginUser.class).setParameter(1, emailId).getSingleResult();	
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Invalid Request");
		}
		if(!ency.matches(otp+"PSL", fpass.getOtp())) {
			throw new BadRequestException("Invalid Request");
		}
		
		if(fpass.isOtpVerified())
			throw new BadRequestException("Invalid Request");
		fpass.setOtpVerified(true);
		
		String randomPass=String.valueOf(randomToken(12)).toString();
		System.out.println("Password Checking --->" + randomPass);
		
		
		user.setPassword(ency.encode(randomPass));
		em.persist(user);
		em.persist(fpass);
		StringBuilder sb=new StringBuilder();
		sb.append("<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">Dear User, <o:p></o:p></span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">&nbsp;</span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">We have received your\r\n" + 
				"request for the new Password. <o:p></o:p></span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">&nbsp;</span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">&nbsp;</span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">Username&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n" + 
				"<a href=\"mailto:abc@xxx.com\">"+emailId+"</a><o:p></o:p></span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">Password :\r\n" + 
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+randomPass+"<o:p></o:p></span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">&nbsp;</span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">You are requested to change\r\n" + 
				"the password on your successful login.<o:p></o:p></span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">&nbsp;</span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">Regards,<o:p></o:p></span></p>\r\n" + 
				"\r\n" + 
				"<p class=\"MsoNormal\"><span style=\"mso-ascii-font-family:Calibri;mso-hansi-font-family:\r\n" + 
				"Calibri;mso-bidi-font-family:Calibri;color:#1F497D\">Team Pulse<o:p></o:p></span></p>");
		String mailBody=sb.toString();//"Your password is "+randomPass;
		List<String> mailTo=new ArrayList<String>();
		mailTo.add(emailId);
		Mailer mailer=new Mailer(em,mailTo,mailBody,"Forgot Password",emailId,emailId);
		
		fpass.setMailSend(true);
		em.persist(fpass);
	}
	
	
}
