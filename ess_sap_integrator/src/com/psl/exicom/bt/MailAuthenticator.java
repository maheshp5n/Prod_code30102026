package com.psl.exicom.bt;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
  String userName;
  
  String passWord;
  
  public MailAuthenticator(String username, String userpassword) {
    this.userName = username;
    this.passWord = userpassword;
  }
  
  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(this.userName, this.passWord);
  }
}
