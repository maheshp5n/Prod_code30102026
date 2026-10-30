package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.SSA;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.SSARequest;
import com.pratikshat.exicom.model.response.SSAResponse;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;

@Repository
public class SSACustomRepositoryImpl implements SSACustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public SSARequest saveSSA(SSARequest request, LoginUser user) throws Exception {
		
		if(request!=null) {
			if(request.getCircleName()!=null) {
				if(request.getSSAName()!=null) {
					
					CircleDetails circle = null;
					try {
						Query selectCircleQuery = entityManager
								.createQuery("select cd from CircleDetails cd where cd.circleName=?1", CircleDetails.class);
						selectCircleQuery.setParameter(1, request.getCircleName());
						circle = (CircleDetails) selectCircleQuery.getSingleResult();
					} catch (NoResultException ex) {
							throw new BadRequestException("Circle does not exist with name "+request.getCircleName());
					}
					
					
					SSA ssa = null;
					try {
						Query selectSSAQuery = entityManager
								.createQuery("select sa from SSA sa left join sa.circle cr where cr.circleName=?1 and sa.name=?2", SSA.class);
						selectSSAQuery.setParameter(1, request.getCircleName());
						selectSSAQuery.setParameter(2, request.getSSAName());
						ssa = (SSA) selectSSAQuery.getSingleResult();
					} catch (NoResultException ex) {
							
					}
					
					if(ssa!=null) {
						throw new BadRequestException("SSA already exist with name "+request.getSSAName());
					}
					
					SSA ssaDetails = new SSA();
					ssaDetails.setName(request.getSSAName());
					ssaDetails.setCircle(circle);
					ssaDetails.setCreatedBy(new EmployeeDetails(user.getUsername()));
					ssaDetails.setCreatedDate(new Date());
					ssaDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
					ssaDetails.setModifiedDate(new Date());
					ssaDetails.setOwner(new EmployeeDetails(user.getUsername()));
					
					entityManager.persist(ssaDetails);
					
					
					
					// search index entry
					try {
						com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
						_search.setKeyName("name");
						_search.setObjectDetailsUrl("ssa/" + ssaDetails.getId());
						_search.setObjectIdName("id");
						_search.setObjectIdValue("" + ssaDetails.getId());
						_search.setObjectName("SSA");
						_search.setSearchString(ssaDetails.getName());
						entityManager.persist(_search);

					} catch (Exception e) {
					}

					request.setSSAId(ssaDetails.getId());

					entityManager.flush();
					entityManager.clear();
					return request;
					
				}else {
					throw new BadRequestException("SSA Name is null");
				}
			}else {
				throw new BadRequestException("Circle Name is null");
			}
		}else {
			throw new BadRequestException("Request Paramter does not exist");
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public SSARequest updateSSA(SSARequest request, LoginUser user) throws Exception {
		
		if(request!=null) {
			if(request.getCircleName()!=null) {
				if(request.getSSAName()!=null) {
					
					CircleDetails circle = null;
					try {
						Query selectCircleQuery = entityManager
								.createQuery("select cd from CircleDetails cd where cd.circleName=?1", CircleDetails.class);
						selectCircleQuery.setParameter(1, request.getCircleName());
						circle = (CircleDetails) selectCircleQuery.getSingleResult();
					} catch (NoResultException ex) {
							throw new BadRequestException("Circle does not exist with name "+request.getCircleName());
					}
					
					
					SSA ssa = null;
					try {
						Query selectSSAQuery = entityManager
								.createQuery("select sa from SSA sa left join sa.circle cr where sa.id=?1", SSA.class);
						selectSSAQuery.setParameter(1, request.getSSAId());
						ssa = (SSA) selectSSAQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new BadRequestException("SSA does not exist");	
					}
					
					if(ssa!=null) {
					
						ssa.setName(request.getSSAName());
						ssa.setCircle(circle);
						ssa.setModifiedBy(new EmployeeDetails(user.getUsername()));
						ssa.setModifiedDate(new Date());
						
						entityManager.persist(ssa);
					}

					entityManager.flush();
					entityManager.clear();
					return request;
					
				}else {
					throw new BadRequestException("SSA Name is null");
				}
			}else {
				throw new BadRequestException("Circle Name is null");
			}
		}else {
			throw new BadRequestException("Request Paramter does not exist");
		}
	}
	
	
	@Override
	public List<SSAResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user) throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.SSAResponse(ssa) from SSA ssa left join ssa.circle cr left join ssa.createdBy cby left join ssa.modifiedBy mby left join ssa.owner onr where 1=1");
			
			if(searchKeys.get("name")!=null&&!searchKeys.get("name").equals("")) {
				queryStringBuilder.append(" and ssa.name like :name");
			}
			if(searchKeys.get("circle")!=null&&!searchKeys.get("circle").equals("")) {
				queryStringBuilder.append(" and cr.circleName like :circle");
			}
				
			Query qr=entityManager.createQuery(queryStringBuilder.toString(),SSAResponse.class);
			
			if(searchKeys.get("name")!=null&&!searchKeys.get("name").equals("")) {
				qr.setParameter("name","%"+ searchKeys.get("name")+"%");
			}
			if(searchKeys.get("circle")!=null&&!searchKeys.get("circle").equals("")) {
				qr.setParameter("circle", "%"+ searchKeys.get("circle")+"%");
			}
				
			return qr.getResultList();
		}

}
