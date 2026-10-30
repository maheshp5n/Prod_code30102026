package com.pratikshat.exicom.repository.custom.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductTransfer;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

import com.pratikshat.exicom.repository.custom.BtProductTransferRepositoryCustom;


public class BtProductTransferRepositoryCustomImpl implements BtProductTransferRepositoryCustom{

	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public BtProductTransfer saveDetails(BtProductTransfer t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
		em.persist(t);
		String manualId="PT-BT-"+ MiscellaneousUtils.fillZero(""+t.getId(), 5);
		t.setManualId(manualId);
		em.merge(t);
		workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		return t;
	}

	@Override
	public BtProductTransfer updateDetails(BtProductTransfer t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
		em.merge(t);
		workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return t;
	}

	@Override
	public BtProductTransfer deleteDetails(BtProductTransfer t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		 em.remove(t);
		 return t;
	}

	@Override
	public void addTransfredQty(Long productTransferId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(qty<0)
				throw new BadRequestException("Invalid quantity "+qty);
		BtProductTransfer transfer=null;
		try {
			transfer= em.find(BtProductTransfer.class, productTransferId);
			
		} catch (NoResultException e) {
			throw new BadRequestException("Invalid product transfer Id");
		} catch(NonUniqueResultException ex){
			throw new BadRequestException("Multiple record found");
		}
		Float tQty=(transfer.getTotalQuantityIssued()==null?0.000F:transfer.getTotalQuantityIssued());
		tQty+=qty;
		transfer.setTotalQuantityIssued(tQty);
		em.merge(transfer);
		
	}

	@Override
	public void addReceivedQty(Long productTransferId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(qty==null||qty<0)
				throw new BadRequestException("Invalid quantity "+qty);
		BtProductTransfer transfer=null;
		try {
			transfer=em.find(BtProductTransfer.class, productTransferId);
			
		} catch (NoResultException e) {
			throw new BadRequestException("Invalid product trannsfer id, No record found");
		} catch (NonUniqueResultException ex){
			throw new BadRequestException("Invalid product trannsfer id,Multiple record found");
		}
		Float tqty=(qty+(transfer.getTotalQuantityReceived()==null?0.000F:transfer.getTotalQuantityReceived()));
		transfer.setTotalQuantityReceived(tqty);
		if(tqty>=transfer.getTotalQuantityIssued())
			transfer.setTransferStatus("Received");
		else
			transfer.setTransferStatus("Partial Received");
			
		em.merge(transfer);
		
	}

	@Override
	public void subTransferdQty(Long productTransferId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(qty==null||qty<0)
				throw new BadRequestException("Invalid quantity");
		BtProductTransfer transfer=null;
		try {
			transfer=em.find(BtProductTransfer.class, productTransferId);
		} catch (NoResultException e) {
			throw new BadRequestException("Invalid id, No record found");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Invalid id, Multiple record found");
		}

		Float tQty=transfer.getTotalQuantityIssued();

		if(tQty==null||tQty<=0)
			throw new BadRequestException("Invalid quantity, issued quantity can not have negative value");
		tQty=tQty-qty;

		if(tQty<0)
			throw new BadRequestException("Invalid quantity, issued quantity can not have negative value");

		transfer.setTotalQuantityIssued(tQty);
		
		em.merge(transfer);
		
	}

	@Override
	public void subTransferReceiveQty(Long productTransferId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(qty==null || qty<0)
				throw new BadRequestException("Invalid quantity");
		BtProductTransfer transfer=null;
		try {
			transfer=em.find(BtProductTransfer.class, productTransferId);
		} catch (NoResultException e) {
			throw new BadRequestException("Invalid id, No record found");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Invalid id, Multiple record found");
		}

		Float tQty=transfer.getTotalQuantityReceived();
		
		if(tQty==null||tQty<=0)
			throw new BadRequestException("Invalid quantity, issued quantity can not have negative value");

		tQty=tQty-qty;

		if(tQty<0)
			throw new BadRequestException("Invalid quantity, issued quantity can not have negative value");


		transfer.setTotalQuantityReceived(tQty);
		em.merge(transfer);

	}
	
	@Override
    public List<Map<String, Object>> advanceSearch(List<Long> locationIds,Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String query="SELECT  \r\n"
        		+ "        trn.`id` AS productTrnsferId,   \r\n"
        		+ "        trn.`created_date` AS productTransferCreatedDate,   \r\n"
        		+ "        trn.`last_modified_date` AS productTransferLastModifiedDate,   \r\n"
        		+ "        trn.`deliv` AS productTransferSapDeliv,   \r\n"
        		+ "        trn.`invoice` AS productTransferSapInvocieNumber,   \r\n"
        		+ "        trn.`mblnr_g` AS productTransferSapMblnrg,   \r\n"
        		+ "        trn.`mjahr_g` AS productTransferSapMjahrG,   \r\n"
        		+ "        trn.`remark_d` AS productTransferRemarkD,   \r\n"
        		+ "        trn.`remark_g` AS productTraansferRemarkG,   \r\n"
        		+ "        trn.`remark_i` AS productTransferRemarkI,   \r\n"
        		+ "        trn.`actual_delivery_date` AS productTransferActualDeliveryDate,   \r\n"
        		+ "        trn.`manual_id` AS productTransferManualId,   \r\n"
        		+ "        trn.`received_date` AS productTransferReceivedDate,   \r\n"
        		+ "        trn.`shipping_mode` AS productTransferShippingMode,   \r\n"
        		+ "        trn.`shipping_to_name` AS productTransferShippingToName,   \r\n"
        		+ "        trn.`tracking_number` AS productTranferTrackingNumber,   \r\n"
        		+ "        trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,   \r\n"
        		+ "        trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
        		+ "        trn.`transfer_status` AS productTransferStatus,   \r\n"
        		+ "	cemp.`first_name` AS productTransferCreatedByFirstName,\r\n"
        		+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,\r\n"
        		+ "	cemp.`last_name` AS productTransferCreatedByLastName,\r\n"
        		+ "	\r\n"
        		+ "        sloc.location_name AS productRequestSourceLocationName,   \r\n"
        		+ "        dloc.location_name AS productRequestDestinationLocationName,   \r\n"
        		+ "        sloc.location_id AS productRequestSourceLocationId,   \r\n"
        		+ "        dloc.location_id AS productRequestDestinationLocationId   \r\n"
        		+ "        FROM `bt_product_transfer` trn    \r\n"
        		+ "    \r\n"
        		+ "        JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
        		+ "         JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id`\r\n"
        		+ "         JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` where 1=1 ";
        
        List<Object> param=new ArrayList<Object>();

        if(locationIds!=null&&!locationIds.isEmpty()){
            String lparam="";
            for(int i=0;i<locationIds.size();i++){
                if(i!=0)
                    lparam+=",";
                lparam+=locationIds.get(i);
            }
            query+=" and (sloc.location_id in ("+lparam+") or dloc.location_id in ("+lparam+")) ";
            
        }
        if(searchRequest.get("productTransferSapInvocieNumber")!=null&&!searchRequest.get("productTransferSapInvocieNumber").toString().equalsIgnoreCase("")){
            query+=" and trn.`invoice` like ? ";
            param.add("%"+searchRequest.get("productTransferSapInvocieNumber")+"%");
        }
        if(searchRequest.get("productTransferManualId")!=null&&!searchRequest.get("productTransferManualId").toString().equalsIgnoreCase("")){
            query+=" and trn.manual_id like ? ";
            param.add("%"+searchRequest.get("productTransferManualId")+"%");
        }
        if(searchRequest.get("productTransferShippingMode")!=null&&!searchRequest.get("productTransferShippingMode").toString().equalsIgnoreCase("")){
            query+=" and trn.`shipping_mode` like ? ";
            param.add("%"+searchRequest.get("productTransferShippingMode")+"%");
        }
        if(searchRequest.get("productTransferShippingToName")!=null&&!searchRequest.get("productTransferShippingToName").toString().equalsIgnoreCase("")){
            query+=" and trn.shipping_to_name like ? ";
            param.add("%"+searchRequest.get("productTransferShippingToName")+"%");
        }
        if(searchRequest.get("productTranferTrackingNumber")!=null&&!searchRequest.get("productTranferTrackingNumber").toString().equalsIgnoreCase("")){
            query+=" and trn.tracking_number like ? ";
            param.add("%"+searchRequest.get("productTranferTrackingNumber")+"%");
        }
        if(searchRequest.get("productRequestSourceLocationName")!=null&&!searchRequest.get("productRequestSourceLocationName").toString().equalsIgnoreCase("")){
            query+=" and sloc.location_name like ? ";
            param.add("%"+searchRequest.get("productRequestSourceLocationName")+"%");
        }
        if(searchRequest.get("productRequestDestinationLocationName")!=null&&!searchRequest.get("productRequestDestinationLocationName").toString().equalsIgnoreCase("")){
            query+=" and dloc.location_name like ?";
            param.add("%"+searchRequest.get("productRequestDestinationLocationName")+"%");
        }
        if(searchRequest.get("productTransferStatus")!=null && !searchRequest.get("productTransferStatus").toString().equalsIgnoreCase("")) {
        	query+=" and trn.transfer_status like ?";
        	param.add(searchRequest.get("productTransferStatus"));
        }
        if (searchRequest.get("productTransferDeliveryDate") != null
                && !searchRequest.get("productTransferDeliveryDate").toString().trim().isEmpty()) {

            query += " and DATE(trn.actual_delivery_date) = DATE(?) ";
            param.add(searchRequest.get("productTransferDeliveryDate"));
        }
        if (searchRequest.get("productTransferReceiveDate") != null
                && !searchRequest.get("productTransferReceiveDate").toString().trim().isEmpty()) {

            query += " and DATE(trn.received_date) = DATE(?) ";
            param.add(searchRequest.get("productTransferReceiveDate"));
        }


        return jdbc.queryForList(query,param.toArray());
    }


}
