package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.QuoteLineItem;
import com.pratikshat.exicom.model.request.OppertunityLineItemRequest;
import com.pratikshat.exicom.model.request.QuoteLineItemRequest;
import com.pratikshat.exicom.model.request.QuoteRequest;
import com.pratikshat.exicom.model.request.WorkOrderRequest;
import com.pratikshat.exicom.model.response.MailLogResponse;
import com.pratikshat.exicom.model.response.QuoteResponse;

public interface QuoteRepositoryCustom {

	QuoteRequest saveQuote(QuoteRequest quoteRequest, LoginUser user) throws Exception;
	QuoteRequest updateQuote(QuoteRequest quoteRequest, LoginUser user) throws Exception;
	List<QuoteLineItemRequest> addLineItem(List<QuoteLineItemRequest> lineItemList, long priceBookId, long quoteId, int businessDomainId, LoginUser user) throws Exception;
	QuoteLineItemRequest updateLineItem(QuoteLineItemRequest lineItem, long priceBookId, long quoteId, int businessDomainId, LoginUser user) throws Exception;;
	List<QuoteLineItemRequest> updateLineItemList(List<QuoteLineItemRequest> lineItemList, long priceBookId, long quoteId, int businessDomainId, LoginUser user) throws Exception;;

	void deleteQuote(long quoteId, LoginUser user) throws Exception;
	
	void deleteQuoteLineItem(long quoteLineItemId, LoginUser user) throws Exception;
	
	boolean syncQuoteWithOppertunity(long quoteId, int businessDomainId, LoginUser user) throws Exception;
	
	boolean stopSyncQuoteWithOppertunity(long quoteId, int businessDomainId, LoginUser user) throws Exception;
	
	ByteArrayInputStream createPdf(long quoteId,LoginUser user) throws Exception;
	
	public void submitForApproval(long quoteId, LoginUser user) throws Exception;
	
	public void workOrderUpdate(WorkOrderRequest request, LoginUser user) throws Exception;
	
	public List<MailLogResponse> getMailLog(long quoteId, LoginUser user) throws Exception;
	
	List<QuoteResponse> advanceSearch( Map<String,Object> request,List<String> users)throws ParseException;
}
