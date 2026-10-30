package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.ReportRequest;
import com.pratikshat.exicom.entity.ReportWizard;
import com.pratikshat.exicom.entity.ReportWizardExecuter;
import com.pratikshat.exicom.entity.ReportWizardParam;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@Repository
public class ReportWizardCustomeImpl implements ReportWizardCustome {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	UserProfileRepository dbUserProfile;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void createNewReportWizard(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		//VALIDATION START
		if(request.get("reportName")==null||request.get("reportName").equals(""))
			throw new BadRequestException("Report name can not be null");
		
		if(request.get("reportQuery")==null||request.get("reportQuery").equals(""))
			throw new BadRequestException("Query can not be null");
		
		//VALIDATION END
		
		Date today=new Date();
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		
		ReportWizard _wizard=new ReportWizard();
		_wizard.setCreatedBy(emp);
		_wizard.setCreatedDate(today);
		_wizard.setLastModifiedBy(emp);
		_wizard.setLastModifiedDate(today);
		_wizard.setPresentationType(request.get("presentationType").toString());
		_wizard.setReportDescription(request.get("reportDescription").toString());
		_wizard.setReportName(request.get("reportName").toString());
		_wizard.setReportQuery(request.get("reportQuery").toString());
		_wizard.setReportType(request.get("reportType").toString());
		if(request.get("reportLinkId")!=null&&!request.get("reportLinkId").toString().equalsIgnoreCase("")) {
			_wizard.setReportLinkId(Long.parseLong(request.get("reportLinkId").toString()));
		}
		em.persist(_wizard);
		
		if(request.get("params")!=null&&!_wizard.getReportType().equalsIgnoreCase("Dashboard")) {
			List<Map<String,Object>> params=(List<Map<String,Object>>) request.get("params");
			for(Map<String,Object> param:params) {
				ReportWizardParam _reportParam=new ReportWizardParam();
				_reportParam.setParamDisplay(param.get("paramDisplay").toString());
				if(param.get("paramIdentifier")==null)
					throw new BadRequestException("Not Valid Identifier");
				if(!param.get("paramIdentifier").toString().startsWith("::"))
					throw new BadRequestException("Please use : to start identityfier");
				
				_reportParam.setParamIdentifier(param.get("paramIdentifier").toString());
				_reportParam.setParamType(param.get("paramType").toString());
				_reportParam.setReportWizard(_wizard);
				em.persist(_reportParam);
			}
		}
		if(request.get("executers")==null)
			throw new BadRequestException("Executer lis not found");
		
		if(request.get("executers")!=null) {
			List<Map<String,Object>> executers=(List<Map<String,Object>>)request.get("executers");
			if(executers.size()==0)
				throw new BadRequestException("Executer lis not found");
			
			for(Map<String,Object> executer:executers) {
				ReportWizardExecuter _executer=new ReportWizardExecuter();
				_executer.setExecuter(executer.get("executer").toString());
				_executer.setExecuterType(executer.get("executerType").toString());
				_executer.setReportWizard(_wizard);
				em.persist(_executer);
			}
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void editReportWizard(Long Id, Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		Date today=new Date();
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		
		ReportWizard _wizard=null;
		try {
			_wizard=em.createQuery("select rpt from ReportWizard rpt where rpt=?1",ReportWizard.class).setParameter(1,new ReportWizard(Id)).getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Wrong report id");
		}
		em.createQuery("delete from ReportWizardParam pr where pr.reportWizard=?1").setParameter(1, _wizard).executeUpdate();
		em.createQuery("delete from ReportWizardExecuter ex where ex.reportWizard=?1").setParameter(1, _wizard).executeUpdate();
		
		_wizard.setLastModifiedBy(emp);
		_wizard.setLastModifiedDate(today);
		_wizard.setPresentationType(request.get("presentationType").toString());
		_wizard.setReportDescription(request.get("reportDescription").toString());
		_wizard.setReportName(request.get("reportName").toString());
		_wizard.setReportQuery(request.get("reportQuery").toString());
		_wizard.setReportType(request.get("reportType").toString());
		if(request.get("reportLinkId")!=null&&!request.get("reportLinkId").toString().equalsIgnoreCase("")) {
			_wizard.setReportLinkId(Long.parseLong(request.get("reportLinkId").toString()));
		}
		em.persist(_wizard);
		
		if(request.get("params")!=null&&!_wizard.getReportType().equalsIgnoreCase("Dashboard")) {
			List<Map<String,Object>> params=(List<Map<String,Object>>) request.get("params");
			for(Map<String,Object> param:params) {
				ReportWizardParam _reportParam=new ReportWizardParam();
				_reportParam.setParamDisplay(param.get("paramDisplay").toString());
				_reportParam.setParamIdentifier(param.get("paramIdentifier").toString());
				_reportParam.setParamType(param.get("paramType").toString());
				_reportParam.setReportWizard(_wizard);
				em.persist(_reportParam);
			}
		}
		if(request.get("executers")==null)
			throw new BadRequestException("Executer lis not found");
		
		if(request.get("executers")!=null) {
			List<Map<String,Object>> executers=(List<Map<String,Object>>)request.get("executers");
			if(executers.size()==0)
				throw new BadRequestException("Executer lis not found");
			
			for(Map<String,Object> executer:executers) {
				ReportWizardExecuter _executer=new ReportWizardExecuter();
				_executer.setExecuter(executer.get("executer").toString());
				_executer.setExecuterType(executer.get("executerType").toString());
				_executer.setReportWizard(_wizard);
				em.persist(_executer);
			}
		}
	}

	@Override
	//@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public List<Map<String, Object>> executeReport(Long Id, List<Map<String, String>> param, LoginUser user,List<String> lstUsers)
			throws BadRequestException, UnauthoriseException {
		SimpleDateFormat dFormat=new SimpleDateFormat("dd-MM-yy");
		String currentRole="";
		Iterator<LoginUserRoles> lsRoles= (Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		
		long userLocationId=0;
		try {
			Map<String,Object> uLocation=jdbc.queryForMap("select `location_location_id` from `service_recource` where `user_email_id`=?",user.getUsername());
			userLocationId=Long.parseLong(uLocation.get("location_location_id").toString());
			
		}catch(Exception ex) {
			
		}
		List<Long> userLocationList= new ArrayList<Long>(); //
		
		StringBuilder userLocationBuilder=new StringBuilder();
		
		try {
			
			userLocationList=dbUserProfile.getLocationAndChildLocation(user.getUsername());
			
		}catch(Exception ex) {
			
		}
		if(userLocationList!=null&&userLocationList.size()>0) {
			int ct=userLocationList.size();
			for(int i=0;i<ct;i++) {
				if(i!=0) {
					userLocationBuilder.append(",");
				}
				userLocationBuilder.append(userLocationList.get(i));
			}
		}
		
		while(lsRoles.hasNext()) {
			LoginUserRoles roles=lsRoles.next();
			currentRole=roles.getAuthority();
			
		}
		Map<String,Object> checkQuery=null;
		try {
			checkQuery=jdbc.queryForMap("select * from report_wizard rm join report_wizard_executer ex on rm.id=ex.report_wizard_id where rm.id=? and ((ex.executer_type=? and ex.executer=?) or (ex.executer_type=? and ex.executer=?))",new Object[] {
					Id,
					"USER",
					user.getUsername(),
					"ROLE",
					currentRole
			});
			
		}catch(EmptyResultDataAccessException ex) {
			throw new UnauthoriseException("You are not Authorise");
		}
		
		if(checkQuery==null)
			throw new UnauthoriseException("You are not Authorise");
		String userList="";
		for(int i=0;i<lstUsers.size();i++) {
			if(i==0) {
				userList="'"+lstUsers.get(i)+"'";
			}
			else {
				userList+=",'"+lstUsers.get(i)+"'";
			}
		}
		
		
		
		String queryString =checkQuery.get("report_query").toString();
		String counterQuery=null;
		
		queryString=queryString.replace("{{user}}","'"+user.getUsername()+"'");
		queryString=queryString.replace("{{userlist}}", userList);
		queryString=queryString.replace("{{location}}", String.valueOf(userLocationId));
		queryString=queryString.replace("{{locationlist}}", userLocationBuilder.toString());
		queryString=queryString.replace("{{owner_details_email_id}}","'"+user.getUsername()+"'");
		if(checkQuery.get("report_query_counter")!=null) {
			counterQuery=checkQuery.get("report_query_counter").toString();
			counterQuery=counterQuery.replace("{{user}}","'"+user.getUsername()+"'");
			counterQuery=counterQuery.replace("{{userlist}}", userList);
			counterQuery=counterQuery.replace("{{location}}", String.valueOf(userLocationId));
			counterQuery=counterQuery.replace("{{locationlist}}", userLocationBuilder.toString());
			counterQuery=counterQuery.replace("{{owner_details_email_id}}","'"+user.getUsername()+"'");
		}
		
		
		for(Map<String,String> vl:param) {
			if(vl.get("paramType").equalsIgnoreCase("Number")) {
				String p=vl.get("paramValue");
				if(!isNumeric(p))
					throw new BadRequestException("Please provide a valid number");
				queryString=queryString.replaceAll(vl.get("paramIdentifier"), vl.get("paramValue"));
				if(counterQuery!=null)
				counterQuery=counterQuery.replaceAll(vl.get("paramIdentifier"), vl.get("paramValue"));
			}
			if(vl.get("paramType").equalsIgnoreCase("Text")) {
				String paramd= vl.get("paramValue");
				paramd=paramd.replaceAll("[^a-zA-Z0-9]","");
				queryString=queryString.replaceAll(vl.get("paramIdentifier"), vl.get("paramValue"));
				if(counterQuery!=null)
				counterQuery=counterQuery.replaceAll(vl.get("paramIdentifier"), vl.get("paramValue"));
			}
				
			if(vl.get("paramType").equalsIgnoreCase("Date")) {
				try {
					Date date=dFormat.parse(vl.get("paramValue"));
					String _d=dFormat.format(date);
					SimpleDateFormat ds=new SimpleDateFormat("yyyy-MM-dd");
					queryString=queryString.replaceAll(vl.get("paramIdentifier"),ds.format(date));
					if(counterQuery!=null)
					counterQuery=counterQuery.replaceAll(vl.get("paramIdentifier"),ds.format(date));
				} catch (ParseException e) {
					throw new BadRequestException("Unable to parse Date"+vl.get("paramValue")+", Please provide date in dd-MM-yy format");
				}	
			}
		}
		//4545664
		
		
		if(checkQuery.get("report_type").toString().equalsIgnoreCase("Report")) {
			Long reportWId=Long.parseLong(checkQuery.get("id").toString());
			ReportRequest req=null;
			try {
				req=em.createQuery("select req from ReportRequest req where req.wizard=?1 and req.requestBy=?2 and (req.reportStatus=?3 or req.reportStatus=?4)",ReportRequest.class)
						.setParameter(1,new ReportWizard(reportWId))
						.setParameter(2, new EmployeeDetails(user.getUsername()))
						.setParameter(3, "Pending")
						.setParameter(4, "Running")
						.getSingleResult();
				throw new BadRequestException("A previous request is already in que");
				
			}catch(NoResultException ex) {
				
			}catch(NonUniqueResultException ex) {
				
			}
			jdbc.update("insert into report_request(query,report_status,request_date,request_by_email_id,wizard_id,counter_query) values(?,?,?,?,?,?)",queryString,"Pending",new Date(),user.getUsername(),reportWId,counterQuery);
//			req=new ReportRequest();
//			req.setQuery(queryString);
//			req.setReportStatus("Pending");
//			req.setRequestBy(new EmployeeDetails(user.getUsername()));
//			req.setRequestDate(new Date());
//			req.setWizard(new ReportWizard(reportWId));
//			em.persist(req);
			List<Map<String,Object>> data= new ArrayList<Map<String,Object>>();
			Map<String,Object> da=new HashMap<String,Object>();
			da.put("Message", "Your request has been submitted for further process");
			//
			data.add(da);
			System.out.println("Yahan tak to aaya");
			return data;
		}
		else {
			return jdbc.queryForList(queryString);
		}
		
	}
	
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	 
	public boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum).matches();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void deleteReportWizard(Long id, LoginUser user) throws BadRequestException {
//				DELETE FROM `report_wizard` WHERE id=?
//				DELETE FROM `report_wizard_executer` WHERE `report_wizard_id`=?
//				DELETE FROM `report_wizard_param` WHERE `report_wizard_id`=?
		try {
			jdbc.update("DELETE FROM `report_wizard_param` WHERE `report_wizard_id`=?",id);
			jdbc.update("DELETE FROM `report_wizard_executer` WHERE `report_wizard_id`=?",id);
			jdbc.update("DELETE FROM `report_wizard` WHERE id=?",id);
		}catch(Exception ex) {
			throw new BadRequestException(ex);
		}
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getAllReportByTypeAndExecuterAdvanceSearch(Map<String, Object> request, String role_user_type, String role_user_value) throws ParseException {
		SimpleDateFormat dformatter=new SimpleDateFormat("yyyy-mm-dd");
		List<Object> value = new ArrayList<>();
		String _query="SELECT \r\n" + 
				"rm.id AS id, \r\n" + 
				"rm.`created_date` AS createdDate, \r\n" + 
				"rm.`last_modified_date` AS lastModifiedDate, \r\n" + 
				"rm.`presentation_type` AS presentationType, \r\n" + 
				"rm.`report_description` AS reportDescription, \r\n" + 
				"rm.`report_name` AS reportName, \r\n" + 
				"rm.`report_type` AS reportType, \r\n" + 
				"rm.`created_by_email_id` AS createdBy, \r\n" + 
				"rm.`last_modified_by_email_id` AS lastModifiedBy  \r\n" + 
				"FROM report_wizard rm \r\n" + 
				"JOIN report_wizard_executer ex ON rm.id=ex.report_wizard_id \r\n" + 
				"WHERE 1=1";
		StringBuilder queryBuilder=new StringBuilder();
		queryBuilder.append(_query);
		
		queryBuilder.append(" and ex.executer_type=?");
		value.add(role_user_type);
		
		queryBuilder.append(" and ex.executer=?");
		value.add(role_user_value);
		
		if(request.get("report_name")!=null&&!request.get("report_name").equals("")) {
			queryBuilder.append(" and rm.`report_name` like ?");
			value.add("%"+request.get("report_name")+"%");
			
		}
		if(request.get("report_description")!=null&&!request.get("report_description").equals("")) {
			queryBuilder.append(" and rm.`report_description` like ?");
			value.add("%"+request.get("report_description")+"%");
		}
		
		return jdbc.queryForList(queryBuilder.toString(),value.toArray());
	}
}
