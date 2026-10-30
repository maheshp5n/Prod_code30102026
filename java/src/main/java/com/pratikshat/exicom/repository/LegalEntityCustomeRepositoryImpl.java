package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.LegalEntityResponse;

@Repository
public class LegalEntityCustomeRepositoryImpl implements LegalEntityCustomeRepository{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<LegalEntityResponse> advanceSearch(Map<String, Object> filterData, LoginUser user, int businessDomain)throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.request.LegalEntityResponse(l) from LegalEntity l left join l.createdBy cby left join l.modifiedBy mby left join l.legalEntityType typ left join l.accountOwner onr where l.businessDoamin.businessDomainId=:businessDomainId ");
			
			if(filterData.get("legalEntityName")!=null&&!filterData.get("legalEntityName").equals(""))
				queryStringBuilder.append(" and l.legalEntityName like :legalEntityName");
			if(filterData.get("type")!=null&&!filterData.get("type").equals(""))
				queryStringBuilder.append(" and typ.typeName like :type");
			
			Query qr=entityManager.createQuery(queryStringBuilder.toString(),LegalEntityResponse.class);
			
			qr.setParameter("businessDomainId", businessDomain);
			
			if(filterData.get("legalEntityName")!=null&&!filterData.get("legalEntityName").equals(""))
				qr.setParameter("legalEntityName","%"+ filterData.get("legalEntityName")+"%");
			if(filterData.get("type")!=null&&!filterData.get("type").equals(""))
				qr.setParameter("type", "%"+ filterData.get("type")+"%");
			
			return qr.getResultList();
		}
}
