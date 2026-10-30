package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PriceBook;
import com.pratikshat.exicom.entity.PriceBookEntries;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.PriceBookEntriesRequest;

@Repository
public class PriceBookEntriesRepositoryCustomImpl implements PriceBookEntriesRepositoryCustom{

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public List<PriceBookEntriesRequest> insertEntries(List<PriceBookEntriesRequest> entries, LoginUser user)
			throws Exception {
		if(entries!=null && !entries.isEmpty()) {
			for(int i=0;i<entries.size();i++) {
				PriceBookEntriesRequest entry = entries.get(i);
				if(entry!=null) {
					if(entry.getPriceBookId()!=0) {
						if(entry.getPartCode()!=null) {
							
							PriceBookEntries entryExist =null;
							try {
								Query selectPriceBookEntryQuery=entityManager.createQuery("select pbe from PriceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb where prd.productPartCode=?1 and pb.id=?2",PriceBookEntries.class);
								selectPriceBookEntryQuery.setParameter(1,entry.getPartCode());
								selectPriceBookEntryQuery.setParameter(2,entry.getPriceBookId());
								entryExist=(PriceBookEntries)selectPriceBookEntryQuery.getSingleResult();
							}catch(NoResultException ex) {
								
							}
							
							
							if(entryExist!=null) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Part Code "+entryExist.getProduct().getProductPartCode()+ " already exist");
							}else {
								PriceBookEntries priceBookEntry = new PriceBookEntries();
								priceBookEntry.setProduct(new Products(entry.getPartCode()));
								priceBookEntry.setPriceBook(new PriceBook(entry.getPriceBookId()));
								priceBookEntry.setActive(entry.isActive());
								priceBookEntry.setListPrice(entry.getListPrice());
								priceBookEntry.setCreatedBy(new EmployeeDetails(user.getUsername()));
								priceBookEntry.setCreatedDate(new Date());
								priceBookEntry.setModifiedBy(new EmployeeDetails(user.getUsername()));
								priceBookEntry.setModifiedDate(new Date());
								
								entityManager.persist(priceBookEntry);
							}
							
							
							
						}else {
							throw new com.pratikshat.exicom.exception.BadRequestException("Entry does not have correct Part Code at position "+i);
						}
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Entry does not have correct Price Book Id at position "+i);
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Entry null at position "+i);
				}
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Request Parameter Null");
		}
		entityManager.flush();
		entityManager.clear();
		return entries;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public PriceBookEntriesRequest updateEntry(PriceBookEntriesRequest entry, LoginUser user) throws Exception {
		if(entry!=null) {
					if(entry.getPriceBookId()!=0) {
						if(entry.getPartCode()!=null) {
							PriceBookEntries priceBookEntry =null;
							try {
								Query selectPriceBookEntryQuery=entityManager.createQuery("select pbe from PriceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb  left join pbe.createdBy createdby left join pbe.modifiedBy modified where pbe.id=?1",PriceBookEntries.class);
								selectPriceBookEntryQuery.setParameter(1,entry.getId());
								priceBookEntry=(PriceBookEntries)selectPriceBookEntryQuery.getSingleResult();
							}catch(NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Id Incorrect");
							}
							
							if(priceBookEntry!=null) {
								priceBookEntry.setActive(entry.isActive());
								priceBookEntry.setListPrice(entry.getListPrice());
								
								priceBookEntry.setModifiedBy(new EmployeeDetails(user.getUsername()));
								priceBookEntry.setModifiedDate(new Date());
								
								entityManager.persist(priceBookEntry);
							}else {
								throw new com.pratikshat.exicom.exception.BadRequestException("Entry does not have correct Id");
							}
						}else {
							throw new com.pratikshat.exicom.exception.BadRequestException("Entry does not have correct Part Code");
						}
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Entry does not have correct Price Book Id");
					}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Request Parameter Null");
		}
	
		return entry;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public PriceBookEntriesRequest insertEntry(PriceBookEntriesRequest entry, LoginUser user) throws Exception {
		
		if(entry!=null) {
					if(entry.getPriceBookId()!=0) {
						if(entry.getPartCode()!=null) {
							
							PriceBookEntries entryExist =null;
							try {
								Query selectPriceBookEntryQuery=entityManager.createQuery("select pbe from PriceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb where prd.productPartCode=?1 and pb.id=?2",PriceBookEntries.class);
								selectPriceBookEntryQuery.setParameter(1,entry.getPartCode());
								selectPriceBookEntryQuery.setParameter(2,entry.getPriceBookId());
								entryExist=(PriceBookEntries)selectPriceBookEntryQuery.getSingleResult();
							}catch(NoResultException ex) {
								
							}
							
							
							if(entryExist!=null) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Part Code "+entryExist.getProduct().getProductPartCode()+ " already exist");
							}else {
								PriceBookEntries priceBookEntry = new PriceBookEntries();
								priceBookEntry.setProduct(new Products(entry.getPartCode()));
								priceBookEntry.setPriceBook(new PriceBook(entry.getPriceBookId()));
								priceBookEntry.setActive(entry.isActive());
								priceBookEntry.setListPrice(entry.getListPrice());
								priceBookEntry.setCreatedBy(new EmployeeDetails(user.getUsername()));
								priceBookEntry.setCreatedDate(new Date());
								priceBookEntry.setModifiedBy(new EmployeeDetails(user.getUsername()));
								priceBookEntry.setModifiedDate(new Date());
								
								entityManager.persist(priceBookEntry);
							}
							
							
							
						}else {
							throw new com.pratikshat.exicom.exception.BadRequestException("Entry does not have correct Part Code");
						}
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Entry does not have correct Price Book Id");
					}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Request Parameter Null");
		}
		return entry;
	}

}
