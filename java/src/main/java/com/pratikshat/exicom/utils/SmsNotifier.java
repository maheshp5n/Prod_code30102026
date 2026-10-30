package com.pratikshat.exicom.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.pratikshat.exicom.entity.SmsLog;
import com.pratikshat.exicom.entity.SmsSetting;
import com.pratikshat.exicom.entity.SmsSettingHeaders;
import com.pratikshat.exicom.entity.SmsSettingParams;

public class SmsNotifier {
	private String mobileNumber;
	private String message;
	private EntityManager entityManager;
//	private String messagingUrl="http://onex-ultimo.in/api/pushsms?user=Exicom2019&authkey=92DhnvBR183E&sender=exichg&mobile=%s&text=%s&rpt=1";
//	http://onex-ultimo.in/api/pushsms?user=Exicom2019&authkey=92l5MK3SZjFJk&sender=Exichg&mobile=987k45632&text=Dear%20User%5CnCase%20No%20BTCASE-20%20has%20been%20logged%20with%20below%20details%3A%5CnSite%20ID%20BTST-2222%5CnSerial%20No%20BT_AST_3104%5CnProblem%20Reported%20Battery%20not%20discharging%5Cn%5CnR%27s%20Exicom&templateid=1007730093718534252&rpt=1
	private SmsSetting smsSetting;
	private List<SmsSettingHeaders> headers;
	private List<SmsSettingParams> params;
	private String userEmailId;
	private String smsTemplateNumber;
	
	public SmsNotifier() {
		
	}
	public SmsNotifier(EntityManager entityManager) {
		this.entityManager=entityManager;
	}
	public SmsNotifier(EntityManager entityManager,String mobileNumber,String message,String emailId) {
		this.mobileNumber=mobileNumber;
		this.entityManager=entityManager;
		this.message=message;
		this.userEmailId=emailId;
		
	}
	
	public SmsNotifier(EntityManager entityManager,String mobileNumber,String message,String emailId,String smsTemplateNumber) {
		this.mobileNumber=mobileNumber;
		this.entityManager=entityManager;
		this.message=message;
		this.userEmailId=emailId;
		this.smsTemplateNumber=smsTemplateNumber;
	}
	
	public SmsNotifier(String mobileNumber,String message,String templateId) {
		this.mobileNumber=mobileNumber;
		this.message=message;
		this.smsTemplateNumber=templateId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public SmsSetting getSmsSetting() {
		return smsSetting;
	}
	public void setSmsSetting(SmsSetting smsSetting) {
		this.smsSetting = smsSetting;
	}
	public List<SmsSettingHeaders> getHeaders() {
		return headers;
	}
	public void setHeaders(List<SmsSettingHeaders> headers) {
		this.headers = headers;
	}
	public List<SmsSettingParams> getParams() {
		return params;
	}
	public void setParams(List<SmsSettingParams> params) {
		this.params = params;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public void sendNotification() {
		getSmsDetails();
			StringBuilder responseString=new StringBuilder();
			try {
				java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("yyyMMdd");
				//String url=String.format(messagingUrl,mobileNumber,java.net.URLEncoder.encode(message,"UTF-8")) ;
				
				java.net.HttpURLConnection http=(java.net.HttpURLConnection)new java.net.URL(getURL()).openConnection();
				http.setRequestMethod(this.smsSetting.getMethodName());
				if(this.headers!=null&&this.headers.size()>0) {
					for(SmsSettingHeaders header:headers)
					http.setRequestProperty(header.getKeyHeader(),header.getValueHeader());
				}
				if(this.smsSetting.getContentType()!=null)
					http.setRequestProperty("Content-Type", this.smsSetting.getContentType());
				if(this.smsSetting.getMethodName().equalsIgnoreCase("post")) {
					http.setDoOutput(true);
					String body=prepareBody();
					http.setRequestProperty("Content-Length", ""+body.length());
					http.setUseCaches(false);
					DataOutputStream out=new DataOutputStream(http.getOutputStream());
					out.write(body.getBytes());
					out.flush();
					out.close();
				}
				
				int response =http.getResponseCode();
				if(response==200) {
					BufferedReader br=new BufferedReader(new InputStreamReader(http.getInputStream()));
					String line;
					while((line=br.readLine())!=null) {
						responseString.append(line);
					}
				}
				else {
					BufferedReader br=new BufferedReader(new InputStreamReader(http.getErrorStream()));
					String line;
					while((line=br.readLine())!=null) {
						responseString.append(line);
					}
				}
				try {
					SmsLog log=new SmsLog(this.mobileNumber, message, this.userEmailId, response, responseString.toString(),new Date());
					entityManager.persist(log);	
					
				}catch(Exception _logException) {
					_logException.printStackTrace();
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
	}
	private void getSmsDetails() {
		try {
			this.smsSetting=entityManager.createQuery("select sms from SmsSetting sms where sms.active=?1",SmsSetting.class).setParameter(1, true).getSingleResult();
			if(this.smsSetting!=null) {
				 this.headers=entityManager.createQuery("select headers from SmsSettingHeaders headers where headers.smsSetting=?1",SmsSettingHeaders.class).setParameter(1,this.smsSetting).getResultList();
				 this.params=entityManager.createQuery("select param from SmsSettingParams param where param.smsSetting=?1",SmsSettingParams.class).setParameter(1, this.smsSetting).getResultList();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private String getURL() {
		StringBuilder builder=new StringBuilder();
		
		if(this.smsSetting.getMethodName().equalsIgnoreCase("GET")) {
			builder.append(this.smsSetting.getUrl().trim());
			try {
				builder.append(prepareBody());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(builder.toString());
		return builder.toString();
	}
	private String prepareBody() throws Exception{
		StringBuilder builder=new StringBuilder();
		if(this.smsSetting.getMethodName().equalsIgnoreCase("GET")) {
			if(this.params!=null&&this.params.size()>0) {
				builder.append("?");
				int i=0;
				for(SmsSettingParams param:params) {
					if(i!=0) {
						builder.append("&");
					}
					builder.append(param.getKeyParam());
					if(param.getParamType().equalsIgnoreCase("static")) {
						builder.append(String.format("=%s",param.getValueParam()));
					}
					if(param.getParamType().equalsIgnoreCase("dyna")) {
						if(param.getValueParam().equalsIgnoreCase("{{mobile}}")) {
							builder.append(String.format("=%s",this.mobileNumber));
						}
						if(param.getValueParam().equalsIgnoreCase("{{message}}")) {
							this.message=this.message.replaceAll("</var>", "");
							builder.append(String.format("=%s", java.net.URLEncoder.encode(this.message,"UTF-8")));
						}
						if(param.getValueParam().equalsIgnoreCase("{{templateid}}")) {
							builder.append(String.format("=%s", java.net.URLEncoder.encode(this.smsTemplateNumber,"UTF-8")));
						}
					}
					i++;
				}
			}
		}
		if(this.smsSetting.getMethodName().equalsIgnoreCase("POST")&&this.smsSetting.getContentType().equalsIgnoreCase("application/x-www-form-urlencoded")) {
			if(this.params!=null&&this.params.size()>0) {
				int i=0;
				for(SmsSettingParams param:params) {
					if(i!=0) {
						builder.append("&");
					}
					builder.append(param.getKeyParam());
					if(param.getParamType().equalsIgnoreCase("static")) {
						builder.append(String.format("=%s",param.getValueParam()));
					}
					if(param.getParamType().equalsIgnoreCase("dyna")) {
						if(param.getValueParam().equalsIgnoreCase("{{mobile}}")) {
							builder.append(String.format("=%s", this.mobileNumber));
						}
						if(param.getValueParam().equalsIgnoreCase("{{message}}")) {
							builder.append(String.format("=%s", java.net.URLEncoder.encode(this.message,"UTF-8")));
						}
					}
					i++;
				}
			}
		}
		return builder.toString();
	}
}
