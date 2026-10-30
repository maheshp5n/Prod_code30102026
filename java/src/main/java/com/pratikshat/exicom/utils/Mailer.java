package com.pratikshat.exicom.utils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;

import com.pratikshat.exicom.entity.MailLog;
import com.pratikshat.exicom.entity.MailLogAttachements;
import com.pratikshat.exicom.entity.MailerEntity;
import com.pratikshat.exicom.entity.MailerProperty;

public class Mailer {
	private EntityManager em;
	private MailerEntity mailerEntity;
	private List<MailerProperty> mailerProperty;
	private List<String> mailTo;
	private String mailBody;
	private String mailSubject;
	private String replyTo;
	private String loginUserName;
	
	public Mailer(EntityManager em,List<String>mailTo,String mailBody,String mailSubject,String replyTo,String loginUser) {
		this.em=em;
		this.mailTo=mailTo;
		this.mailBody=mailBody;
		this.mailSubject=mailSubject;
		this.replyTo=replyTo;
		this.loginUserName=loginUser;
		getMailerEntityAndProperty();
		sendEmail(getSession(),this.mailTo,this.mailBody,this.mailSubject,null,null);
	}
	public Mailer(EntityManager em,List<String>mailTo,String mailBody,String mailSubject,String replyTo,String loginUser,List<File> attachements) {
		this.em=em;
		this.mailTo=mailTo;
		this.mailBody=mailBody;
		this.mailSubject=mailSubject;
		this.replyTo=replyTo;
		this.loginUserName=loginUser;
		getMailerEntityAndProperty();
		sendEmail(getSession(),this.mailTo,this.mailBody,this.mailSubject,attachements,null);
	}
	
	public Mailer(EntityManager em,List<String>mailTo,String mailBody,String mailSubject,String replyTo,String loginUser,List<File> attachements,List<String>ccList) {
		this.em=em;
		this.mailTo=mailTo;
		this.mailBody=mailBody;
		this.mailSubject=mailSubject;
		this.replyTo=replyTo;
		this.loginUserName=loginUser;
		getMailerEntityAndProperty();
		sendEmail(getSession(),this.mailTo,this.mailBody,this.mailSubject,attachements,ccList);
	}
	
	public Mailer(EntityManager em,List<String>mailTo,String mailBody,String mailSubject,String replyTo,String loginUser,List<File> attachements,List<String>ccList, String objectName, String objectId) {
		this.em=em;
		this.mailTo=mailTo;
		this.mailBody=mailBody;
		this.mailSubject=mailSubject;
		this.replyTo=replyTo;
		this.loginUserName=loginUser;
		getMailerEntityAndProperty();
		sendEmail(getSession(),this.mailTo,this.mailBody,this.mailSubject,attachements,ccList, objectName, objectId);
	}
	
	private void getMailerEntityAndProperty() {
		mailerEntity=em.createQuery("select m from MailerEntity m where m.active=?1",MailerEntity.class).setParameter(1, true).getSingleResult();
		if(mailerEntity!=null) {
			mailerProperty=em.createQuery("select m from MailerProperty m where m.mailerEntity=?1",MailerProperty.class).setParameter(1,mailerEntity).getResultList();
		}
	}
	private Session getSession() {
		if(mailerEntity!=null) {
			Authenticator auth=null;
			Properties prop=new Properties();
			prop.put("mail.smtp.host", mailerEntity.getSmtpServer());
			if(mailerEntity.isRequiredSsl()) {
				prop.put("mail.smtp.socketFactory.port",mailerEntity.getSslPort());
				prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			}
			if(mailerEntity.isRequiredAuthentication()) {
				prop.put("mail.smtp.auth", true);
				auth = new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						
						return new PasswordAuthentication(mailerEntity.getDefaultUserName(), mailerEntity.getDefaultPassword());
					}
				};
			}
			if(mailerEntity.isRequiredTlsStart())
				prop.put("mail.smtp.starttls.enable", "true");
			if(mailerProperty!=null&&mailerProperty.size()>0) {
				for(MailerProperty pt:mailerProperty) {
					if(pt.isActive())
						prop.put(pt.getMailerPropertyName(), pt.getMailerpropertyValue());
				}
			}
			if(auth!=null) {
				return Session.getInstance(prop, auth);	
			}
			else {
				return Session.getInstance(prop);
			}
			
		}
		return null;
	}
	
	private void sendEmail(Session session,List<String>emailId,String message,String subject,List<File> attachement,List<String>ccList) {
		try {
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<emailId.size();i++) {
				if(i!=0)sb.append(";");
				sb.append(emailId.get(i));
			}
				new SenderThread(session,emailId,message,subject,attachement,ccList).start();
				
				MailLog log=new MailLog(this.loginUserName, new Date(), message, replyTo==null?mailerEntity.getDefaultUserName():replyTo, sb.toString());
				em.persist(log);
				if(attachement!=null&&attachement.size()>0) {
					for(File at:attachement) {
						MailLogAttachements atc=new MailLogAttachements(log, at.getAbsolutePath());
						em.persist(atc);
					}
				}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void sendEmail(Session session,List<String>emailId,String message,String subject,List<File> attachement,List<String>ccList, String objectName, String objectId) {
		try {
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<emailId.size();i++) {
				if(i!=0)sb.append(";");
				sb.append(emailId.get(i));
			}
			
			StringBuilder sbcc=new StringBuilder();
			for(int i=0;i<ccList.size();i++) {
				if(i!=0)sbcc.append(";");
				sbcc.append(ccList.get(i));
			}
				new SenderThread(session,emailId,message,subject,attachement,ccList).start();
				
				MailLog log=new MailLog(this.loginUserName, new Date(), message, replyTo==null?mailerEntity.getDefaultUserName():replyTo, sb.toString(), sbcc.toString(), subject, objectName, objectId);
				em.persist(log);
				if(attachement!=null&&attachement.size()>0) {
					for(File at:attachement) {
						MailLogAttachements atc=new MailLogAttachements(log, at.getAbsolutePath());
						em.persist(atc);
					}
				}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	public class SenderThread extends Thread{
		Session session;
		List<String>emailId;
		String message;
		String subject;
		List<File> attachement;
		List<String> ccList;
		
		public SenderThread(Session session,List<String>emailId,String message,String subject,List<File> attachement,List<String>ccList) {
			this.session=session;
			this.emailId=emailId;
			this.message=message;
			this.subject=subject;
			this.attachement=attachement;
			this.ccList=ccList;
		}
		public void run() {
			try {
				StringBuilder sb=new StringBuilder();
				MimeMessage msg=new MimeMessage(session);
				msg.setSubject(subject, "UTF-8");
				msg.setHeader("Content-type", "text/html");
				msg.setFrom(new InternetAddress(mailerEntity.getDefaultUserName(),true));
				msg.setReplyTo(InternetAddress.parse(replyTo==null?mailerEntity.getDefaultUserName():replyTo,false));
				InternetAddress[] tos=new InternetAddress[emailId.size()];
				for(int i=0;i<emailId.size();i++) {
					tos[i]=new InternetAddress(emailId.get(i),false);
					sb.append(emailId.get(i));
				}
				msg.setRecipients(Message.RecipientType.TO, tos);
				if(ccList!=null) {
					InternetAddress[] ccc=new InternetAddress[ccList.size()];
					for(int i=0;i<ccList.size();i++) {
						ccc[i]=new InternetAddress(ccList.get(i),false);
					}
					msg.setRecipients(Message.RecipientType.CC, ccc);	
				}
				
				if(attachement!=null&&attachement.size()>0) {
					BodyPart bodyPart=new MimeBodyPart();
					//bodyPart.setText(message);
					Multipart multipart=new MimeMultipart();
					multipart.addBodyPart(bodyPart);
					for(File filename:attachement) {
						BodyPart messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(filename);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(filename.getName());
						multipart.addBodyPart(messageBodyPart);
					}
					bodyPart.setContent(message,"text/html; charset=utf-8");
					msg.setContent(multipart);
				}
				else {
		
					msg.setContent(mailBody, "text/html; charset=utf-8");
				}
			
				Transport.send(msg);
			
			
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
