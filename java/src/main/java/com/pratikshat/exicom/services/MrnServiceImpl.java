package com.pratikshat.exicom.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MrnDetails;
import com.pratikshat.exicom.entity.MrnItems;
import com.pratikshat.exicom.entity.MrnLineItems;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MrnItemsRequest;
import com.pratikshat.exicom.model.request.MrnRequest;
import com.pratikshat.exicom.repository.AccountsRepository;
import com.pratikshat.exicom.repository.LocationProductItemRepository;
import com.pratikshat.exicom.repository.LocationProductLineItemRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.MrnDetailsRepository;
import com.pratikshat.exicom.repository.MrnItemsRepository;
import com.pratikshat.exicom.repository.MrnLineItemsRepository;
import com.pratikshat.exicom.repository.ProductRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@Service
public class MrnServiceImpl implements MrnService {

	@Autowired
	MrnDetailsRepository dbMrn;
	@Autowired
	MrnItemsRepository dbMrnItem;
	@Autowired
	MrnLineItemsRepository dbMrnLineItems;
	@Autowired
	LocationRepository dbLocation;
	@Autowired
	ProductRepository dbProduct;
	@Autowired
	AccountsRepository dbAccount;
	@Autowired
	LocationProductItemRepository dbWarehouseItems;
	@Autowired
	LocationProductLineItemRepository dbWarehouseLineItems;
	@Autowired
	UserProfileRepository dbUserprofile;
	
	
	@Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
	public void saveMrn(MrnRequest mrn, LoginUser user) throws BadRequestException, UnauthoriseException {
		SimpleDateFormat dFormat=new SimpleDateFormat("yyMMdd");
		
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), MrnDetails.class, UserProfileRepository.CREATE)) {
			throw new UnauthoriseException("You are not authorised for MRN");
		}
		
		if(mrn==null) throw new BadRequestException("MRN details not found");
		if(mrn.getWarehouseId()==0) throw new BadRequestException("Warehouse not found");
		if(mrn.getItems()==null||mrn.getItems().size()==0) throw new BadRequestException("Item not found for MRN");
		if(mrn.getAccountSapNumber()==null||mrn.getAccountSapNumber().equalsIgnoreCase("")) throw new BadRequestException("Vendor details not found");
		Accounts _vendor=dbAccount.findBySapCustomerNumber(mrn.getAccountSapNumber());
		if(_vendor==null)throw new BadRequestException("Vendor details not found");
		Location _location=dbLocation.findByLocationId(mrn.getWarehouseId());
		if(_location==null) throw new BadRequestException("Warehouse not found");
		if(_location.getOwner()==null||!_location.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) throw new UnauthoriseException("You are not authorised");
		
		MrnDetails mrnDetails=new MrnDetails();
		mrnDetails.setMrnDate(mrn.getMrnDate());
		mrnDetails.setPoReferenceNumber(mrn.getPoReferenceNumber()!=null?mrn.getPoReferenceNumber():"NA");
		mrnDetails.setTransporter(mrn.getTransporter()!=null?mrn.getTransporter():"NA");
		mrnDetails.setVehicleNumber(mrn.getVehicleNumber()!=null?mrn.getVehicleNumber():"NA");
		mrnDetails.setVendor(_vendor);
		mrnDetails.setVendorChallanNumber(mrn.getVendorChallanNumber()!=null?mrn.getVendorChallanNumber():"NA");
		mrnDetails.setVendorInvoiceNumber(mrn.getVendorInvoiceNumber()!=null?mrn.getVendorInvoiceNumber():"NA");
		mrnDetails.setCreatedBy(new EmployeeDetails(user.getUsername()));
		mrnDetails.setCreatedDate(new Date());
		mrnDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
		mrnDetails.setOwner(new EmployeeDetails(user.getUsername()));
		
		mrnDetails.setWarehouse(_location);
		
		dbMrn.save(mrnDetails);
		mrnDetails.setManualId(String.format("MRN-%s-%s", dFormat.format(new Date()),MiscellaneousUtils.fillZero(String.valueOf(mrnDetails.getId()), 5)));
		dbMrn.save(mrnDetails);
		
		for(MrnItemsRequest itemRequest:mrn.getItems()) {
			if(itemRequest.getProductPartCode()==null||itemRequest.getProductPartCode().equalsIgnoreCase("")) throw new BadRequestException("Product part code not foudn");
			if(itemRequest.getQty()==null||itemRequest.getQty()==0) throw new BadRequestException(String.format("Item quantity for part code % not found",itemRequest.getProductPartCode()));
			Products _product=dbProduct.findByProductPartCode(itemRequest.getProductPartCode());
			if(_product==null) throw new BadRequestException(String.format("Product part code %s not found",itemRequest.getProductPartCode()));
			if(!_product.getProductType().equalsIgnoreCase("Component")&&(itemRequest.getSerialNumbers()==null||itemRequest.getSerialNumbers().size()!=itemRequest.getQty())) throw new BadRequestException(String.format("Serial number for product part code %s not found", itemRequest.getProductPartCode()));
			MrnItems _mrnItems=new MrnItems();
			_mrnItems.setCgst(itemRequest.getCgst()==null?0:itemRequest.getCgst());
			_mrnItems.setIgst(itemRequest.getIgst()==null?0:itemRequest.getIgst());
			_mrnItems.setSgst(itemRequest.getSgst()==null?0:itemRequest.getSgst());
			_mrnItems.setDiscount(itemRequest.getDiscount()==null?0:itemRequest.getDiscount());
			_mrnItems.setMrnDetails(mrnDetails);
			_mrnItems.setProduct(_product);
			_mrnItems.setQty(itemRequest.getQty());
			_mrnItems.setRateUnitQty(itemRequest.getRateUnitQty());
			_mrnItems.setTaxableAmount(_mrnItems.getQty()*_mrnItems.getRateUnitQty());
			_mrnItems.setTaxableAmountAfterDiscount(getAmountAfterDiscount(_mrnItems.getTaxableAmount(),_mrnItems.getDiscount()));
			_mrnItems.setTotalAmount(getTotalAmountAfterTax(_mrnItems.getTaxableAmountAfterDiscount(),_mrnItems.getIgst(),_mrnItems.getCgst(),_mrnItems.getSgst()));
			
			dbMrnItem.save(_mrnItems);
			
			//UPDATE STOCK ON SELECTED WAREHOUSE
			LocationProductItem lItem=dbWarehouseItems.findByProductAndLocation(_product, _location);
			if(lItem==null) {
				lItem=new LocationProductItem();
				lItem.setBerQty(0);
				lItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				lItem.setCreatedDate(new Date());
				lItem.setCustomerInventory(0);
				lItem.setFaultyQuantity(0);
				lItem.setHslQuantity(0);
				lItem.setHslStatus("L");
				lItem.setIssuedQunatity(0);
				lItem.setLocation(_location);
				lItem.setMslQuantity(0);
				lItem.setMslQuantityStatus("L");
				lItem.setOkQuantity(0);
				lItem.setProduct(_product);
				lItem.setQuantityInOnHand(0);
				lItem.setQuantityUnitOfMasur("NO");
				lItem.setRejectedQty(0);
				lItem.setScrapQty(0);
			}
			lItem.setOkQuantity((lItem.getOkQuantity()+itemRequest.getQty()));
			lItem.setQuantityInOnHand((lItem.getQuantityInOnHand()+itemRequest.getQty()));
			lItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			lItem.setModifiedDate(new Date());
			dbWarehouseItems.save(lItem);
			if(lItem.getLocationProductItemId()==null) {
				lItem.setLocationProductItemId(String.format("PI-%s",MiscellaneousUtils.fillZero(String.valueOf(lItem.getId()), 5)));
				dbWarehouseItems.save(lItem);
			}
			
			if(!_product.getProductType().equalsIgnoreCase("Component")) {
				for(String serialNumber:itemRequest.getSerialNumbers()) {
					//CHECKING SERIAL NUMBER EXIST OR NOT
					if(dbWarehouseLineItems.countByLocationProductItem_ProductAndItemSerialNumber(_product, serialNumber)>0)throw new BadRequestException(String.format("Serial Number %s for product part code %s already exists", serialNumber,itemRequest.getProductPartCode()));
					
					MrnLineItems _lineitems=new MrnLineItems();
					_lineitems.setItem(_mrnItems);
					_lineitems.setSerialNumber(serialNumber);
					dbMrnLineItems.save(_lineitems);
					
					//UPDATE SERIAL NUMBER TO WAREHOUSE
					LocationProductLineItems wLineItems=new LocationProductLineItems();
					wLineItems.setCreatedBy(new EmployeeDetails(user.getUsername()));
					wLineItems.setCreatedDate(new Date());
					wLineItems.setCustomerInventory(false);
					wLineItems.setInStock(true);
					wLineItems.setItemSerialNumber(serialNumber);
					wLineItems.setLocationProductItem(lItem);
					wLineItems.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
					wLineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(1));
					wLineItems.setModifiedBy(new EmployeeDetails(user.getUsername()));
					wLineItems.setModifiedDate(new Date());
					wLineItems.setReplaced(false);
					wLineItems.setReturned(false);
					dbWarehouseLineItems.save(wLineItems);
					wLineItems.setLocationProductLineItemId(String.format("PIL-%s",MiscellaneousUtils.fillZero(String.valueOf(wLineItems.getId()), 0)));
					dbWarehouseLineItems.save(wLineItems);
				}
			}
			
		}
	}
	Double getAmountAfterDiscount(Double totalAmount,Float discount) {
		return (totalAmount-(totalAmount*discount/100));
	}
	Double getTotalAmountAfterTax(Double taxableAmount,Float igst,Float cgst,Float sgst) {
		double amount=0D;
		if(igst!=null&&igst>0)
			amount+=(taxableAmount*igst/100);
		if(cgst!=null&&cgst>0)
			amount+=(taxableAmount*cgst/100);
		if(sgst!=null&&sgst>0)
			amount+=(taxableAmount*sgst/100);
		return (taxableAmount+amount);
	}

}
