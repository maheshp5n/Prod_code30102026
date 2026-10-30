package com.pratikshat.exicom.interceptor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pratikshat.exicom.config.ApprovalWorkflowCrat;
import com.pratikshat.exicom.config.CustomeClassLoader;
import com.pratikshat.exicom.entity.Auditable;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.BtToBeApproved;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.ObjectDefinition;
import com.pratikshat.exicom.entity.UserRoles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@Component
public class WorkflowExecuter {
	
	@Value("${app.workflow.classpath}")
	String classPath;
	
	@Autowired
	CustomeClassLoader loader;
	
	public void executeWorkflow(javax.persistence.EntityManager em,Object entity,String dmlCommand,String onEvent,LoginUser user) throws BadRequestException,UnauthoriseException {
		java.util.List<com.pratikshat.exicom.entity.WorkFlowEntity> workflowQuery=new java.util.ArrayList<com.pratikshat.exicom.entity.WorkFlowEntity>();
		try {
			String objectName=entity.getClass().getName();
			objectName=objectName.substring((objectName.lastIndexOf(".")+1));
			
			workflowQuery=em.createQuery("select work from WorkFlowEntity work where work.object=?1 and work.onevent=?2 and work.dmlCommand=?3 and work.active=?4",com.pratikshat.exicom.entity.WorkFlowEntity.class)
					.setParameter(1, new com.pratikshat.exicom.entity.ObjectDefinition(objectName))
					.setParameter(2, onEvent)
					.setParameter(3, dmlCommand)
					.setParameter(4, true)
					.getResultList();
			
			if(workflowQuery!=null&&workflowQuery.size()>0) {
				for(com.pratikshat.exicom.entity.WorkFlowEntity _work:workflowQuery) {
					try {

						
						Class classz=loader.getClass(_work.getClassName());
						
						Object obj=classz.newInstance();
						classz.getDeclaredMethods()[0].invoke(obj, em,entity,user);
					}
					catch(InvocationTargetException e) {
						e.getCause().printStackTrace();
						throw new BadRequestException(e.getCause().getMessage());
					}
					catch(Exception ex) {
						ex.printStackTrace();
						throw new BadRequestException(ex.getMessage());
					}
				}
			}
			
			
			
		}catch(BadRequestException ex) {
			throw ex;
		}
		
		if(onEvent.equalsIgnoreCase("POST")) {
			List<BtApprovalStapes> stpsList=new ArrayList<BtApprovalStapes>();
			
			try {
				
				String objectName=entity.getClass().getName();
				
				objectName=objectName.substring((objectName.lastIndexOf(".")+1));

				
				List<String> objectUrl=null;
				try {
						objectUrl=em.createQuery("select obj.objectUrl from ObjectDefinition obj where obj.objectName=?1",String.class).setParameter(1, objectName).getResultList();
				}catch(NoResultException ex) {
								
				}
				
				try {
					if(dmlCommand.equalsIgnoreCase("INSERT")){
						for(String url:objectUrl){
							if(url!=null){
								Auditable<EmployeeDetails> spObjes=(Auditable<EmployeeDetails>)entity;
								url=url==null?"#":url+"/"+spObjes.getId();
								GlobalSearchIndex sr=new GlobalSearchIndex();
								sr.setKeyName("manualId");
								sr.setObjectDetailsUrl(url);
								sr.setObjectIdName("id");
								sr.setObjectIdValue(""+spObjes.getId());
								sr.setObjectName(objectName);
								sr.setSearchString(spObjes.getManualId());
								if(spObjes.getManualId()==null)
									spObjes.setManualId("NA");
								em.persist(sr);
							}
							
						}
						
					}
					

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				stpsList=em.createQuery("select stp from BtApprovalStapes stp join stp.approval apr where stp.stapeNumber=?1 and apr.active=?2 and (apr.initialSubmitters like ?3 or apr.initialSubmitters like ?4) and apr.objectClassName=?5",BtApprovalStapes.class)
						.setParameter(1, 1)
						.setParameter(2, true)
						.setParameter(3,"%ROLE:"+getRole(user,em)+"%")
						.setParameter(4,"%USER:"+user.getUsername()+"%")
						.setParameter(5, objectName)
						.getResultList();
				
				if(stpsList!=null&&stpsList.size()>0) {
					
					for(BtApprovalStapes stps:stpsList) {
						
						//URLClassLoader classLoader=URLClassLoader.newInstance(new URL[] {new File(stps.getApproval().getCtClassPath()).toURI().toURL()},this.getClass().getClassLoader());
						
						//Class clz=classLoader.loadClass(stps.getApproval().getCtClassName());
						
						ApprovalWorkflowCrat card=loader.loadClass(stps.getApproval().getCtClassName());// (ApprovalWorkflowCrat)clz.newInstance();
						
						if(card.isValid(entity,dmlCommand, user, em, stps)) {
							String url=objectUrl!=null&&objectUrl.size()>0 ? objectUrl.get(0):"";
							Auditable<EmployeeDetails> spObje=(Auditable<EmployeeDetails>)entity;
							url=url+"/"+spObje.getId();
							BtToBeApproved toBeApproved=new BtToBeApproved();
							toBeApproved.setAssignedDate(new Date());
							toBeApproved.setAssignedTo(stps.getAssignedTo());
							toBeApproved.setCreatedBy(new EmployeeDetails(user.getUsername()));
							toBeApproved.setCreatedDate(new Date());
							toBeApproved.setInitiator(new EmployeeDetails(user.getUsername()));
							toBeApproved.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
							toBeApproved.setLastModifiedDate(new Date());
							toBeApproved.setObjectId(spObje.getId());
							//toBeApproved.setManualId(spObje.getManualId());
							toBeApproved.setObjectManualId(spObje.getManualId());
							toBeApproved.setObjectName(objectName);
							toBeApproved.setOwner(new EmployeeDetails(user.getUsername()));
							toBeApproved.setStage(1);
							toBeApproved.setStapes(stps);
							toBeApproved.setObjectUrl(url);
							toBeApproved.setStatus(false);
							
							em.persist(toBeApproved);
						}
					}
				}						
				
			}catch(Exception ex) {
				
				throw new BadRequestException(ex);
			}
		}
		
		
	}
	
	private String getRole(LoginUser user,EntityManager em) {
		String role="";
		if(user.getAuthorities()!=null) {
			Iterator<LoginUserRoles> ite= (Iterator<LoginUserRoles>)user.getAuthorities().iterator();
			
			while(ite.hasNext()) {
				LoginUserRoles rls=ite.next();
				role=rls.getAuthority();
			}
		}else {
			LoginUserRoles rls=em.createQuery("select rls from LoginUserRoles rls where rls.loginUser=?1",LoginUserRoles.class).setParameter(1, user).getSingleResult();
			role=rls.getAuthority();
		}
		
		return role;
	}
}
