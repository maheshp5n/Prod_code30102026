package com.pratikshat.exicom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.CityCatagory;
import com.pratikshat.exicom.entity.CityDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.AssetsResponse;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.model.response.CityDetailsResponse;
@Repository
public class CityDetailsCustomRepositoryImpl implements CityDetailsCustomRepository {

	@PersistenceContext
	EntityManager em;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public com.pratikshat.exicom.model.request.CityDetails saveCityDetails(com.pratikshat.exicom.model.request.CityDetails cityDetails,String userId)throws Exception {
		
		com.pratikshat.exicom.entity.CityDetails city = null;
		try {
			Query selectCityQuery = em.createQuery("select cd from CityDetails cd where cd.cityName=?1",CityDetails.class);
			selectCityQuery.setParameter(1, cityDetails.getCityName());
			city = (com.pratikshat.exicom.entity.CityDetails)selectCityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(city!=null) {
			throw new BadRequestException("City already exist with name "+cityDetails.getCityName());
		}
		
		CityCatagory cityCategory=getCityCatagoryById(cityDetails.getCityCategoryId());
		if(cityCategory!=null) {
			com.pratikshat.exicom.entity.CityDetails _city=new com.pratikshat.exicom.entity.CityDetails();
			_city.setCityCatagory(cityCategory);
			_city.setCityName(cityDetails.getCityName());
			_city.setCreatedBy(new EmployeeDetails(userId));
			_city.setCreatedDate(new Date());
			_city.setModifiedBy(new EmployeeDetails(userId));
			_city.setModifiedDate(new Date());
			_city.setOwnerDetails(new EmployeeDetails(userId));
			_city.setSynced(false);
			workflow.executeWorkflow(em, _city, "INSERT", "PRE", new LoginUser(userId));
			em.persist(_city);
			workflow.executeWorkflow(em, _city, "INSERT", "POST", new LoginUser(userId));
			
			// Global Search Entry Point
			try {
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(cityDetails.getCityName());
				_search.setKeyName("cityName");
				_search.setObjectName("CityDetails");
				_search.setObjectDetailsUrl("city/"+cityDetails.getCityName());
				_search.setObjectIdName("cityName");
				_search.setObjectIdValue(cityDetails.getCityName());
				
				em.persist(_search);
				
			}catch(Exception e) {}
			
			
			em.flush();
			em.clear();
			return cityDetails;
		}else {
			throw new Exception("City Category does not match");
		}
		
		
	}
	
	public CityCatagory getCityCatagoryById(int cityCategoryId) {
		Query query=em.createQuery("from CityCatagory c where c.cityCategoryId=?1",CityCatagory.class);
		query.setParameter(1,cityCategoryId);
		try {
			CityCatagory catagory=(CityCatagory)query.getSingleResult();
			return catagory;
		}catch(Exception e) {}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CityDetailsResponse> advanceSearch(Map<String, Object> searchKeys, LoginUser user, List<String> userLists) {
		String _query="select new com.pratikshat.exicom.model.response.CityDetailsResponse(city) from CityDetails city left join city.cityCatagory cat where 1=1 ";
		StringBuilder queryBuilder=new StringBuilder();
		queryBuilder.append(_query);
		if(searchKeys.get("cityName")!=null&&!searchKeys.get("cityName").equals("")) {
			queryBuilder.append(" and city.cityName like :cityName");
		}
		if(searchKeys.get("cityCatagory")!=null&&!searchKeys.get("cityCatagory").equals("")) {
			queryBuilder.append(" and cat.cityCatagoryName like :cityCatagory");
		}
		
		Query query = em.createQuery(queryBuilder.toString(),CityDetailsResponse.class);
		
		if(searchKeys.get("cityName")!=null&&!searchKeys.get("cityName").equals("")) {
			query.setParameter("cityName","%"+searchKeys.get("cityName")+"%");
		}
		if(searchKeys.get("cityCatagory")!=null&&!searchKeys.get("cityCatagory").equals("")) {
			query.setParameter("cityCatagory", "%"+searchKeys.get("cityCatagory")+"%");
		}
		
		return query.getResultList();
	}

}
