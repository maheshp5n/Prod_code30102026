package com.pratikshat.exicom.interceptor.action;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.hibernate.StatelessSession;
import org.hibernate.event.spi.EventSource;
import org.hibernate.jpa.QueryHints;

public class ServiceAppointmentAction {
	com.pratikshat.exicom.entity.ServiceAppointment _erviceApointment;
	StatelessSession eventSource;
	public ServiceAppointmentAction(com.pratikshat.exicom.entity.ServiceAppointment _serviceApointment,StatelessSession eventSource){
		this._erviceApointment=_serviceApointment;
		this.eventSource=eventSource;
		try {
			sendSms();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private void sendSms() {
		try {
			SimpleDateFormat format=new SimpleDateFormat("yyyMMdd");
			com.pratikshat.exicom.entity.ServiceRecource serviceResource=eventSource.createQuery("select r from ServiceRecource r where r=?1",com.pratikshat.exicom.entity.ServiceRecource.class).setParameter(1,new com.pratikshat.exicom.entity.ServiceRecource(this._erviceApointment.getServiceResource().getServiceResourceId())).getSingleResult();
			com.pratikshat.exicom.entity.ContactDetails contact=eventSource.createQuery("select c from ContactDetails c where c.contactId=?1",com.pratikshat.exicom.entity.ContactDetails.class).setParameter(1, serviceResource.getContact().getContactId()).getSingleResult();
			String message="Dear <Vendor Name> \r\n" + 
					"New Case (<Case NO>) has been Assigned To you. please follow the link to see the case <Link Of case>";
			message=message.replace("<Vendor Name>", serviceResource.getName());
			message=message.replace("<Case NO>","CS-"+format.format(new java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(""+_erviceApointment.getCaseDetails().getCaseId(),5));
			message=message.replace("<Link Of case>", "http://5.189.166.26:8282/#/serviceappointment/"+_erviceApointment.getServiceAppointmentId());
			
			String url=String.format("http://onex-ultimo.in/api/pushsms?user=Exicom2019&authkey=92DhnvBR183E&sender=exichg&mobile=%s&text=%s&rpt=1", contact.getMobile(),URLEncoder.encode(message,"UTF-8")) ;
			
			HttpURLConnection http=(HttpURLConnection)new URL(url).openConnection();
			http.setRequestMethod("GET");
			int response =http.getResponseCode();
			http.disconnect();
			System.out.println("sms send status code");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
