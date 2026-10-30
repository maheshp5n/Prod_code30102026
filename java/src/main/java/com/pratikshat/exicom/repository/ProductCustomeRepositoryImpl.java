package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.model.response.PrcResponse;

@Repository
public class ProductCustomeRepositoryImpl implements ProductCustomeRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	@Transactional
	public List<com.pratikshat.exicom.model.response.Products> advanceSearch( Map<String,Object> request,List<String> users) {
		
		String queryString ="select new com.pratikshat.exicom.model.response.Products(p,pfs) from Products p left join p.subProductFamily pf left join pf.productFamily pfs left join p.createdBy cby left join p.modifiedBy mby where 1=1";
		
		
		if(request.get("partCode")!=null&&!request.get("partCode").equals("")) {
			queryString+=" and p.productPartCode like :partCode";
		}
		if(request.get("productFamily")!=null&&!request.get("productFamily").equals("")) {
			queryString+=" and pfs.productFamilyName like :productFamily";
		}
		if(request.get("productSubFamily")!=null&&!request.get("productSubFamily").equals("")) {
			queryString+=" and pf.productFailySubProductName like :productSubFamily";
		}
		if(request.get("type")!=null&&!request.get("type").equals("")) {
			queryString+=" and p.productType like :type";
		}
		if(request.get("description")!=null&&!request.get("description").equals("")) {
			queryString+=" and p.pdoductDescription like :description";
		}
		
		
		Query qry=entityManager.createQuery(queryString,com.pratikshat.exicom.model.response.Products.class);
		
		
		if(request.get("partCode")!=null&&!request.get("partCode").equals("")) {
			qry.setParameter("partCode","%"+ request.get("partCode")+"%");
		}
		if(request.get("productFamily")!=null&&!request.get("productFamily").equals("")) {
			qry.setParameter("productFamily","%"+ request.get("productFamily")+"%");
		}
		if(request.get("productSubFamily")!=null&&!request.get("productSubFamily").equals("")) {
			qry.setParameter("productSubFamily","%"+ request.get("productSubFamily")+"%");
		}
		if(request.get("type")!=null&&!request.get("type").equals("")) {
			qry.setParameter("type","%"+ request.get("type")+"%");
		}
		if(request.get("description")!=null&&!request.get("description").equals("")) {
			qry.setParameter("description","%"+ request.get("description")+"%");
		}
		
		List<com.pratikshat.exicom.model.response.Products> response= qry.getResultList();
																										
		 return response;
	}
}
