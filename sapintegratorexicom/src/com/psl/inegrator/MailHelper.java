package com.psl.inegrator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailHelper implements Runnable{

	private String mailBody;
	private String subjectLine;
	private List<String> to;
	
	private String smtpUser;
	private String smtpHost;
	private int port;
	private String smtpPass;
	private boolean sslRequired;
	private boolean authRequired;
	private SqlHelper db=new SqlHelper("CRM");
	
	public MailHelper(String subjectLine,String mailBody,String to) {
		getSmtpUserAndPassword();
		this.subjectLine=subjectLine;
		this.mailBody=mailBody;
		if(this.to==null) {
			this.to=new ArrayList<String>();
			this.to.add(to);
		}
		else {
			this.to.add(to);
		}
	}
	public MailHelper(String subjectLine,String mailBody,List<String>tos) {
		getSmtpUserAndPassword();
		this.subjectLine=subjectLine;
		this.mailBody=mailBody;
		if(this.to==null) {
			this.to=tos;
		}
		else {
			for(String to:tos) {
				this.to.add(to);
			}
		}
	}
	
	@Override
	public void run() {
		
		getSmtpUserAndPassword();		
		sendEmail(getSession());
		
	}
	
	private void getSmtpUserAndPassword() {
		try {
			Map<String,Object> mailerEntity= db.getSingleResult("select * from mailer_entity where `active`=true");
			smtpPass=mailerEntity.get("default_password").toString();
			smtpUser=mailerEntity.get("default_user_name").toString();
			port=Integer.parseInt(mailerEntity.get("smtp_port").toString());
			smtpHost=mailerEntity.get("smtp_server").toString();
			this.authRequired=Boolean.parseBoolean(mailerEntity.get("required_authentication").toString());
			this.sslRequired=Boolean.parseBoolean(mailerEntity.get("required_ssl").toString());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	private void sendEmail(Session session) {
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(smtpUser));
			InternetAddress[] tos=new InternetAddress[to.size()];
			for(int i=0;i<to.size();i++) {
				System.out.println("Mail to user email id "+to.get(i));
				tos[i]= new InternetAddress(to.get(i));
			}
			
			message.addRecipients(RecipientType.TO, tos);
			message.setSubject(subjectLine);
			BodyPart bodyPart=new MimeBodyPart();
			
			bodyPart.setContent(mailBody,"text/html; charset=utf-8");
			
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
			
			System.out.println("Mail send");
			System.out.println("Send from "+this.smtpUser+" Logged by "+App.user);
			
			try {
				db.executeUpdate("insert into mail_log(log_by,log_date,mail_content,send_from,send_to,`subject`) values(?,?,?,?,?,?)", new Object[] {
					App.user,
					new Date(),
					mailBody,					
					smtpUser,
					tos.toString(),
					subjectLine
					
				});
				System.out.println("Log inserted to db");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Session getSession() {
		Authenticator auth=null;
		Properties prop=new Properties();
		prop.put("mail.smtp.host",smtpHost);
		prop.put("mail.smtp.socketFactory.port",port);
		
		if(this.sslRequired)
			prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		if(this.authRequired) {
			prop.put("mail.smtp.auth", true);
			auth=new MailAuthenticator(this.smtpUser, this.smtpPass);
			return Session.getDefaultInstance(prop, auth);
		}
		else {
			return Session.getDefaultInstance(prop);
		}
		
		
	}

}
