package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.IGSTBillingState;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Oppertunity;
import com.pratikshat.exicom.entity.OppertunityForecastCategory;
import com.pratikshat.exicom.entity.OppertunityLineItem;
import com.pratikshat.exicom.entity.OppertunityStage;
import com.pratikshat.exicom.entity.OppertunityType;
import com.pratikshat.exicom.entity.PriceBook;
import com.pratikshat.exicom.entity.PriceBookEntries;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.QuoteLineItem;
import com.pratikshat.exicom.entity.TaxAndVisitCharges;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.OppertunityLineItemRequest;
import com.pratikshat.exicom.model.request.OppertunityRequest;
import com.pratikshat.exicom.model.request.QuoteLineItemSumQueryResponse;
import com.pratikshat.exicom.model.response.PrcResponse;

@Repository
public class OppertunityRepositoryCustomImpl implements OppertunityRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	//@Autowired
	//OppertunityRepository dbOppertunityRepository; 

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public OppertunityRequest saveOppertunity(OppertunityRequest oppertunityRequest, LoginUser user) throws Exception {
		if (oppertunityRequest != null) {
			
			List<Long> oprStage = new ArrayList<Long>();
			oprStage.add(1L); // Proposed/Price Quote
			oprStage.add(2L); // Closed Won
			
			Oppertunity oppertunity = null;
			try {
				Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr left join opr.caseDetails cd where opr.isDeleted=false and cd.caseId=?1 and opr.oppertunityStage.oppertunityStageId in ?2",Oppertunity.class);
				selectOppertunityQuery.setParameter(1,oppertunityRequest.getCaseId());
				selectOppertunityQuery.setParameter(2,oprStage);
				oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
			}catch(NoResultException ex) {
				
			}
			if(oppertunity!=null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity already exist on this Case");
			}
			
			
			CaseDetails caseDetails = null;
			try {
				Query selectCaseQuery=entityManager.createQuery("select cd from CaseDetails cd where cd.caseId=?1",CaseDetails.class);
				selectCaseQuery.setParameter(1,oppertunityRequest.getCaseId());
				caseDetails=(CaseDetails)selectCaseQuery.getSingleResult();
			}catch(NoResultException ex) {
				
			}
			if(caseDetails==null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			
			
			Oppertunity _oppertunity = new Oppertunity();
			_oppertunity.setAccounts(new Accounts(oppertunityRequest.getAccountId()));
			_oppertunity.setCaseDetails(new CaseDetails(oppertunityRequest.getCaseId()));
			_oppertunity.setCreateDate(new Date());
			_oppertunity.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_oppertunity.setForcastCategory(new OppertunityForecastCategory(oppertunityRequest.getForcastCategoryId()));
			_oppertunity.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_oppertunity.setModifiedDate(new Date());
			_oppertunity.setOppertunityStage(new OppertunityStage(oppertunityRequest.getOppertunityStageId()));
			_oppertunity.setOppertunityType(new OppertunityType(oppertunityRequest.getOppertunityTypeId()));
			_oppertunity.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_oppertunity.setProbability(oppertunityRequest.getProbability());
			_oppertunity.setSyncFlag(false);
			
			entityManager.persist(_oppertunity);
			
			Query updateManualId=entityManager.createQuery("update Oppertunity op set op.oppertunityNumber=?1 where op.oppertunityId=?2");
			SimpleDateFormat dateformatter=new SimpleDateFormat("yyyyMMdd");
			//String manualId= "OP-"+dateformatter.format(new java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_oppertunity.getOppertunityId()),5);
			String caseManualId = caseDetails.getManualId();
			String manualId=null;
			if(caseManualId.split("-").length==2) {
				manualId= "OP-"+caseManualId.split("-")[1];
				updateManualId.setParameter(1,manualId);
				updateManualId.setParameter(2, _oppertunity.getOppertunityId());
				updateManualId.executeUpdate();
			}else {
				manualId= "OP-"+caseManualId.split("-")[2]+"-"+caseManualId.split("-")[3];
				updateManualId.setParameter(1,manualId);
				updateManualId.setParameter(2, _oppertunity.getOppertunityId());
				updateManualId.executeUpdate();	
			}
			
			
			oppertunityRequest.setOppertunityId(_oppertunity.getOppertunityId());
			
			//search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search=new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("oppertunityNumber");
				_search.setObjectDetailsUrl("Opportunities/"+_oppertunity.getOppertunityId());
				_search.setObjectIdName("oppertunityId");
				_search.setObjectIdValue(""+_oppertunity.getOppertunityId());
				_search.setObjectName("Oppertunity");
				_search.setSearchString(manualId);
				entityManager.persist(_search);
				
			}catch(Exception e) {}
			entityManager.flush();
			entityManager.clear();
			return oppertunityRequest;
		} else {
			throw new Exception("No data found to save details");
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public List<OppertunityLineItemRequest> addLineItem(List<OppertunityLineItemRequest> lineItemList, long priceBookId, long oppertunityId, int businessDomainId, LoginUser user)
			throws Exception {
		
		Oppertunity oppertunityExist = null;
		try {
			Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",Oppertunity.class);
			selectOppertunityQuery.setParameter(1,oppertunityId);
			oppertunityExist=(Oppertunity)selectOppertunityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(oppertunityExist==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}
		
		List<Long> oprStage = new ArrayList<Long>();
		oprStage.add(1L); // "Proposed/Price Quote"
		
		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1 and opr.oppertunityStage.oppertunityStageId in ?2",Oppertunity.class);
			selectOppertunityQuery.setParameter(1,oppertunityId);
			selectOppertunityQuery.setParameter(2,oprStage);
			oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(oppertunity==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You can't update this record");
		}
		
		
		PriceBook priceBook = null;
		try {
			Query selectPriceBookQuery=entityManager.createQuery("select pb from PriceBook pb where pb.id=?1",PriceBook.class);
			selectPriceBookQuery.setParameter(1,priceBookId);
			priceBook=(PriceBook)selectPriceBookQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(priceBook==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Price Book does not exist");
		}
		
		Quote quoteExist = null;
		try {
			Query selectQuoteQuery=entityManager.createQuery("select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where opr.oppertunityId=?1 order by qt.createdDate desc",Quote.class);
			selectQuoteQuery.setParameter(1,oppertunityId);
			quoteExist=(Quote)selectQuoteQuery.getResultList().get(0);
		}catch(NoResultException ex) {
			
		}catch(IndexOutOfBoundsException e) {
			
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(4); // Quote Sent
		quoteStatusList.add(6); // Submitted For Approval
		quoteStatusList.add(7); // Approved
		quoteStatusList.add(8);	// Invoiced
		quoteStatusList.add(10);// Approved by Store
		
		if(quoteExist!=null && quoteExist.getQuoteStatus()!=null) {
			if(quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Can't update because quote not in the draft/Reject mode");
			}
		}
		
		//float totalAmount = 0;
		if(oppertunity.isSyncFlag()) {
			
			
			if(quoteExist!=null) {
				
				TaxAndVisitCharges taxAndVisitCharges  = null;
				try {
					Query selectTaxAndVisitChargesQuery=entityManager.createQuery("select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",TaxAndVisitCharges.class);
					selectTaxAndVisitChargesQuery.setParameter(1,businessDomainId);
					taxAndVisitCharges=(TaxAndVisitCharges)selectTaxAndVisitChargesQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(taxAndVisitCharges==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
				}
				
				
				IGSTBillingState igstBillingState  = null;
				try {
					Query selectTaxAndVisitChargesQuery=entityManager.createQuery("select bs from IGSTBillingState bs where bs.stateName=?1",IGSTBillingState.class);
					selectTaxAndVisitChargesQuery.setParameter(1,quoteExist.getBillingState().getStateName());
					igstBillingState=(IGSTBillingState)selectTaxAndVisitChargesQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				
				//float quoteTotalAmount = 0;
				//float quoteCGSTAmount = 0;
				//float quoteSGSTAmount = 0;
				//float quoteIGSTAmount = 0;
				//float quoteTotalAmountWithTax = 0;
				
				for(int i=0; i<lineItemList.size(); i++) {
					OppertunityLineItemRequest request = lineItemList.get(i);
					
					PriceBookEntries priceBookEntries = null;
					try {
						Query selectPriceBookEntriesQuery=entityManager.createQuery("select pbe from PriceBookEntries pbe where pbe.id=?1",PriceBookEntries.class);
						selectPriceBookEntriesQuery.setParameter(1,request.getPriceBookEntryId());
						priceBookEntries=(PriceBookEntries)selectPriceBookEntriesQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(priceBookEntries==null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Product does not exist");
					}
					
					OppertunityLineItem oppertunityLineItem = null;
					try {
						Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where opr.oppertunityId=?1 and prd.productPartCode=?2 and oli.isDeleted=?3",OppertunityLineItem.class);
						selectOppertunityLineItemQuery.setParameter(1,oppertunity.getOppertunityId());
						selectOppertunityLineItemQuery.setParameter(2,request.getPartCode());
						selectOppertunityLineItemQuery.setParameter(3,false);
						oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(oppertunityLineItem!=null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Line Item "+request.getPartCode()+" already exist in this oppertunity");
					}
					
					OppertunityLineItem item = new OppertunityLineItem();
					item.setOppertunity(oppertunity);
					item.setPriceBookEntries(priceBookEntries);
					
					double price = request.getPrice();
					double quantity = request.getQuantity();
					
					double totalPrice = price*quantity;
					
					item.setPrice(price);
					item.setQuantity(quantity);
					item.setTotalPrice(totalPrice);
					item.setCreatedBy(new EmployeeDetails(user.getUsername()));
					item.setCreatedDate(new Date());
					item.setModifiedBy(new EmployeeDetails(user.getUsername()));
					item.setModifiedDate(new Date());
					//totalAmount = totalAmount+totalPrice;
					
					entityManager.persist(item);
					
					
					QuoteLineItem quoteItem = new QuoteLineItem();
					quoteItem.setQuote(quoteExist);
					quoteItem.setPriceBookEntries(priceBookEntries);
					
					quoteItem.setPrice(price);
					quoteItem.setQuantity(quantity);
					quoteItem.setTotalPrice(totalPrice);
					
					//quoteTotalAmount = quoteTotalAmount+totalPrice;
					
					if(igstBillingState==null) {
						double cgstAmount = (totalPrice*taxAndVisitCharges.getCgst())/100;
						double sgstAmount = (totalPrice*taxAndVisitCharges.getSgst())/100;
						
						//quoteCGSTAmount = quoteCGSTAmount+cgstAmount;
						//quoteSGSTAmount = quoteSGSTAmount+sgstAmount;
						
						quoteItem.setCgstAmount(cgstAmount);
						quoteItem.setSgstAmount(sgstAmount);
						
						
						double amountWithTax = totalPrice+cgstAmount+sgstAmount;
						quoteItem.setTotalPriceWithTax(amountWithTax);
						quoteItem.setTotalRepairPrice(amountWithTax);
					}else {
						double igstAmount = (totalPrice*taxAndVisitCharges.getIgst())/100;
						
						//quoteIGSTAmount = quoteIGSTAmount + igstAmount;
						
						double amountWithTax = totalPrice+igstAmount;
						//quoteTotalAmountWithTax = quoteTotalAmountWithTax+amountWithTax;
						quoteItem.setIgstAmount(igstAmount);
						quoteItem.setTotalPriceWithTax(amountWithTax);
						quoteItem.setTotalRepairPrice(amountWithTax);
					}
					quoteItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
					quoteItem.setCreatedDate(new Date());
					quoteItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					quoteItem.setModifiedDate(new Date());
					entityManager.persist(quoteItem);
				}
				
				Object[] amount = null;
				try {
					Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
					sumOppertunityQuery.setParameter(1,quoteExist.getId());
					amount  = (Object[])sumOppertunityQuery.getSingleResult();
				}catch(NoResultException ex) {

				}
				
				if(amount!=null) {
					quoteExist.setSubTotal((double) amount[0]);
					quoteExist.setCgstAmount((double) amount[1]);
					quoteExist.setSgstAmount((double) amount[2]);
					quoteExist.setIgstAmount((double) amount[3]);
					quoteExist.setDiscount(0);
					quoteExist.setTotalAmountWithTax((double) amount[4]);
					quoteExist.setTotalAmountAfterDiscount((double)amount[4]);
					
					quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
					quoteExist.setModifiedDate(new Date());
					
					entityManager.persist(quoteExist);	
				}else {
					
				}
				
				
			}else {
				for(int i=0; i<lineItemList.size(); i++) {
					OppertunityLineItemRequest request = lineItemList.get(i);
					
					
					OppertunityLineItem oppertunityLineItem = null;
					try {
						Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where opr.oppertunityId=?1 and prd.productPartCode=?2 and oli.isDeleted=?3",OppertunityLineItem.class);
						selectOppertunityLineItemQuery.setParameter(1,oppertunity.getOppertunityId());
						selectOppertunityLineItemQuery.setParameter(2,request.getPartCode());
						selectOppertunityLineItemQuery.setParameter(3,false);
						oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(oppertunityLineItem!=null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Line Item "+request.getPartCode()+" already exist in this oppertunity");
					}
					
					
					PriceBookEntries priceBookEntries = null;
					try {
						Query selectPriceBookEntriesQuery=entityManager.createQuery("select pbe from PriceBookEntries pbe where pbe.id=?1",PriceBookEntries.class);
						selectPriceBookEntriesQuery.setParameter(1,request.getPriceBookEntryId());
						priceBookEntries=(PriceBookEntries)selectPriceBookEntriesQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(priceBookEntries==null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Product does not exist");
					}
					
					OppertunityLineItem item = new OppertunityLineItem();
					item.setOppertunity(oppertunity);
					item.setPriceBookEntries(priceBookEntries);
					
					double price = request.getPrice();
					double quantity = request.getQuantity();
					
					double totalPrice = price*quantity;
					
					item.setPrice(price);
					item.setQuantity(quantity);
					item.setTotalPrice(totalPrice);
					
					item.setCreatedBy(new EmployeeDetails(user.getUsername()));
					item.setCreatedDate(new Date());
					item.setModifiedBy(new EmployeeDetails(user.getUsername()));
					item.setModifiedDate(new Date());
					//totalAmount = totalAmount+totalPrice;
					
					entityManager.persist(item);
					
				}
			}
		}else {
			for(int i=0; i<lineItemList.size(); i++) {
				OppertunityLineItemRequest request = lineItemList.get(i);
				
				
				OppertunityLineItem oppertunityLineItem = null;
				try {
					Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where opr.oppertunityId=?1 and prd.productPartCode=?2 and oli.isDeleted=?3",OppertunityLineItem.class);
					selectOppertunityLineItemQuery.setParameter(1,oppertunity.getOppertunityId());
					selectOppertunityLineItemQuery.setParameter(2,request.getPartCode());
					selectOppertunityLineItemQuery.setParameter(3,false);
					oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(oppertunityLineItem!=null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Line Item "+request.getPartCode()+" already exist in this oppertunity");
				}
				
				
				PriceBookEntries priceBookEntries = null;
				try {
					Query selectPriceBookEntriesQuery=entityManager.createQuery("select pbe from PriceBookEntries pbe where pbe.id=?1",PriceBookEntries.class);
					selectPriceBookEntriesQuery.setParameter(1,request.getPriceBookEntryId());
					priceBookEntries=(PriceBookEntries)selectPriceBookEntriesQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(priceBookEntries==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Product does not exist");
				}
				
				OppertunityLineItem item = new OppertunityLineItem();
				item.setOppertunity(oppertunity);
				item.setPriceBookEntries(priceBookEntries);
				
				double price = request.getPrice();
				double quantity = request.getQuantity();
				
				double totalPrice = price*quantity;
				
				item.setPrice(price);
				item.setQuantity(quantity);
				item.setTotalPrice(totalPrice);
				
				//totalAmount = totalAmount+totalPrice;
				
				item.setCreatedBy(new EmployeeDetails(user.getUsername()));
				item.setCreatedDate(new Date());
				item.setModifiedBy(new EmployeeDetails(user.getUsername()));
				item.setModifiedDate(new Date());
				
				entityManager.persist(item);
				
			}
		}
		
		try {
			Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1",Double.class);
			sumOppertunityQuery.setParameter(1,oppertunityId);
			Double totalAmount  = (Double)sumOppertunityQuery.getSingleResult();
			oppertunity.setAmmount(totalAmount);
			oppertunity.setExpectedRevenue(totalAmount);
			oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunity.setModifiedDate(new Date());
			
			entityManager.persist(oppertunity);
		}catch(NoResultException ex) {
			
		}
		
		
		
		
		entityManager.flush();
		entityManager.clear();
		
		return lineItemList;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public OppertunityLineItemRequest updateLineItem(OppertunityLineItemRequest lineItem, long priceBookId, long oppertunityId, int businessDomainId, LoginUser user) throws Exception{
		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",Oppertunity.class);
			selectOppertunityQuery.setParameter(1,oppertunityId);
			oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(oppertunity==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}
		
		
		PriceBook priceBook = null;
		try {
			Query selectPriceBookQuery=entityManager.createQuery("select pb from PriceBook pb where pb.id=?1",PriceBook.class);
			selectPriceBookQuery.setParameter(1,priceBookId);
			priceBook=(PriceBook)selectPriceBookQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(priceBook==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Price Book does not exist");
		}
		
		Quote quoteExist = null;
		try {
			Query selectQuoteQuery=entityManager.createQuery("select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where opr.oppertunityId=?1 order by qt.createdDate desc",Quote.class);
			selectQuoteQuery.setParameter(1,oppertunityId);
			quoteExist=(Quote)selectQuoteQuery.getResultList().get(0);
		}catch(NoResultException ex) {
			
		}catch(IndexOutOfBoundsException e) {
			
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(4); // Quote Sent
		quoteStatusList.add(6); // Submitted For Approval
		quoteStatusList.add(7); // Approved
		quoteStatusList.add(8);	// Invoiced
		quoteStatusList.add(10);// Approved by Store
		
		if(quoteExist!=null && quoteExist.getQuoteStatus()!=null) {
			if(quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Can't update because quote not in the draft/Reject mode");
			}
		}
		
		if(oppertunity.isSyncFlag()) {
			
			if(quoteExist!=null) {
				
				TaxAndVisitCharges taxAndVisitCharges  = null;
				try {
					Query selectTaxAndVisitChargesQuery=entityManager.createQuery("select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",TaxAndVisitCharges.class);
					selectTaxAndVisitChargesQuery.setParameter(1,businessDomainId);
					taxAndVisitCharges=(TaxAndVisitCharges)selectTaxAndVisitChargesQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(taxAndVisitCharges==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
				}
				
				
				IGSTBillingState igstBillingState  = null;
				try {
					Query selectTaxAndVisitChargesQuery=entityManager.createQuery("select bs from IGSTBillingState bs where bs.stateName=?1",IGSTBillingState.class);
					selectTaxAndVisitChargesQuery.setParameter(1,quoteExist.getBillingState().getStateName());
					igstBillingState=(IGSTBillingState)selectTaxAndVisitChargesQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				
				
				OppertunityLineItem oppertunityLineItem = null;
				try {
					Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where oli.id=?1 and oli.isDeleted=?2",OppertunityLineItem.class);
					selectOppertunityLineItemQuery.setParameter(1,lineItem.getId());
					selectOppertunityLineItemQuery.setParameter(2,false);
					oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(oppertunityLineItem==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
				}
				
				
				double price = lineItem.getPrice();
				double quantity = lineItem.getQuantity();
				
				double totalPrice = price*quantity;
				
				oppertunityLineItem.setPrice(price);
				oppertunityLineItem.setQuantity(quantity);
				oppertunityLineItem.setTotalPrice(totalPrice);
				
				oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunityLineItem.setModifiedDate(new Date());
				
				entityManager.persist(oppertunityLineItem);

				
				QuoteLineItem quoteLineItem = null;
				try {
					Query selectQuoteLineItemQuery=entityManager.createQuery("select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pb left join pb.product prd where qt.id=?1 and prd.productPartCode=?2 and qli.isDeleted=?3",QuoteLineItem.class);
					selectQuoteLineItemQuery.setParameter(1,quoteExist.getId());
					selectQuoteLineItemQuery.setParameter(2,oppertunityLineItem.getPriceBookEntries().getProduct().getProductPartCode());
					selectQuoteLineItemQuery.setParameter(3,false);
					quoteLineItem=(QuoteLineItem)selectQuoteLineItemQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(quoteLineItem==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
				}
				
				quoteLineItem.setPrice(price);
				quoteLineItem.setQuantity(quantity);
				quoteLineItem.setTotalPrice(totalPrice);
				
				//quoteTotalAmount = quoteTotalAmount+totalPrice;
				
				if(igstBillingState==null) {
					double cgstAmount = (totalPrice*taxAndVisitCharges.getCgst())/100;
					double sgstAmount = (totalPrice*taxAndVisitCharges.getSgst())/100;
					
					//quoteCGSTAmount = quoteCGSTAmount+cgstAmount;
					//quoteSGSTAmount = quoteSGSTAmount+sgstAmount;
					
					quoteLineItem.setCgstAmount(cgstAmount);
					quoteLineItem.setSgstAmount(sgstAmount);
					
					
					double amountWithTax = totalPrice+cgstAmount+sgstAmount;
					quoteLineItem.setTotalPriceWithTax(amountWithTax);
					quoteLineItem.setTotalRepairPrice(amountWithTax);
				}else {
					double igstAmount = (totalPrice*taxAndVisitCharges.getIgst())/100;
					
					//quoteIGSTAmount = quoteIGSTAmount + igstAmount;
					
					double amountWithTax = totalPrice+igstAmount;
					//quoteTotalAmountWithTax = quoteTotalAmountWithTax+amountWithTax;
					quoteLineItem.setIgstAmount(igstAmount);
					quoteLineItem.setTotalPriceWithTax(amountWithTax);
					quoteLineItem.setTotalRepairPrice(amountWithTax);
				}
				
				quoteLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				quoteLineItem.setModifiedDate(new Date());
				
				entityManager.persist(quoteLineItem);
				
				Object[] amount = null;
				try {
					Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
					sumOppertunityQuery.setParameter(1,quoteExist.getId());
					amount  = (Object[])sumOppertunityQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				
				if(amount!=null) {
					quoteExist.setCgstAmount((double)amount[1]);
					quoteExist.setIgstAmount((double)amount[3]);
					quoteExist.setSgstAmount((double)amount[2]);
					quoteExist.setSubTotal((double)amount[0]);
					quoteExist.setDiscount(0);
					quoteExist.setTotalAmountWithTax((double)amount[4]);
					quoteExist.setTotalAmountAfterDiscount((double)amount[4]);
					
					quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
					quoteExist.setModifiedDate(new Date());
					
					entityManager.persist(quoteExist);	
				}else {
					
				}
				
				
			}else {
				
				OppertunityLineItem oppertunityLineItem = null;
				try {
					Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where oli.id=?1 and oli.isDeleted=?2",OppertunityLineItem.class);
					selectOppertunityLineItemQuery.setParameter(1,lineItem.getId());
					selectOppertunityLineItemQuery.setParameter(2,false);
					oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(oppertunityLineItem==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
				}
				
				
				
				double price = lineItem.getPrice();
				double quantity = lineItem.getQuantity();
				
				double totalPrice = price*quantity;
				
				oppertunityLineItem.setPrice(price);
				oppertunityLineItem.setQuantity(quantity);
				oppertunityLineItem.setTotalPrice(totalPrice);
				
				oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunityLineItem.setModifiedDate(new Date());
				
				entityManager.persist(oppertunityLineItem);
				
			}
		}else {
			
			OppertunityLineItem oppertunityLineItem = null;
			try {
				Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where oli.id=?1 and oli.isDeleted=?2",OppertunityLineItem.class);
				selectOppertunityLineItemQuery.setParameter(1,lineItem.getId());
				selectOppertunityLineItemQuery.setParameter(2,false);
				oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
			}catch(NoResultException ex) {
				
			}
			if(oppertunityLineItem==null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
			}
			
			double price = lineItem.getPrice();
			double quantity = lineItem.getQuantity();
			
			double totalPrice = price*quantity;
			
			oppertunityLineItem.setPrice(price);
			oppertunityLineItem.setQuantity(quantity);
			oppertunityLineItem.setTotalPrice(totalPrice);
			
			oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunityLineItem.setModifiedDate(new Date());
			
			entityManager.persist(oppertunityLineItem);
			
		}
		
		try {
			Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1",Double.class);
			sumOppertunityQuery.setParameter(1,oppertunityId);
			Double totalAmount  = (Double)sumOppertunityQuery.getSingleResult();
			
			oppertunity.setAmmount(totalAmount);
			oppertunity.setExpectedRevenue(totalAmount);
			
			oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunity.setModifiedDate(new Date());
			
			entityManager.persist(oppertunity);
		}catch(NoResultException ex) {
			
		}
		
		
		entityManager.flush();
		entityManager.clear();
		
		return lineItem;
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public List<OppertunityLineItemRequest> updateLineItemList(List<OppertunityLineItemRequest> lineItemList,
			long priceBookId, long oppertunityId, int businessDomainId, LoginUser user) throws Exception {
		
		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",Oppertunity.class);
			selectOppertunityQuery.setParameter(1,oppertunityId);
			oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(oppertunity==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}
		
		
		PriceBook priceBook = null;
		try {
			Query selectPriceBookQuery=entityManager.createQuery("select pb from PriceBook pb where pb.id=?1",PriceBook.class);
			selectPriceBookQuery.setParameter(1,priceBookId);
			priceBook=(PriceBook)selectPriceBookQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(priceBook==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Price Book does not exist");
		}
		
		Quote quoteExist = null;
		try {
			Query selectQuoteQuery=entityManager.createQuery("select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where opr.oppertunityId=?1 order by qt.createdDate desc",Quote.class);
			selectQuoteQuery.setParameter(1,oppertunityId);
			quoteExist=(Quote)selectQuoteQuery.getResultList().get(0);
		}catch(NoResultException ex) {
			
		}catch(IndexOutOfBoundsException e) {
			
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(4); // Quote Sent
		quoteStatusList.add(6); // Submitted For Approval
		quoteStatusList.add(7); // Approved
		quoteStatusList.add(8);	// Invoiced
		quoteStatusList.add(10);// Approved by Store
		
		if(quoteExist!=null && quoteExist.getQuoteStatus()!=null) {
			if(quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Can't update because quote not in the draft/Reject mode");
			}
		}
		
		if(oppertunity.isSyncFlag()) {
			
			if(quoteExist!=null) {
				
				TaxAndVisitCharges taxAndVisitCharges  = null;
				try {
					Query selectTaxAndVisitChargesQuery=entityManager.createQuery("select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",TaxAndVisitCharges.class);
					selectTaxAndVisitChargesQuery.setParameter(1,businessDomainId);
					taxAndVisitCharges=(TaxAndVisitCharges)selectTaxAndVisitChargesQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(taxAndVisitCharges==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
				}
				
				
				IGSTBillingState igstBillingState  = null;
				try {
					Query selectTaxAndVisitChargesQuery=entityManager.createQuery("select bs from IGSTBillingState bs where bs.stateName=?1",IGSTBillingState.class);
					selectTaxAndVisitChargesQuery.setParameter(1,quoteExist.getBillingState().getStateName());
					igstBillingState=(IGSTBillingState)selectTaxAndVisitChargesQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				
				for(int i=0; i<lineItemList.size(); i++) {
					OppertunityLineItemRequest oppertunityLineItemRequest = lineItemList.get(i);
					
					OppertunityLineItem oppertunityLineItem = null;
					try {
						Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where oli.id=?1 and oli.isDeleted=?2",OppertunityLineItem.class);
						selectOppertunityLineItemQuery.setParameter(1,oppertunityLineItemRequest.getId());
						selectOppertunityLineItemQuery.setParameter(2,false);
						oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(oppertunityLineItem==null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
					}
					
					double price = oppertunityLineItemRequest.getPrice();
					double quantity = oppertunityLineItemRequest.getQuantity();
					
					double totalPrice = price*quantity;
					
					oppertunityLineItem.setPrice(price);
					oppertunityLineItem.setQuantity(quantity);
					oppertunityLineItem.setTotalPrice(totalPrice);
					
					oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					oppertunityLineItem.setModifiedDate(new Date());
					
					entityManager.persist(oppertunityLineItem);	
					
					
					QuoteLineItem quoteLineItem = null;
					try {
						Query selectQuoteLineItemQuery=entityManager.createQuery("select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pb left join pb.product prd where qt.id=?1 and prd.productPartCode=?2 and qli.isDeleted=?3",QuoteLineItem.class);
						selectQuoteLineItemQuery.setParameter(1,quoteExist.getId());
						selectQuoteLineItemQuery.setParameter(2,oppertunityLineItem.getPriceBookEntries().getProduct().getProductPartCode());
						selectQuoteLineItemQuery.setParameter(3,false);
						quoteLineItem=(QuoteLineItem)selectQuoteLineItemQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(quoteLineItem==null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
					}
					
					quoteLineItem.setPrice(price);
					quoteLineItem.setQuantity(quantity);
					quoteLineItem.setTotalPrice(totalPrice);
					
					
					if(igstBillingState==null) {
						double cgstAmount = (totalPrice*taxAndVisitCharges.getCgst())/100;
						double sgstAmount = (totalPrice*taxAndVisitCharges.getSgst())/100;
						
						//quoteCGSTAmount = quoteCGSTAmount+cgstAmount;
						//quoteSGSTAmount = quoteSGSTAmount+sgstAmount;
						
						quoteLineItem.setCgstAmount(cgstAmount);
						quoteLineItem.setSgstAmount(sgstAmount);
						
						
						double amountWithTax = totalPrice+cgstAmount+sgstAmount;
						quoteLineItem.setTotalPriceWithTax(amountWithTax);
						quoteLineItem.setTotalRepairPrice(amountWithTax);
					}else {
						double igstAmount = (totalPrice*taxAndVisitCharges.getIgst())/100;
						
						//quoteIGSTAmount = quoteIGSTAmount + igstAmount;
						
						double amountWithTax = totalPrice+igstAmount;
						//quoteTotalAmountWithTax = quoteTotalAmountWithTax+amountWithTax;
						quoteLineItem.setIgstAmount(igstAmount);
						quoteLineItem.setTotalPriceWithTax(amountWithTax);
						quoteLineItem.setTotalRepairPrice(amountWithTax);
					}
					
					quoteLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					quoteLineItem.setModifiedDate(new Date());
					
					entityManager.persist(quoteLineItem);
					
					
					Object[] amount = null;
					try {
						Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
						sumOppertunityQuery.setParameter(1,quoteExist.getId());
						amount  = (Object[])sumOppertunityQuery.getSingleResult();
					}catch(NoResultException ex) {

					}
					
					if (amount != null) {
						quoteExist.setSubTotal((double) amount[0]);
						quoteExist.setCgstAmount((double) amount[1]);
						quoteExist.setSgstAmount((double) amount[2]);
						quoteExist.setIgstAmount((double) amount[3]);
						quoteExist.setDiscount(0);
						quoteExist.setTotalAmountWithTax((double) amount[4]);
						quoteExist.setTotalAmountAfterDiscount((double)amount[4]);

						quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
						quoteExist.setModifiedDate(new Date());

						entityManager.persist(quoteExist);
					} else {

					}
				}
			}else {
				
				for(int i=0; i<lineItemList.size(); i++) {
					OppertunityLineItemRequest oppertunityLineItemRequest = lineItemList.get(i);
				
					OppertunityLineItem oppertunityLineItem = null;
					try {
						Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where oli.id=?1 and and oli.isDeleted=?2",OppertunityLineItem.class);
						selectOppertunityLineItemQuery.setParameter(1,oppertunityLineItemRequest.getId());
						selectOppertunityLineItemQuery.setParameter(2,false);
						oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(oppertunityLineItem==null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
					}
					
					double price = oppertunityLineItemRequest.getPrice();
					double quantity = oppertunityLineItemRequest.getQuantity();
					
					double totalPrice = price*quantity;
					
					oppertunityLineItem.setPrice(price);
					oppertunityLineItem.setQuantity(quantity);
					oppertunityLineItem.setTotalPrice(totalPrice);
					
					oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					oppertunityLineItem.setModifiedDate(new Date());
					
					entityManager.persist(oppertunityLineItem);
				}	
			}
		}else {
			
			for(int i=0; i<lineItemList.size(); i++) {
				OppertunityLineItemRequest oppertunityLineItemRequest = lineItemList.get(i);
				
				
				OppertunityLineItem oppertunityLineItem = null;
				try {
					Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where oli.id=?1 and oli.isDeleted=?2",OppertunityLineItem.class);
					selectOppertunityLineItemQuery.setParameter(1,oppertunityLineItemRequest.getId());
					selectOppertunityLineItemQuery.setParameter(2,false);
					oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				if(oppertunityLineItem==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
				}
				
				
				double price = oppertunityLineItemRequest.getPrice();
				double quantity = oppertunityLineItemRequest.getQuantity();
				
				double totalPrice = price*quantity;
				
				oppertunityLineItem.setPrice(price);
				oppertunityLineItem.setQuantity(quantity);
				oppertunityLineItem.setTotalPrice(totalPrice);
				
				oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunityLineItem.setModifiedDate(new Date());
				
				entityManager.persist(oppertunityLineItem);
			
			}
		}
		
		try {
			Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1",Double.class);
			sumOppertunityQuery.setParameter(1,oppertunityId);
			Double totalAmount  = (Double)sumOppertunityQuery.getSingleResult();
			
			oppertunity.setAmmount(totalAmount);
			oppertunity.setExpectedRevenue(totalAmount);
			
			oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunity.setModifiedDate(new Date());
			
			entityManager.persist(oppertunity);
		}catch(NoResultException ex) {
			
		}
		
		
		entityManager.flush();
		entityManager.clear();
		
		return lineItemList;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public void deleteOppertunity(long oppertunityId, LoginUser user) throws Exception {
		
		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",Oppertunity.class);
			selectOppertunityQuery.setParameter(1,oppertunityId);
			oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(oppertunity==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}
		
		
		Quote quoteExist = null;
		try {
			Query selectQuoteQuery=entityManager.createQuery("select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where opr.oppertunityId=?1 order by qt.createdDate desc",Quote.class);
			selectQuoteQuery.setParameter(1,oppertunityId);
			quoteExist=(Quote)selectQuoteQuery.getResultList().get(0);
		}catch(NoResultException ex) {
			
		}catch(IndexOutOfBoundsException e) {
			
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(4); // Quote Sent
		quoteStatusList.add(6); // Submitted For Approval
		quoteStatusList.add(7); // Approved
		quoteStatusList.add(8);	// Invoiced
		quoteStatusList.add(10);// Approved by Store
		
		if(quoteExist!=null && quoteExist.getQuoteStatus()!=null) {
			if(quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Can't update because quote not in the draft/Reject mode");
			}
		}
		
		
		if(oppertunity.isSyncFlag()) {
			
			oppertunity.setDeleted(true);
			oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunity.setModifiedDate(new Date());
			entityManager.persist(oppertunity);
			
			if(quoteExist!=null) {
				quoteExist.setDeleted(true);
				quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
				quoteExist.setModifiedDate(new Date());
				entityManager.persist(quoteExist);
			}
			
			List<QuoteLineItem> quoteLineItemList = null;
			try {
				Query selectQuoteLineItemQuery=entityManager.createQuery("select qli from QuoteLineItem qli left join qli.quote qt where qt.id=?1",QuoteLineItem.class);
				selectQuoteLineItemQuery.setParameter(1,quoteExist.getId());
				quoteLineItemList=(List<QuoteLineItem>)selectQuoteLineItemQuery.getResultList();
			}catch(NoResultException ex) {
				
			}
			
			if(quoteLineItemList!=null && quoteLineItemList.size()>0) {
				for(int i=0; i<quoteLineItemList.size(); i++) {
					QuoteLineItem quoteLineItem = quoteLineItemList.get(i);
					quoteLineItem.setDeleted(true);
					quoteLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					quoteLineItem.setModifiedDate(new Date());
					entityManager.persist(quoteLineItem);
				}
			}
			
			
		}else {
			oppertunity.setDeleted(true);
			oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunity.setModifiedDate(new Date());
			entityManager.persist(oppertunity);
		}
		List<OppertunityLineItem> oppertunityLineItemList= null;
		try {
			Query selectLineItemList=entityManager.createQuery("select oli from OppertunityLineItem oli where oli.oppertunity.oppertunityId=?1",OppertunityLineItem.class);
			selectLineItemList.setParameter(1,oppertunityId);
			oppertunityLineItemList=selectLineItemList.getResultList();
		}catch(NoResultException e) {
			
		}
		
		if(oppertunityLineItemList!=null && oppertunityLineItemList.size()>0) {
			for(int i=0; i<oppertunityLineItemList.size(); i++) {
				OppertunityLineItem oppertunityLineItem = oppertunityLineItemList.get(i);
				oppertunityLineItem.setDeleted(true);
				oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunityLineItem.setModifiedDate(new Date());
				entityManager.persist(oppertunityLineItem);
			}
		}
		
		entityManager.flush();
		entityManager.clear();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public void deleteOppertunityLineItem(long oppertunityLineItemId, LoginUser user) throws Exception {
		
		OppertunityLineItem oppertunityLineItem = null;
		try {
			Query selectOppertunityLineItemQuery=entityManager.createQuery("select oli from OppertunityLineItem oli left join oli.oppertunity left join oli.priceBookEntries pbe left join pbe.product prd where oli.isDeleted=false and oli.id=?1",OppertunityLineItem.class);
			selectOppertunityLineItemQuery.setParameter(1,oppertunityLineItemId);
			oppertunityLineItem=(OppertunityLineItem)selectOppertunityLineItemQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(oppertunityLineItem==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
		}
		
		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",Oppertunity.class);
			selectOppertunityQuery.setParameter(1,oppertunityLineItem.getOppertunity().getOppertunityId());
			oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		if(oppertunity==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}
		
		
		Quote quoteExist = null;
		try {
			Query selectQuoteQuery=entityManager.createQuery("select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where opr.oppertunityId=?1 order by qt.createdDate desc",Quote.class);
			selectQuoteQuery.setParameter(1,oppertunityLineItem.getOppertunity().getOppertunityId());
			quoteExist=(Quote)selectQuoteQuery.getResultList().get(0);
		}catch(NoResultException ex) {
			
		}catch(IndexOutOfBoundsException e) {
			
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(4); // Quote Sent
		quoteStatusList.add(6); // Submitted For Approval
		quoteStatusList.add(7); // Approved
		quoteStatusList.add(8);	// Invoiced
		quoteStatusList.add(10);// Approved by Store
		
		if(quoteExist!=null && quoteExist.getQuoteStatus()!=null) {
			if(quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Can't update because quote not in the draft/Reject mode");
			}
		}
		
		
		if(oppertunity.isSyncFlag()) {
			
			if(quoteExist!=null) {
				QuoteLineItem quoteLineItem = null;
				try {
					Query selectQuoteLineItemQuery=entityManager.createQuery("select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pb left join pb.product prd where qt.id=?1 and prd.productPartCode=?2",QuoteLineItem.class);
					selectQuoteLineItemQuery.setParameter(1,quoteExist.getId());
					selectQuoteLineItemQuery.setParameter(2,oppertunityLineItem.getPriceBookEntries().getProduct().getProductPartCode());
					quoteLineItem=(QuoteLineItem)selectQuoteLineItemQuery.getSingleResult();
				}catch(NoResultException ex) {
					
				}
				
				if(quoteLineItem!=null) {
					quoteLineItem.setDeleted(true);
					quoteLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					quoteLineItem.setModifiedDate(new Date());
					
					entityManager.persist(quoteLineItem);
					
					Object[] amount = null;
					try {
						Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
						sumOppertunityQuery.setParameter(1,quoteExist.getId());
						amount  = (Object[])sumOppertunityQuery.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					
					if(amount!=null) {
						quoteExist.setCgstAmount((double)amount[1]);
						quoteExist.setIgstAmount((double)amount[3]);
						quoteExist.setSgstAmount((double)amount[2]);
						quoteExist.setSubTotal((double)amount[0]);
						quoteExist.setDiscount(0);
						quoteExist.setTotalAmountWithTax((double)amount[4]);
						quoteExist.setTotalAmountAfterDiscount((double)amount[4]);
						
						quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
						quoteExist.setModifiedDate(new Date());
						
						entityManager.persist(quoteExist);	
					}
				}
				
			}
			
			oppertunityLineItem.setDeleted(true);
			oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunityLineItem.setModifiedDate(new Date());
			entityManager.persist(oppertunityLineItem);	
		}else {
			oppertunityLineItem.setDeleted(true);
			oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunityLineItem.setModifiedDate(new Date());
			entityManager.persist(oppertunityLineItem);
		}
		
		
		
		try {
			Query sumOppertunityQuery=entityManager.createQuery("select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1",Double.class);
			sumOppertunityQuery.setParameter(1,oppertunityLineItem.getOppertunity().getOppertunityId());
			Double totalAmount  = (Double)sumOppertunityQuery.getSingleResult();
			
			oppertunity.setAmmount(totalAmount);
			oppertunity.setExpectedRevenue(totalAmount);
			
			oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunity.setModifiedDate(new Date());
			
			entityManager.persist(oppertunity);
		}catch(NoResultException ex) {
			
		}
		
		entityManager.flush();
		entityManager.clear();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public OppertunityRequest updateOppertunity(OppertunityRequest oppertunityRequest, LoginUser user)
			throws Exception {
		
		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr join opr.oppertunityStage oprstg where opr.isDeleted=false and opr.oppertunityId=?1",Oppertunity.class);
			selectOppertunityQuery.setParameter(1,oppertunityRequest.getOppertunityId());
			oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(oppertunity==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}
		
		List<Long> oprStage = new ArrayList<Long>();
		oprStage.add(2L); // Closed Won
		oprStage.add(3L); // Closed Lost
		
		if(oprStage.contains(oppertunity.getOppertunityStage().getOppertunityStageId())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
		}
		
		Quote quoteExist = null;
		try {
			Query selectQuoteQuery=entityManager.createQuery("select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where opr.oppertunityId=?1 order by opr.createDate desc",Quote.class);
			selectQuoteQuery.setParameter(1,oppertunityRequest.getOppertunityId());
			quoteExist=(Quote)selectQuoteQuery.getResultList().get(0);
		}catch(NoResultException ex) {
			
		}catch(IndexOutOfBoundsException e) {
			
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(4); // Quote Sent
		quoteStatusList.add(6); // Submitted For Approval
		quoteStatusList.add(7); // Approved
		quoteStatusList.add(8);	// Invoiced
		quoteStatusList.add(10);// Approved by Store
		
		if(quoteExist!=null && quoteExist.getQuoteStatus()!=null) {
			if(quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Can't update because quote not in the draft/Reject mode");
			}
		}
		
		if(oprStage.contains(oppertunityRequest.getOppertunityStageId())) {
			oppertunity.setCloseDate(new Date());
		}
		
		oppertunity.setForcastCategory(new OppertunityForecastCategory(oppertunityRequest.getForcastCategoryId()));
		oppertunity.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		oppertunity.setModifiedDate(new Date());
		oppertunity.setOppertunityStage(new OppertunityStage(oppertunityRequest.getOppertunityStageId()));
		oppertunity.setOppertunityType(new OppertunityType(oppertunityRequest.getOppertunityTypeId()));
		oppertunity.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		oppertunity.setProbability(oppertunityRequest.getProbability());
		entityManager.persist(oppertunity);
		
		entityManager.flush();
		entityManager.clear();
		
		return oppertunityRequest;
	}
	
	
	@Override
	@Transactional
	public List<com.pratikshat.exicom.model.response.OppertunityResponse> advanceSearch( Map<String,Object> request,LoginUser users) {
		
		String queryString ="select new com.pratikshat.exicom.model.response.OppertunityResponse(op) from Oppertunity op left join op.caseDetails cd left join op.accounts act left join op.ownerDetails onr left join op.createdBy cb left join op.modifiedBy mb where op.isDeleted=false and 1=1";
		
		if(request.get("manualId")!=null&&!request.get("manualId").equals("")) {
			queryString+=" and op.oppertunityNumber like :manualId";
		}
		if(request.get("account")!=null&&!request.get("account").equals("")) {
			queryString+=" and act.name like :account";
		}
		if(request.get("stage")!=null&&!request.get("stage").equals("")) {
			queryString+=" and op.oppertunityStage.oppertunityStageName like :stage";
		}
		if(request.get("forecastCategory")!=null&&!request.get("forecastCategory").equals("")) {
			queryString+=" and op.forcastCategory.oppertunityForecastCategoryName like :forecastCategory";
		}
		if(request.get("owner")!=null&&!request.get("owner").equals("")) {
			queryString+=" and concat(coalesce(onr.firstName,''),coalesce(onr.middleName,''),' ',coalesce(onr.lastName,'')) like :owner";
		}
		if(request.get("closedDate")!=null&&!request.get("closedDate").equals("")) {
			queryString+=" and function('date_format', op.closeDate, '%Y-%m-%d')=function('date_format', :closedDate, '%Y-%m-%d')";
		}
		
		Query qry=entityManager.createQuery(queryString,com.pratikshat.exicom.model.response.OppertunityResponse.class);
		
		if(request.get("manualId")!=null&&!request.get("manualId").equals("")) {
			qry.setParameter("manualId","%"+ request.get("manualId")+"%");
		}
		if(request.get("account")!=null&&!request.get("account").equals("")) {
			qry.setParameter("account","%"+ request.get("account")+"%");
		}
		if(request.get("stage")!=null&&!request.get("stage").equals("")) {
			qry.setParameter("stage","%"+ request.get("stage")+"%");
		}
		if(request.get("forecastCategory")!=null&&!request.get("forecastCategory").equals("")) {
			qry.setParameter("forecastCategory","%"+ request.get("forecastCategory")+"%");
		}
		if(request.get("owner")!=null&&!request.get("owner").equals("")) {
			qry.setParameter("owner","%"+ request.get("owner")+"%");
		}
		if(request.get("closedDate")!=null&&!request.get("closedDate").equals("")) {
			qry.setParameter("closedDate", request.get("closedDate"));
		}

		List<com.pratikshat.exicom.model.response.OppertunityResponse> response= qry.getResultList();
		
		 return response;
	}
}
