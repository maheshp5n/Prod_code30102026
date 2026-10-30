package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ResourceType;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ServiceResourseRequest;
import com.pratikshat.exicom.model.response.SSAResponse;
import com.pratikshat.exicom.model.response.ServiceResourseResponse;

@Repository
public class ServiceRecourseRepositoryCustomeImpl implements ServiceRecourseRepositoryCustome {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public ServiceResourseRequest saveServiceResource(ServiceResourseRequest serviceRequest,LoginUser user, int businessDomainId) throws Exception {
		
		
		com.pratikshat.exicom.entity.EmployeeDetails emp;
		try {
			
			emp=entityManager.createQuery("select u from EmployeeDetails u join u.contact c where u=?1",com.pratikshat.exicom.entity.EmployeeDetails.class).setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(serviceRequest.getUserEmailId())).getSingleResult();
			if(emp.getContact().getContactId()!=serviceRequest.getContactId()) {
				
				throw new BadRequestException("Not a valid user, Given contact is not bind with user");
			}
			
		}catch(Exception ex) {
			
			throw new BadRequestException("Not a valid user");
		}
		Location location=null;
		try {
			location=entityManager.createQuery("select loc from Location loc where loc=?1",Location.class)
					.setParameter(1,new Location(serviceRequest.getLocationId()))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Warehouse not exists");
		}
		
		ServiceRecource serviceResource=null;
		try {
			serviceResource=entityManager.createQuery("select sr from ServiceRecource sr where sr.name=?1",ServiceRecource.class).setParameter(1, serviceRequest.getName()).getSingleResult();
		}catch(NoResultException ex) {
			
		}catch(NonUniqueResultException ex){
			throw new BadRequestException("Service Resource already exit with this name");
		}
		
		if(serviceResource!=null && serviceRequest.getServiceResourceId()==0) {
			throw new BadRequestException("Service Resource already exit with this name");
		}
		
		
		boolean n=false;
		ServiceRecource _serviceRequest=null;
		try {
			_serviceRequest=entityManager.createQuery("select sr from ServiceRecource sr where sr.user=?1",ServiceRecource.class).setParameter(1, emp).getSingleResult();
			
		}catch(NoResultException ex) {
			_serviceRequest=new com.pratikshat.exicom.entity.ServiceRecource();
			n=true;
		}
		
		 if(n) {
				_serviceRequest.setCreatedBy(new EmployeeDetails(user.getUsername()));
				_serviceRequest.setCreatedDate(new Date());
		 }
	
		_serviceRequest.setLocation(location);
		_serviceRequest.setModifiedBy(new EmployeeDetails(user.getUsername()));
		_serviceRequest.setModifiedDate(new Date());
		_serviceRequest.setName(serviceRequest.getName());
		_serviceRequest.setRecourceType(new ResourceType(serviceRequest.getRecourceTypeId()));
		_serviceRequest.setContact(emp.getContact());
		_serviceRequest.setActive(serviceRequest.isActive());
		_serviceRequest.setUser(emp);
		_serviceRequest.setBusinessDomain(new BusinessDomain(businessDomainId));
		entityManager.persist(_serviceRequest);
		serviceRequest.setServiceResourceId(_serviceRequest.getServiceResourceId());
		location.setOwner(emp);
		location.setModifiedBy(new EmployeeDetails(user.getUsername()));
		location.setModifiedDate(new Date());
		entityManager.persist(location);
		try {
			if(n) {
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(_serviceRequest.getName());
				_search.setKeyName("name");
				_search.setObjectName("ServiceRecource");
				_search.setObjectDetailsUrl("serviceResource/"+_serviceRequest.getServiceResourceId());
				_search.setObjectIdName("serviceResourceId");
				_search.setObjectIdValue(String.valueOf(_serviceRequest.getServiceResourceId()));
				
				entityManager.persist(_search);
			}
			
			
		}catch(Exception e) {}
		
		return serviceRequest;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public ServiceResourseRequest editServiceResource(ServiceResourseRequest serviceRequest, LoginUser user, int businessDomainId)
			throws BadRequestException, UnauthoriseException {
		ServiceRecource _serviceRequest=null;
		try {
			_serviceRequest=entityManager.createQuery("select sr from  ServiceRecource sr join sr.user usr join sr.location loc join sr.contact ct where sr=?1",ServiceRecource.class)
					.setParameter(1, new ServiceRecource(serviceRequest.getServiceResourceId()))
					.getSingleResult();
		}catch(NoResultException ex) {
			
			throw new BadRequestException("Service resource not found");
		}
		
		ServiceRecource count=null;
		try {
			count=entityManager.createQuery("select sc from ServiceRecource sc where sc.user.emailId=?1",ServiceRecource.class).setParameter(1, serviceRequest.getUserEmailId()).getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(!count.getUser().getEmailId().equalsIgnoreCase(_serviceRequest.getUser().getEmailId()))
			throw new BadRequestException("Selected User is bind with a another service resourcce");
		
		com.pratikshat.exicom.entity.EmployeeDetails emp;
		try {
			
			emp=entityManager.createQuery("select u from EmployeeDetails u join u.contact c where u=?1",com.pratikshat.exicom.entity.EmployeeDetails.class).setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(serviceRequest.getUserEmailId())).getSingleResult();
			if(emp.getContact().getContactId()!=serviceRequest.getContactId()) {
				
				throw new BadRequestException("Not a valid user, Given contact is not bind with user");
			}
			
		}catch(Exception ex) {
			
			throw new BadRequestException("Not a valid user");
		}
		Location location=null;
		try {
			location=entityManager.createQuery("select loc from Location loc where loc=?1",Location.class)
					.setParameter(1,new Location(serviceRequest.getLocationId()))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Warehouse not exists");
		}
		
		Location existingLocation=_serviceRequest.getLocation();
		if(existingLocation.getOwner().getEmailId().equalsIgnoreCase(emp.getEmailId())) {
			existingLocation.setOwner(new EmployeeDetails(user.getUsername()));
			entityManager.persist(existingLocation);
		}
		
		ServiceRecource checkLocation=null;
		try {
			checkLocation=entityManager.createQuery("select sr from ServiceRecource sr where sr.location=?1",ServiceRecource.class)
					.setParameter(1,location)
					.getSingleResult();
			if(checkLocation.getServiceResourceId()!=_serviceRequest.getServiceResourceId())
				throw new BadRequestException("Warehouse is already assigned to other resource");
		}
		catch(NoResultException ex) {
			
		}
		if(checkLocation!=null)
			if(checkLocation.getServiceResourceId()!=_serviceRequest.getServiceResourceId())
				throw new BadRequestException("Warehouse is already assigned to other resource");
		
		_serviceRequest=new com.pratikshat.exicom.entity.ServiceRecource();
		
		_serviceRequest.setLocation(location);
		_serviceRequest.setModifiedBy(new EmployeeDetails(user.getUsername()));
		_serviceRequest.setModifiedDate(new Date());
		_serviceRequest.setName(serviceRequest.getName());
		_serviceRequest.setRecourceType(new ResourceType(serviceRequest.getRecourceTypeId()));
		_serviceRequest.setContact(emp.getContact());
		_serviceRequest.setActive(serviceRequest.isActive());
		_serviceRequest.setUser(emp);
		_serviceRequest.setBusinessDomain(new BusinessDomain(businessDomainId));
		entityManager.persist(_serviceRequest);
		serviceRequest.setServiceResourceId(_serviceRequest.getServiceResourceId());
		location.setOwner(emp);
		location.setModifiedBy(new EmployeeDetails(user.getUsername()));
		location.setModifiedDate(new Date());
		entityManager.persist(location);
		return serviceRequest;
	}
	
	@Override
	public List<ServiceResourseResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user, int businessDomainId) throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.ServiceResourseResponse(res) from ServiceRecource res left join res.user usr left join res.location loc left join res.recourceType typ left join res.createdBy cby left join res.modifiedBy mby where 1=1");
			
			queryStringBuilder.append(" and (res.businessDomain.businessDomainId=:businessDomainId or res.businessDomain.businessDomainId=3)");
			
			if(searchKeys.get("name")!=null&&!searchKeys.get("name").equals("")) {
				queryStringBuilder.append(" and res.name like :name");
			}
			if(searchKeys.get("userEmailId")!=null&&!searchKeys.get("userEmailId").equals("")) {
				queryStringBuilder.append(" and usr.emailId like :userEmailId");
			}
			if(searchKeys.get("type")!=null&&!searchKeys.get("type").equals("")) {
				queryStringBuilder.append(" and typ.resourceTypeName like :type");
			}
			if(searchKeys.get("location")!=null&&!searchKeys.get("location").equals("")) {
				queryStringBuilder.append(" and loc.locationName like :location");
			}
				
			Query qr=entityManager.createQuery(queryStringBuilder.toString(),ServiceResourseResponse.class);
			
			qr.setParameter("businessDomainId",businessDomainId);
			
			if(searchKeys.get("name")!=null&&!searchKeys.get("name").equals("")) {
				qr.setParameter("name","%"+ searchKeys.get("name")+"%");
			}
			if(searchKeys.get("userEmailId")!=null&&!searchKeys.get("userEmailId").equals("")) {
				qr.setParameter("userEmailId", "%"+ searchKeys.get("userEmailId")+"%");
			}
			if(searchKeys.get("type")!=null&&!searchKeys.get("type").equals("")) {
				qr.setParameter("type", "%"+ searchKeys.get("type")+"%");
			}
			if(searchKeys.get("location")!=null&&!searchKeys.get("location").equals("")) {
				qr.setParameter("location", "%"+ searchKeys.get("location")+"%");
			}
				
			return qr.getResultList();
		}

	@Override
	public ServiceRecource getServiceResourceWithCaseId(long id)
			throws BadRequestException {
		ServiceRecource serviceRecource = null;
		try {
			Query serviceAppointmentOwnerQuery = entityManager.createQuery(
					"select sr from BtCase cd left join cd.site st left join st.serviceResource sr left join sr.user ur where cd.id=?1",
					ServiceRecource.class);
			serviceAppointmentOwnerQuery.setParameter(1, id);
			serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		return serviceRecource;
	}

	@Override
	public ServiceRecource getServiceResourceWithId(long id) throws BadRequestException {
		ServiceRecource serviceRecource = null;
		
		try {
			Query serviceAppointmentOwnerQuery = entityManager.createQuery(
					"select sr from ServiceRecource sr left join sr.user ur where sr.serviceResourceId=?1",
					ServiceRecource.class);
			serviceAppointmentOwnerQuery.setParameter(1, id);
			serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		return serviceRecource;
	}

	@Override
	public ServiceRecource getServiceResourceWithIdAndServiceid(long serviceId, long resourceid)
			throws BadRequestException {
		ServiceRecource serviceRecource = null;
		try {
			
			serviceRecource = entityManager.createQuery("select sr from ServiceRecource sr join sr.contact cnt join cnt.legalEntity le join cnt.account act join sr.location lc where le.legalEntityId=?1 and sr.serviceResourceId=?2",ServiceRecource.class)
					.setParameter(1,serviceId).setParameter(2, resourceid).getSingleResult();
	
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
		}
		return serviceRecource;
	}

	@Override
	public ServiceRecource getServiceResourceWithUser(EmployeeDetails user) throws BadRequestException {
		ServiceRecource res=null;
		try {
			res=entityManager.createQuery("select res from ServiceRecource res join res.contact ct join ct.legalEntity lg join ct.account act where res.user=?1",ServiceRecource.class)
					.setParameter(1, user)
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service resource not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple service resource found");
		}
		return res;
	}

	@Override
	public List<Map<String, Object>> getServiceResourceRatingAdvanceSearch(Map<String, Object> data, int businessDomainId) {
		String query="SELECT sr.`name`,lg.`legal_entity_name`,AVG(sar.`engrating`) as avg_rating FROM `sa_eng_rating` sar JOIN `service_appointment` sa ON sar.sa_id=sa.`service_appointment_id`\r\n" + 
				"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n" + 
				"	JOIN `contact_details` ct ON ct.`contact_id` =sr.`contact_contact_id`\r\n" + 
				"	JOIN `legal_entity` lg ON lg.`legal_entity_id`=ct.`legal_entity_id`\r\n"+
				"	where 1=1 ";
				//"	GROUP BY sr.`name`,lg.`legal_entity_name`";
		List<String> param=new ArrayList<String>();
		
		query+=" and (sr.`business_domain_business_domain_id`=? or sr.`business_domain_business_domain_id`=3)";
		param.add(String.valueOf(businessDomainId));
		
		if(data.get("eng_name")!=null&&!data.get("eng_name").toString().equalsIgnoreCase("")) {
			query+=" and sr.`name` like ?";
			param.add("%"+data.get("eng_name").toString()+"%");
		}
		if(data.get("legal_entity")!=null&&!data.get("legal_entity").toString().equalsIgnoreCase("")) {
			query+=" and lg.`legal_entity_name` like ?";
			param.add("%"+data.get("legal_entity").toString()+"%");
		}
		
		query+=" GROUP BY sr.`name`,lg.`legal_entity_name`";
		
		return jdbc.queryForList(query,param.toArray());
	}

	
	

}
