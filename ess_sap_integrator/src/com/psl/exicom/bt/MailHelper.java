package com.psl.exicom.bt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailHelper implements Runnable {
  private String mailBody;
  
  private String subjectLine;
  
  private List<String> to;
  
  private String smtpUser;
  
  private String smtpHost;
  
  private int port;
  
  private String smtpPass;
  
  private boolean sslRequired;
  
  private boolean authRequired;
  
  private SqlHelper db = new SqlHelper("CRM");
  
  public MailHelper(String subjectLine, String mailBody, String to) {
    getSmtpUserAndPassword();
    this.subjectLine = subjectLine;
    this.mailBody = mailBody;
    if (this.to == null) {
      this.to = new ArrayList<>();
      this.to.add(to);
    } else {
      this.to.add(to);
    } 
  }
  
  public MailHelper(String subjectLine, String mailBody, List<String> tos) {
    getSmtpUserAndPassword();
    this.subjectLine = subjectLine;
    this.mailBody = mailBody;
    if (this.to == null) {
      this.to = tos;
    } else {
      for (String to : tos)
        this.to.add(to); 
    } 
  }
  
  public void run() {
    getSmtpUserAndPassword();
    sendEmail(getSession());
  }
  
  private void getSmtpUserAndPassword() {
    try {
      Map<String, Object> mailerEntity = this.db.getSingleResult("select * from mailer_entity where `active`=true");
      this.smtpPass = mailerEntity.get("default_password").toString();
      this.smtpUser = mailerEntity.get("default_user_name").toString();
      this.port = Integer.parseInt(mailerEntity.get("smtp_port").toString());
      this.smtpHost = mailerEntity.get("smtp_server").toString();
      this.authRequired = Boolean.parseBoolean(mailerEntity.get("required_authentication").toString());
      this.sslRequired = Boolean.parseBoolean(mailerEntity.get("required_ssl").toString());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void sendEmail(Session session) {
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom((Address)new InternetAddress(this.smtpUser));
      InternetAddress[] tos = new InternetAddress[this.to.size()];
      for (int i = 0; i < this.to.size(); i++) {
        System.out.println("Mail to user email id " + (String)this.to.get(i));
        tos[i] = new InternetAddress(this.to.get(i));
      } 
      message.addRecipients(MimeMessage.RecipientType.TO, (Address[])tos);
      message.setSubject(this.subjectLine);
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(this.mailBody, "text/html; charset=utf-8");
      MimeMultipart mimeMultipart = new MimeMultipart();
      mimeMultipart.addBodyPart((BodyPart)mimeBodyPart);
      message.setContent((Multipart)mimeMultipart);
      Transport.send((Message)message);
      System.out.println("Mail send");
      System.out.println("Send from " + this.smtpUser + " Logged by " + App.user);
      try {
        this.db.executeUpdate("insert into mail_log(log_by,log_date,mail_content,send_from,send_to,`subject`) values(?,?,?,?,?,?)", new Object[] { App.user, 
              new Date(), 
              this.mailBody, 
              this.smtpUser, 
              tos.toString(), 
              this.subjectLine });
        System.out.println("Log inserted to db");
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } catch (MessagingException e) {
      e.printStackTrace();
    } 
  }
  
  private Session getSession() {
    Authenticator auth = null;
    Properties prop = new Properties();
    prop.put("mail.smtp.host", this.smtpHost);
    prop.put("mail.smtp.socketFactory.port", Integer.valueOf(this.port));
    if (this.sslRequired)
      prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
    if (this.authRequired) {
      prop.put("mail.smtp.auth", Boolean.valueOf(true));
      auth = new MailAuthenticator(this.smtpUser, this.smtpPass);
      return Session.getDefaultInstance(prop, auth);
    } 
    return Session.getDefaultInstance(prop);
  }
}
