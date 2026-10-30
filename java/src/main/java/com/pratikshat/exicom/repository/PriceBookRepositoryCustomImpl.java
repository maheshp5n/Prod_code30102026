package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PriceBook;
import com.pratikshat.exicom.entity.PriceBookEntries;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.PriceBookRequest;

@Repository
public class PriceBookRepositoryCustomImpl implements PriceBookRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public PriceBookRequest createPriceBook(PriceBookRequest priceBookRequest, LoginUser user) throws Exception {
		if(priceBookRequest!=null) {
			if(priceBookRequest.getName()!=null) {
				
				PriceBook priceBookExist =null;
				try {
					Query selectPriceBookEntryQuery=entityManager.createQuery("select pb from PriceBook pb where pb.name=?1",PriceBook.class);
					selectPriceBookEntryQuery.setParameter(1,priceBookRequest.getName());

					priceBookExist=(PriceBook)selectPriceBookEntryQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				
				if(priceBookExist!=null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Price book already exist with name "+priceBookRequest.getName());
				}else {
				
					PriceBook priceBook = new PriceBook();
					priceBook.setName(priceBookRequest.getName());
					priceBook.setDescription(priceBookRequest.getDescription());
					priceBook.setActive(priceBookRequest.isActive());
					priceBook.setStandardPriceBook(priceBookRequest.isStandardPriceBook());
					priceBook.setCreatedBy(new EmployeeDetails(user.getUsername()));
					priceBook.setCreatedDate(new Date());
					priceBook.setModifiedBy(new EmployeeDetails(user.getUsername()));
					priceBook.setModifiedDate(new Date());
					
					entityManager.persist(priceBook);
					priceBookRequest.setId(priceBook.getId());
					
					
					try {
						GlobalSearchIndex _search=new GlobalSearchIndex();
						_search.setSearchString(priceBook.getName());
						_search.setKeyName("name");
						_search.setObjectName("PriceBook");
						_search.setObjectDetailsUrl("pricebook/"+priceBook.getId());
						_search.setObjectIdName("id");
						_search.setObjectIdValue(String.valueOf(priceBook.getId()));
						
						entityManager.persist(_search);
						
					}catch(Exception e) {}
				}
				
				
				
				entityManager.flush();
				entityManager.clear();
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Request Parameter Null");
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Price book name is Null");
		}
		return priceBookRequest;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public PriceBookRequest updatePriceBook(PriceBookRequest priceBookRequest, LoginUser user) throws Exception {
		if(priceBookRequest!=null) {
			if(priceBookRequest.getId()!=0) {
				if(priceBookRequest.getName()!=null) {
					PriceBook priceBook =null;
					try {
						Query selectPriceBookQuery=entityManager.createQuery("select pb from PriceBook pb left join pb.createdBy createdby left join pb.modifiedBy modified where pb.id=?1",PriceBook.class);
						selectPriceBookQuery.setParameter(1,priceBookRequest.getId());
						priceBook=(PriceBook)selectPriceBookQuery.getSingleResult();
					}catch(NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Id Incorrect");
					}
					
					if(priceBook!=null) {
						priceBook.setName(priceBookRequest.getName());
						priceBook.setDescription(priceBookRequest.getDescription());
						priceBook.setActive(priceBookRequest.isActive());
						priceBook.setStandardPriceBook(priceBookRequest.isStandardPriceBook());
						priceBook.setModifiedBy(new EmployeeDetails(user.getUsername()));
						priceBook.setModifiedDate(new Date());
						
						entityManager.persist(priceBook);
						
						entityManager.flush();
						entityManager.clear();
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Id Incorrect");
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Price Book name is Null");
				}
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Id Incorrect");
			}
		}
		
		return priceBookRequest;
	}

}
