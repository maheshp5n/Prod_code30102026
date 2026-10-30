package com.pratikshat.exicom.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductItem;
import com.pratikshat.exicom.entity.BtProductReturnOrder;
import com.pratikshat.exicom.entity.BtProductReturnOrderItem;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;
import com.pratikshat.exicom.model.response.BtPackingItem;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductReturnOrderItemRepository;
import com.pratikshat.exicom.repository.BtProductReturnOrderRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtReturnOrderService;
import com.pratikshat.exicom.utils.DateConverter;

import org.apache.commons.collections4.functors.FalsePredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtReturnOrderServiceImpl implements BtReturnOrderService {

    @Autowired
    BtProductReturnOrderRepository dbReturnOrder;
    
    @Autowired
    BtProductReturnOrderItemRepository dbReturnOrderItem;

    @Autowired
    ServiceRecourseRepository dbServiceResource;

    @Autowired
    UserProfileRepository dbProfile;

    @Autowired
    BtProductItemRepository dbStock;


    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW_ALL))
            return dbReturnOrder.getSingle((Long)id);
        else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW))
            return dbReturnOrder.getSingle((Long)id, dbProfile.getLocationAndChildLocation(user.getUsername()));
        throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW_ALL))
            return dbReturnOrder.getPage(page);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW))
            return dbReturnOrder.getPage(dbProfile.getLocationAndChildLocation(user.getUsername()), page);
        
        throw new UnauthoriseException("You are not authorised");
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
            BtProductReturnOrder returnOrder=new BtProductReturnOrder();
            ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
            Location destinationLocation=new Location(Long.parseLong(request.get("returnOrderDestinationLocationId").toString()));
            
            if(sr.getLocation().getLocationId()==destinationLocation.getLocationId())
                throw new BadRequestException("Source location and destination location can not be same");

            if(request.get("returnOrderDate")!=null)
                returnOrder.setActualSendDate(DateConverter.convertToDate(request.get("returnOrderDate").toString()));
            
            returnOrder.setDestination(destinationLocation);
            returnOrder.setOwner(new EmployeeDetails(user.getUsername()));
            returnOrder.setReturnOrderStatus("Draft");
            returnOrder.setShipToName(request.get("returnOrderShiptoName").toString());
            returnOrder.setShippingMode(request.get("returnOrderShippingMode").toString());
            returnOrder.setSource(sr.getLocation());
            returnOrder.setTotalQuantityReceived(0F);
            returnOrder.setTotalQuantityReturn(0F);
            returnOrder.setTrackingNumber(request.get("returnOrderTrackingNumber").toString());
            
            dbReturnOrder.saveDetails(returnOrder, user);

            List<Map<String,Object>> items=(List<Map<String,Object>>) request.get("items");
            if(items!=null&&!items.isEmpty()){
                for(Map<String,Object> item:items){
                    Float qty=item.get("exicomIvnentory")==null?0F:Float.parseFloat(item.get("exicomIvnentory").toString());
                    if(qty<=0)
                        continue;
                    BtProductReturnOrderItem returnOrderItem=new BtProductReturnOrderItem();
                    returnOrderItem.setCustomerQuantityReceived(0F);
                    returnOrderItem.setCustomerQuantityReturned(item.get("customerInventory")==null?0F:Float.parseFloat(item.get("customerInventory").toString()));
                    returnOrderItem.setOwner(new EmployeeDetails(user.getUsername()));
                    returnOrderItem.setProducts(new BtProducts(Long.parseLong(item.get("productId").toString())));
                    returnOrderItem.setQuantityReceived(0F);
                    returnOrderItem.setQuantityReturned(item.get("exicomIvnentory")==null?0F:Float.parseFloat(item.get("exicomIvnentory").toString()));
                    returnOrderItem.setReturnOrder(returnOrder);

                    dbReturnOrderItem.addItem(returnOrderItem, user);

                    dbStock.subQuantity(returnOrderItem.getProducts(), sr.getLocation(), returnOrderItem.getQuantityReturned(), false, 2, user);
                    dbStock.subQuantity(returnOrderItem.getProducts(), sr.getLocation(), returnOrderItem.getCustomerQuantityReturned(), true, 2, user);

                    dbReturnOrder.addReturnedQuantity(returnOrder, returnOrderItem.getCustomerQuantityReturned(), true, user);
                    dbReturnOrder.addReturnedQuantity(returnOrder, returnOrderItem.getQuantityReturned(), false, user);

                    item.put("returnOrderItemId", returnOrder.getId());
                    item.put("returnOrderItemManualId", returnOrder.getManualId());
                }
            }
            request.put("returnOrderId", returnOrder.getId());
            request.put("returnOrderManualId", returnOrder.getManualId());

        return request;
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        String status=request.get("status").toString();
        if(!status.equalsIgnoreCase("Submitted"))
            throw new BadRequestException("Invalid status");

        Long returnOrderItemId=Long.parseLong(request.get("returnOrderId").toString());
        Optional<BtProductReturnOrder> optionalReturnOrder=dbReturnOrder.findById(returnOrderItemId);
        if(!optionalReturnOrder.isPresent())
            throw new BadRequestException("Invalid return order id");
        BtProductReturnOrder returnOrder=optionalReturnOrder.get();
        if(!returnOrder.getReturnOrderStatus().equalsIgnoreCase("Draft"))
            throw new BadRequestException("You can not change status of return order "+returnOrder.getManualId());
        returnOrder.setReturnOrderStatus(status);
        dbReturnOrder.updateDetails(returnOrder, user);
        
        return request;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW_ALL))
            return dbReturnOrder.getSerachForList(search);
        else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class,UserProfileRepository.VIEW))
            return dbReturnOrder.getSerachForList(search, dbProfile.getLocationAndChildLocation(user.getUsername()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW_ALL))
                return dbReturnOrder.advanceSearch(null,searchRequest,user);
        else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW))
                return dbReturnOrder.advanceSearch(dbProfile.getLocationAndChildLocation(user.getUsername()), searchRequest, user);
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getItemByReturnOrderId(Long returnOrderId, LoginUser user,Pageable page)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW_ALL))
                return dbReturnOrderItem.getPageReturnOrderItemsByReturnOrderId(returnOrderId,page);
        else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW))
                return dbReturnOrderItem.getPageReturnOrderItemsByReturnOrderId(returnOrderId, dbProfile.getLocationAndChildLocation(user.getUsername()),page);
        else
                throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> getListItemByReturnOrderId(Long returnOrderId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW_ALL))
                return dbReturnOrderItem.getListReturnOrderItemsByReturnOrderId(returnOrderId);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW))
                return dbReturnOrderItem.getListReturnOrderItemsByReturnOrderId(returnOrderId,dbProfile.getLocationAndChildLocation(user.getUsername()));
        else
                throw new UnauthoriseException("You are not authorised");
        
    }

    @Override
    public List<Map<String, Object>> advanceSearchOfItemsByReturnOrderId(Long returnOrderId,
            Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW_ALL))
                return dbReturnOrderItem.advanceSearchOfItemsByReturnOrderId(returnOrderId, null,request, user);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW))
                return dbReturnOrderItem.advanceSearchOfItemsByReturnOrderId(returnOrderId, dbProfile.getLocationAndChildLocation(user.getUsername()),request, user);
        else
                throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> getPendingToreceive(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr =dbServiceResource.getByEmail(user.getUsername());
        System.out.println("Location ID =>> "+sr.getLocation().getLocationId());
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW_ALL)||dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW))
            return dbReturnOrderItem.getPendingReturnOrderItemToReceive(sr.getLocation().getLocationId());
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public void receiveItem(List<Map<String, Object>> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        if(request!=null&&!request.isEmpty()){
            ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
            for(Map<String,Object> req:request){
                Long returnOrderItemId=Long.parseLong(req.get("returnOrderItemId").toString());
                Optional<BtProductReturnOrderItem> returnOrderOptionalItem=dbReturnOrderItem.findById(returnOrderItemId);
                if(!returnOrderOptionalItem.isPresent())
                    throw new BadRequestException("Invalid return order item id");
                BtProductReturnOrderItem returnOrderItem=returnOrderOptionalItem.get();

                BtProductReturnOrder returnOrder =dbReturnOrder.findById(returnOrderItem.getReturnOrder().getId()).get();

                BtProductItem productItem=dbStock.findByProductAndWarehouse(returnOrderItem.getProducts(), sr.getLocation());

                Float customerInventoryQuantity=Float.parseFloat(req.get("customerQuantity").toString());
                Float exicomInventoryQuantity=Float.parseFloat(req.get("exicomQuantity").toString());

                dbReturnOrderItem.addReturnOrderReceivedQuantity(returnOrderItem.getId(), exicomInventoryQuantity, false, user);
                dbReturnOrderItem.addReturnOrderReceivedQuantity(returnOrderItem.getId(), customerInventoryQuantity, true, user);

                dbReturnOrder.addReturnReceivedQuantity(returnOrder.getId(), exicomInventoryQuantity, false, user);
                dbReturnOrder.addReturnReceivedQuantity(returnOrder.getId(), customerInventoryQuantity, true, user);
                if(productItem==null){

                    productItem=new BtProductItem();
                    productItem.setCustomerTotalBerQuantity(0F);
                    productItem.setCustomerTotalFaultyQuantity(0F);
                    productItem.setCustomerTotalOkQuantity(0F);
                    productItem.setCustomerTotalQuantity(0F);
                    productItem.setCustomerTotalRejectedQuantity(0F);
                    productItem.setCustomerTotalScrapQuantity(0F);
                    productItem.setExicomTotalBerQuantity(0F);
                    productItem.setExicomTotalFaultyQuantity(0F);
                    productItem.setExicomTotalOkQuantity(0F);
                    productItem.setExicomTotalQuantity(0F);
                    productItem.setExicomTotalRejectedQuantity(0F);
                    productItem.setExicomTotalScrapQuantity(0F);
                    productItem.setOwner(new EmployeeDetails(user.getUsername()));
                    productItem.setProduct(returnOrderItem.getProducts());
                    productItem.setWarehouse(sr.getLocation());
                    
                    dbStock.saveDetails(productItem, user);
                }

                dbStock.addQuantity(productItem.getId(), customerInventoryQuantity, true, 2, user);
                dbStock.addQuantity(productItem.getId(), exicomInventoryQuantity, false, 2, user);

            }
        }
        
    }

    @Override
    public List<Map<String, Object>> getPendingToreceive(Long returnOrderId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                ServiceRecource sr =dbServiceResource.getByEmail(user.getUsername());
                System.out.println("Location ID =>> "+sr.getLocation().getLocationId());
                if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW_ALL)||dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrderItem.class, UserProfileRepository.VIEW))
                    return dbReturnOrderItem.getPendingReturnOrderItemToReceive(sr.getLocation().getLocationId(),returnOrderId);
                else
                    throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getPageByFilterName(String filtername,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException {
        
        if(dbProfile.hasObjectAccess(user.getUsername(),BtProductReturnOrder.class,UserProfileRepository.VIEW_ALL)){

            if(filtername.equalsIgnoreCase("Draft"))
                return dbReturnOrder.getPageByStatus(filtername, page);
            else if (filtername.equalsIgnoreCase("Received"))
                return dbReturnOrder.getPageByStatus(filtername, page);
            else if (filtername.equalsIgnoreCase("Submitted"))
                return dbReturnOrder.getPageByStatus("Submitted", page);
            else if (filtername.equalsIgnoreCase("Send To Me")){
                ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
                return dbReturnOrder.getPageSendToMe(sr.getLocation().getLocationId(), page);
            }
            else if (filtername.equalsIgnoreCase("Send By Me")){
                ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
                return dbReturnOrder.getPageSendByMe(sr.getLocation().getLocationId(),page);
            }
            else
                return dbReturnOrder.getPage(page);

        }
        else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductReturnOrder.class, UserProfileRepository.VIEW)){
            if(filtername.equalsIgnoreCase("Draft"))
                return dbReturnOrder.getPageByStatus(filtername,dbProfile.getLocationAndChildLocation(user.getUsername()), page);
            else if (filtername.equalsIgnoreCase("Received"))
                return dbReturnOrder.getPageByStatus(filtername,dbProfile.getLocationAndChildLocation(user.getUsername()), page);
            else if (filtername.equalsIgnoreCase("Submitted"))
                return dbReturnOrder.getPageByStatus("Submitted",dbProfile.getLocationAndChildLocation(user.getUsername()), page);
            else if (filtername.equalsIgnoreCase("Send To Me")){
                ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
                return dbReturnOrder.getPageSendToMe(sr.getLocation().getLocationId(), page);
            }
            else if (filtername.equalsIgnoreCase("Send By Me")){
                ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
                return dbReturnOrder.getPageSendByMe(sr.getLocation().getLocationId(),page);
            }
            else
                return dbReturnOrder.getPage(dbProfile.getLocationAndChildLocation(user.getUsername()),page);

        }
        else
            throw new UnauthoriseException("You are not authorised");

    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public void addItem(Long returnOrderId, Map<String,Object> item, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                Optional<BtProductReturnOrder> optionalOrder=dbReturnOrder.findById(returnOrderId);
                if(!optionalOrder.isPresent())
                    throw new BadRequestException("Wrong return order id");
                
                BtProductReturnOrder returnOrder=optionalOrder.get();

                ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
                if(sr.getLocation().getLocationId()!=returnOrder.getSource().getLocationId())
                    throw new UnauthoriseException("You are not authorised");
                if(!returnOrder.getReturnOrderStatus().equalsIgnoreCase("Draft"))
                    throw new UnauthoriseException("Invalid request, Request already submitted");

                BtProductReturnOrderItem returnOrderItem=new BtProductReturnOrderItem();
                returnOrderItem.setCustomerQuantityReceived(0F);
                returnOrderItem.setCustomerQuantityReturned(item.get("customerInventory")==null?0F:Float.parseFloat(item.get("customerInventory").toString()));
                returnOrderItem.setOwner(new EmployeeDetails(user.getUsername()));
                returnOrderItem.setProducts(new BtProducts(Long.parseLong(item.get("productId").toString())));
                returnOrderItem.setQuantityReceived(0F);
                returnOrderItem.setQuantityReturned(item.get("exicomIvnentory")==null?0F:Float.parseFloat(item.get("exicomIvnentory").toString()));
                returnOrderItem.setReturnOrder(returnOrder);

                dbReturnOrderItem.addItem(returnOrderItem, user);

                dbStock.subQuantity(returnOrderItem.getProducts(), sr.getLocation(), returnOrderItem.getQuantityReturned(), false, 2, user);
                dbStock.subQuantity(returnOrderItem.getProducts(), sr.getLocation(), returnOrderItem.getCustomerQuantityReturned(), true, 2, user);

                dbReturnOrder.addReturnedQuantity(returnOrder, returnOrderItem.getCustomerQuantityReturned(), true, user);
                dbReturnOrder.addReturnedQuantity(returnOrder, returnOrderItem.getQuantityReturned(), false, user);

                item.put("returnOrderItemId", returnOrder.getId());
                item.put("returnOrderItemManualId", returnOrder.getManualId());
        
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public void deleteItem(Long returnOrderId,Long returnOrderItemId, LoginUser user) throws BadRequestException, UnauthoriseException {

        ServiceRecource sr =dbServiceResource.getByEmail(user.getUsername());
        Optional<BtProductReturnOrderItem> optionalReturnOrderItem=dbReturnOrderItem.findById(returnOrderItemId);
        if(!optionalReturnOrderItem.isPresent())
            throw new BadRequestException("Invalid request, Return order item not found");
        BtProductReturnOrderItem returnOrderItem=optionalReturnOrderItem.get();
        BtProductReturnOrder returnOrder=dbReturnOrder.findById(returnOrderItem.getReturnOrder().getId()).get();

        if(!returnOrder.getId().equals(returnOrderId))
            throw new BadRequestException("Invalid request, Return order id not found");

        if(returnOrder.getSource().getLocationId()!=sr.getLocation().getLocationId())
            throw new UnauthoriseException("You are not authorised");

        if(!returnOrder.getReturnOrderStatus().equalsIgnoreCase("Draft"))
            throw new BadRequestException("Invalid request, Return order is not in draft mode");
        dbReturnOrder.subReturnedQuantity(returnOrder, returnOrderItem.getQuantityReturned(), false, user);
        dbReturnOrder.subReturnedQuantity(returnOrder, returnOrderItem.getCustomerQuantityReturned(), true, user);

        dbStock.addQuantity(returnOrderItem.getProducts(), sr.getLocation(), returnOrderItem.getQuantityReturned(), false, 2, user);
        dbStock.addQuantity(returnOrderItem.getProducts(), sr.getLocation(), returnOrderItem.getCustomerQuantityReturned(), true, 2,user);

        dbReturnOrderItem.deleteDetails(returnOrderItem, user);

    }

    @Override
    public BtPacking getPacking(Long id) {
        BtProductReturnOrder returnOrder=dbReturnOrder.getById(id);
        if(returnOrder!=null){
            BtPacking packing=new BtPacking();
            packing.setCreationDate(returnOrder.getCreatedDate());
            packing.setDestinationLegalEntity(returnOrder.getDestination().getLegalEntity().getLegalEntityName());
            packing.setDestinationWarehouseName(returnOrder.getDestination().getLocationName());
            packing.setRef(returnOrder.getManualId());
            packing.setRefType("ReturnOrder");
            packing.setSourceLegalEntity(returnOrder.getSource().getLegalEntity().getLegalEntityName());
            packing.setSrouceWarehouseName(returnOrder.getSource().getLocationName());
            
            List<BtPackingItem> items= new ArrayList<BtPackingItem>();
            List<Map<String,Object>> lstItems=dbReturnOrderItem.getListReturnOrderItemsByReturnOrderId(id);
            if(lstItems!=null&&!lstItems.isEmpty()){
                if(lstItems.size()>0){
                    for(Map<String,Object> obj:lstItems){
                        BtPackingItem _item=new BtPackingItem();
                        _item.setDescription(MiscellaneousUtils.objToString(obj.get("productDescription")));
                        _item.setPartCode(MiscellaneousUtils.objToString(obj.get("productManualId")));
                        _item.setProductStatus("Faulty");
                        _item.setProductType(MiscellaneousUtils.objToString(obj.get("productType")));
                        _item.setQuantity(Float.parseFloat(MiscellaneousUtils.objToString(obj.get("returnOrderItemQuantityReturned"))));
                        items.add(_item);
                    }
                }
            }
            packing.setItems(items);
            return packing;
        }
        return null;
    }
    
}
