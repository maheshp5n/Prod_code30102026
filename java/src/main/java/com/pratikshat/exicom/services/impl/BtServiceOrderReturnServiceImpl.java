package com.pratikshat.exicom.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtServiceOrder;
import com.pratikshat.exicom.entity.BtServiceOrderItem;
import com.pratikshat.exicom.entity.BtServiceOrderItemReceive;
import com.pratikshat.exicom.entity.BtServiceOrderItemReturn;
import com.pratikshat.exicom.entity.BtServiceOrderItemReturnReceive;
import com.pratikshat.exicom.entity.BtServiceOrderReturn;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;

import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.UserProfile;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtJobCardRepository;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemReceiveRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemReturnReceiveRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemReturnRepository;
import com.pratikshat.exicom.repository.BtServiceOrderRepository;
import com.pratikshat.exicom.repository.BtServiceOrderReturnRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtServiceOrderReturnService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtServiceOrderReturnServiceImpl implements BtServiceOrderReturnService {

    @Autowired
    BtServiceOrderReturnRepository dbReturn;
    @Autowired
    BtServiceOrderItemReturnRepository dbItemReturn;
    @Autowired
    BtServiceOrderItemRepository dbServiceOrderItem;
    @Autowired
    BtServiceOrderRepository dbServiceOrder;
    @Autowired
    UserProfileRepository dbUserProfile;
    @Autowired
    LocationRepository dbLocation;
    @Autowired
    ServiceRecourseRepository dbResource;
    @Autowired
    BtServiceOrderItemReceiveRepository dbReceive;
    @Autowired
    BtServiceOrderItemReturnReceiveRepository dbReturnReceive;
    @Autowired
    BtJobCardRepository dbJobcard;
    @Autowired
    BtProductItemRepository dbStock;
    @Autowired
    BtCustomerInventoryInRepository dbCustomerInventoryIn;

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        if(dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderReturn.class,UserProfileRepository.VIEW_ALL))
            return dbReturn.getServiceOrderReturnById(Long.parseLong(id.toString()));
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderReturn.class, UserProfileRepository.VIEW))
            return dbReturn.getServiceOrderReturnById(dbUserProfile.getLocationAndChildLocation(user.getUsername()),Long.parseLong(id.toString()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user,String filterParam)
            throws BadRequestException, UnauthoriseException {
                if(dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderReturn.class, UserProfileRepository.VIEW_ALL)) {
                	if(filterParam.equalsIgnoreCase("pendingtoreceive"))
                        return dbReturn.getServiceOrderReturnPendingToReceive(page);
                    	if(filterParam.equalsIgnoreCase("received"))
                            return dbReturn.getServiceOrderReturnReceived(page);
                    	else
                    return dbReturn.getServiceOrderReturn(page);
                }
                else if(dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderReturn.class, UserProfileRepository.VIEW)) {
                	if(filterParam.equalsIgnoreCase("pendingtoreceive"))
                    return dbReturn.getServiceOrderReturnPendingToReceive(dbUserProfile.getLocationAndChildLocation(user.getUsername()), page);
                	if(filterParam.equalsIgnoreCase("received"))
                        return dbReturn.getServiceOrderReturnReceived(dbUserProfile.getLocationAndChildLocation(user.getUsername()), page);
                	else
                		 return dbReturn.getServiceOrderReturn(dbUserProfile.getLocationAndChildLocation(user.getUsername()), page);
                }
            else
                throw new UnauthoriseException("You are not authorised");
    }

    @Override
    @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
            Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (!dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderReturn.class,
                UserProfileRepository.CREATE))
            throw new UnauthoriseException("You are not authorised");

        Long destinationLocationId = Long.parseLong(request.get("destinationLocationId").toString());
        Optional<Location> opDestinationLocation = dbLocation.findById(destinationLocationId);
        if (!opDestinationLocation.isPresent())
            throw new BadRequestException("Invalid Location");
        ServiceRecource sr = dbResource.getByEmail(user.getUsername());
        if (sr == null || sr.getLocation() == null)
            throw new UnauthoriseException("You are not authorised");

        BtServiceOrderReturn returnDetails = new BtServiceOrderReturn();
        returnDetails.setDestinationLocation(opDestinationLocation.get());
        returnDetails.setSourceLocation(sr.getLocation());
        returnDetails.setOwner(new EmployeeDetails(user.getUsername()));
        returnDetails.setShippingTo(MiscellaneousUtils.objToString(request.get("shippingTo")));
        returnDetails.setShippintMode(MiscellaneousUtils.objToString(request.get("shippingMode")));
        returnDetails.setTotalNumberOfQuantity(0F);
        returnDetails.setTotalNumberOfQuantityReceived(0F);
        returnDetails.setTrackingNumber(MiscellaneousUtils.objToString(request.get("trackingNumber")));
        dbReturn.saveDetails(returnDetails, user);
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        if (items == null || items.size() == 0)
            throw new BadRequestException("Item not found to return");

        for (Map<String, Object> item : items) {

            Long serviceOrderItemId = Long.parseLong(item.get("serviceOrderItemId").toString());
            Optional<BtServiceOrderItem> optionalSoItem = dbServiceOrderItem.findById(serviceOrderItemId);
            Optional<BtServiceOrderItemReceive> optionalSoItemReceive = dbReceive.findById(serviceOrderItemId);

            if (!optionalSoItemReceive.isPresent())
                throw new BadRequestException("Invalid Item to return");
            BtServiceOrderItemReceive soItemReceive = optionalSoItemReceive.get();
            BtServiceOrderItem soItem = optionalSoItem.get();

            if (soItem.getReturnedToSource())
                throw new BadRequestException("Item " + soItem.getSerialNumber() + " already returned to source");

            BtServiceOrderItemReturn itemReturn = new BtServiceOrderItemReturn();
            Optional<BtJobCard> optionalJobCard = dbJobcard.findById(serviceOrderItemId);
            if (!optionalJobCard.isPresent())
                throw new BadRequestException("Job card not found");
            BtJobCard jobCard = optionalJobCard.get();

            itemReturn.setItemReceive(soItemReceive);
            itemReturn.setOwner(new EmployeeDetails(user.getUsername()));
            itemReturn.setReceived(false);
            itemReturn.setSoReturn(returnDetails);
            itemReturn.setId(soItem.getId());
            dbItemReturn.saveDetails(itemReturn, user);
            soItem.setReturnedToSource(true);
            dbServiceOrderItem.updateDetails(soItem, user);
            dbServiceOrder.addTotalNumberOfItemReturned(soItem.getServiceOrder().getId(), 1F, user);
            dbReturn.addTotalReturndQuantity(returnDetails.getId(), 1F, user);
            // STOCK ADJUSTMENT FOR RETURNING USER

            dbStock.subQuantity(soItem.getProduct(), sr.getLocation(), 1F, soItem.getCustomerInventory(),
                    jobCard.getRepairProductState(), user);

            // CUSTOMER INVENTORY STATE AND STAGE UPDATION
            if (soItem.getInCustomerInventory() != null) {
                Optional<BtCustomerInventoryIn> optionCustomerInventoryIn = dbCustomerInventoryIn
                        .findById(soItem.getInCustomerInventory().getId());
                if (optionCustomerInventoryIn.isPresent()) {
                    BtCustomerInventoryIn _inCustomerInventory = optionCustomerInventoryIn.get();
                    _inCustomerInventory.setFinalStateCode(jobCard.getRepairProductState());
                    _inCustomerInventory.setCustomerInventoryStatus("Repaired/BER");
                    _inCustomerInventory.setCustomerInventoryStage("SO Return Prepared");
                    dbCustomerInventoryIn.updateDetails(_inCustomerInventory, user);
                }
            }

            item.put("returnItemId", itemReturn.getId());
            item.put("returnItemManualId", itemReturn.getManualId());

        }
        request.put("id", returnDetails.getId());
        request.put("manualId", returnDetails.getManualId());
        return request;
    }

    // @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
    //     Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
            if(!dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItemReturnReceive.class, UserProfileRepository.CREATE))
                throw new UnauthoriseException("You are not authorised");

                if(request.get("serviceOrderReturnItemId")==null)
                    throw new BadRequestException("Invalid return item id");
            Long soReturnOrderItemId=Long.parseLong(request.get("serviceOrderReturnItemId").toString());
            Optional<BtServiceOrderItemReturn> optionReturnItem=dbItemReturn.findById(soReturnOrderItemId);
            if(!optionReturnItem.isPresent())
                throw new BadRequestException("Invalid return item id, Record not found");
            BtServiceOrderItemReturn returnItem=optionReturnItem.get();
            if(returnItem.getReceived())
                throw new BadRequestException("Invalid return item id, Item already received");

            BtServiceOrderReturn returnSo=dbReturn.findById(returnItem.getSoReturn().getId()).get();
            ServiceRecource sr=dbResource.getByEmail(user.getUsername());
            if(sr==null||sr.getLocation()==null)
                throw new UnauthoriseException("You are not authorised");
            if(sr.getLocation().getLocationId()!=returnSo.getDestinationLocation().getLocationId())
                throw new UnauthoriseException("You are not authorised");
            
            BtServiceOrderItem items=dbServiceOrderItem.findById(soReturnOrderItemId).get();
            BtServiceOrder so=dbServiceOrder.findById(items.getServiceOrder().getId()).get();

            if(items.getCustomerInventory()!=null&&items.getCustomerInventory()&&items.getInCustomerInventory()!=null){

                BtCustomerInventoryIn cuIn=dbCustomerInventoryIn.findById(items.getInCustomerInventory().getId()).get();
                cuIn.setFinalStateCode(items.getrStatus());
                if(cuIn.getInLocation().getLocationId()==sr.getLocation().getLocationId())
                    cuIn.setInStock(true);
                cuIn.setCustomerInventoryStage("SO Return Received");
                cuIn.setCustomerInventoryStatus("Repaired/BER");
                cuIn.setServiceOrderDone(true);
                cuIn.setServiceOrderReturn(true);
                dbCustomerInventoryIn.updateDetails(cuIn, user);
            }
            returnItem.setReceived(true);
            dbItemReturn.updateDetails(returnItem, user);

            BtServiceOrderItemReturnReceive receiveItem=new BtServiceOrderItemReturnReceive();
            receiveItem.setId(returnItem.getId());
            receiveItem.setItemReturn(returnItem);
            receiveItem.setOwner(new EmployeeDetails(user.getUsername()));
            receiveItem.setReceiveDate(new Date());
            dbReturnReceive.saveDetails(receiveItem, user);

            dbStock.addQuantity(items.getProduct(), 1F, items.getCustomerInventory(), items.getrStatus(), user);
            dbReturn.addTotalReturndReceivedQuantity(returnSo.getId(), 1F, user);
            

            return request;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        return null;
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        return null;
    }

    @Override
    public List<Map<String, Object>> getPendingItemToReturn(LoginUser user, Long destinationLocationId)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr = dbResource.getByEmail(user.getUsername());
        if (sr == null)
            throw new UnauthoriseException("You are not authorised");
        return dbServiceOrderItem.getPendingItemToReturnToSource(sr.getLocation().getLocationId(),
                destinationLocationId);
    }

    @Override
    public List<Map<String, Object>> getPendingItemToReceive(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr=dbResource.getByEmail(user.getUsername());
        if(sr==null||sr.getLocation()==null)
                throw new UnauthoriseException("You are not authorised");
        return dbItemReturn.getPendingItemToreceive(sr.getLocation().getLocationId());
    }

    @Override
    public List<Map<String, Object>> getPendingLocationToTransfer(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr = dbResource.getByEmail(user.getUsername());
        if (sr == null)
            throw new UnauthoriseException("You are not authorised");
        return dbServiceOrderItem.getPendingLocation(sr.getLocation().getLocationId());
    }

    @Override
    public List<Map<String, Object>> getItemList(Long serviceOrderReturnId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return dbItemReturn.getListbysoreturnId(serviceOrderReturnId);
    }

    @Override
    public Page<Map<String, Object>> getItemPage(Long serviceOrderReturnId, Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return dbItemReturn.getPagebysoreturnId(serviceOrderReturnId, page);
    }

    @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
        Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void updateReceive(List<Map<String, Object>> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(!request.isEmpty()){
            for(Map<String,Object> req:request){
                updateObject(req, user);
            }
        }
    }

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
