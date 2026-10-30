package com.pratikshat.exicom.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.domain.AuditorAware;

import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.ObjectLog;

public class ObjectAuditListener {
	
	@PostUpdate 
	public void updateObject(Object obj) {
		EntityManager em=BeanUtil.getBean(EntityManager.class);
		AuditorAware<EmployeeDetails> emp=BeanUtil.getBean(AuditorAware.class);
		Map<String,String> oldValueMap=new HashMap<String,String>();
		Class cls= obj.getClass();
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String idfiled="";
		String idValue="0";
		Field[] methods= cls.getDeclaredFields();
		for(int i=0;i<methods.length;i++) {
			Field fld=methods[i];
			
			
			Annotation[] fanotation=fld.getAnnotations();
			for(Annotation f:fanotation) {
				
				if(f.toString().contains("@javax.persistence.Id()")) {
					idfiled=fld.getName();
					break;
				}
			}
		}
		try {
			Method oldDataMethod=cls.getDeclaredMethod("gOld");
			
			Object oldobject=oldDataMethod.invoke(obj);
			if(oldobject!=null)
				oldValueMap=getClassMethodValue(oldobject);
			
		}catch(Exception ex) {
			//System.out.println("Old Data Exception ex"+ex.getMessage());
		}
	
		
		for(Method method:cls.getDeclaredMethods()) {
			String keyName=method.getName();
			if(keyName.startsWith("set")||keyName.startsWith("gOld")||keyName.startsWith("old"))
				continue;
			
			try {
				Object value = method.invoke(obj);
				if(keyName.equalsIgnoreCase("get"+idfiled)) {
					idValue=value.toString();
					break;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		for(Method method:cls.getDeclaredMethods()) {
			try {
				String keyName=method.getName();
				if(keyName.startsWith("set")||keyName.startsWith("toString")||keyName.startsWith("gOld")||keyName.startsWith("old"))
					continue;
				Object value= method.invoke(obj);
				
				if(keyName.startsWith("get"))
					keyName=keyName.replaceFirst("get","");
				if(keyName.startsWith("is"))
					keyName=keyName.replaceFirst("is", "");
				String mVal=value==null?"":value.toString();
				if(value instanceof Date) {
					if(!mVal.equalsIgnoreCase("")) {
						Date dt=(Date)value;
						mVal=formatter.format(dt);
					}
				}
				//System.out.println("KeyName "+keyName+" \t"+mVal+"\r old value "+oldValueMap.get(keyName));
				String od=oldValueMap.get(keyName)==null?"":oldValueMap.get(keyName).toString();
				if(!od.equalsIgnoreCase(mVal)) {
					ObjectLog log=new ObjectLog();
					log.setKeyName(keyName);
					log.setValue(mVal);
					log.setObjectId(idValue);
					log.setObjectName(cls.getName().replace("com.pratikshat.exicom.entity.",""));
					log.setCreatedDate(new Date());
					log.setLoggedBy(emp.getCurrentAuditor().get());
					log.setOldValue(oldValueMap.get(keyName));
					em.persist(log);
				}
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//System.out.println(e.getMessage());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//System.out.println(e.getMessage());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				//System.out.println(e.getMessage());
			}
			catch(Exception ex) {
				
				//System.out.println(ex.getMessage());
			}
		}
		
	
	}
	private Map<String,String> getClassMethodValue(Object obj){
		Map<String,String> vlm=new HashMap<String,String>();
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Class cls=obj.getClass();
		for(Method method:cls.getDeclaredMethods()) {
			String mName=method.getName();
			if(mName.startsWith("get")||mName.startsWith("is")) {
				try {
					Object value=method.invoke(obj);
					String vl=value==null?"":value.toString();
					if(value instanceof Date ) {
						if(!vl.equalsIgnoreCase("")) {
							Date dt=(Date)value;
							vl=formatter.format(dt);
						}
						
					}										
					mName=mName.replaceFirst("get", "");
					mName=mName.replaceFirst("is","");
					
					vlm.put(mName, vl);
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return vlm;
	}

}
