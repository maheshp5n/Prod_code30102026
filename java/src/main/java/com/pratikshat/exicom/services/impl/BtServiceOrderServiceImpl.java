package com.pratikshat.exicom.services.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtServiceOrder;
import com.pratikshat.exicom.entity.BtServiceOrderItem;
import com.pratikshat.exicom.entity.BtServiceOrderItemReceive;
import com.pratikshat.exicom.entity.BtServiceOrderReturn;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;
import com.pratikshat.exicom.model.response.BtPackingItem;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemReceiveRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemReturnRepository;
import com.pratikshat.exicom.repository.BtServiceOrderRepository;
import com.pratikshat.exicom.repository.BtServiceOrderReturnRepository;
import com.pratikshat.exicom.repository.LegalEntityRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtServiceOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtServiceOrderServiceImpl implements BtServiceOrderService {

    @Autowired
    BtServiceOrderRepository dbServiceOrder;

    @Autowired
    BtServiceOrderItemRepository dbServiceOrderItem;

    @Autowired
    BtProductItemRepository dbStock;

    @Autowired
    UserProfileRepository dbUserProfile;

    @Autowired
    ServiceRecourseRepository dbServiceResource;

    @Autowired
    BtCustomerInventoryInRepository dbCustomerInventoryIn;

    @Autowired
    BtProductsRepository dbProduct;

    @Autowired
    BtServiceOrderItemReceiveRepository dbItemReceive;

    @Autowired LocationRepository dbLocation;
    @Autowired LegalEntityRepository dbLegalEntity;

    @Autowired BtServiceOrderReturnRepository dbSoReturn;
    @Autowired
    BtServiceOrderItemReturnRepository dbItemReturn;
    

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.VIEW_ALL))
            return dbServiceOrder.getById(Long.parseLong(id.toString()));
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.VIEW))
            return dbServiceOrder.getById(Long.parseLong(id.toString()),
                    dbUserProfile.getLocationAndChildLocation(user.getUsername()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.VIEW_ALL))
            return dbServiceOrder.getPage(page);
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.VIEW))
            return dbServiceOrder.getPage(dbUserProfile.getLocationAndChildLocation(user.getUsername()), page);
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (!dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.CREATE))
            throw new UnauthoriseException("You are not authorised");

        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
        Location sourceLocation = sr.getLocation();
        Location destinationLocation = new Location(Long.parseLong(request.get("destinationLocationId").toString()));

        if (sourceLocation.getLocationId() == destinationLocation.getLocationId())
            throw new BadRequestException("Destination and source location can not be same");

        BtServiceOrder sOrder = new BtServiceOrder();

        sOrder.setDestinationLocation(destinationLocation);
        sOrder.setOwner(new EmployeeDetails(user.getUsername()));
        sOrder.setShippingMode(objToString(request.get("shippingMode")));
        sOrder.setShippingTo(objToString(request.get("shippingTo")));
        sOrder.setSourceLocation(sourceLocation);
        sOrder.setTotalNumberOfItemAssigned(0F);
        sOrder.setTotalNumberOfItemAssignedReturned(0F);
        sOrder.setTotalNumberOfItemReceived(0F);
        sOrder.setTotalNumberOfItemReturned(0F);
        sOrder.setTotalNumberOfItem(0F);
        sOrder.setTrackingNumber(objToString(request.get("trackingNumber")));

        List<Map<String, Object>> requestItems = (List<Map<String, Object>>) request.get("items");
        if (requestItems == null || requestItems.isEmpty())
            throw new BadRequestException("No Item found for service order");
        dbServiceOrder.saveDetails(sOrder, user);

        for (Map<String, Object> requestItem : requestItems) {
            BtServiceOrderItem sItem = new BtServiceOrderItem();
            sItem.setAssigned(false);
            sItem.setAssignedReturned(false);
            Boolean isCustomerInventory = Boolean.parseBoolean(requestItem.get("isCustomerInventory").toString());
            sItem.setCustomerInventory(isCustomerInventory);
            sItem.setOwner(new EmployeeDetails(user.getUsername()));
            if (requestItem.get("customerInventoryInId") != null
                    && !requestItem.get("customerInventoryInId").toString().equalsIgnoreCase("")) {
                Optional<BtCustomerInventoryIn> optionalCustomerInventoryIn = dbCustomerInventoryIn
                        .findById(Long.parseLong(requestItem.get("customerInventoryInId").toString()));
                if (!optionalCustomerInventoryIn.isPresent())
                    throw new BadRequestException("Wrong/Invalid customer inventory / BRC case id");
                BtCustomerInventoryIn btCustomerInventoryIn = optionalCustomerInventoryIn.get();

                if (btCustomerInventoryIn.getServiceOrderDone())
                    throw new BadRequestException("BRC case number/ Customer inventory in number "
                            + btCustomerInventoryIn.getManualId() + " has already forwarded for service order");

                sItem.setProduct(btCustomerInventoryIn.getProduct());
                sItem.setSerialNumber(btCustomerInventoryIn.getAssetsSerialNumber());
                sItem.setInCustomerInventory(btCustomerInventoryIn);

                dbStock.subQuantity(btCustomerInventoryIn.getProduct(), sourceLocation, 1F, true, 2, user);
                btCustomerInventoryIn.setServiceOrderDone(true);
                btCustomerInventoryIn.setCustomerInventoryStatus("Under Repaired");
                btCustomerInventoryIn.setCustomerInventoryStage("Service Order Prepared");
                btCustomerInventoryIn.setInStock(false);
                dbCustomerInventoryIn.updateDetails(btCustomerInventoryIn, user);
            } else {
                Optional<BtProducts> optionalproduct = dbProduct
                        .findById(Long.parseLong(requestItem.get("productId").toString()));
                if (!optionalproduct.isPresent())
                    throw new BadRequestException("Invalid product id");
                BtProducts btProducts = optionalproduct.get();
                sItem.setProduct(btProducts);
                sItem.setSerialNumber(objToString(requestItem.get("serialNumber")));

                dbStock.subQuantity(btProducts, sourceLocation, 1F, isCustomerInventory, 2, user);
            }
            sItem.setReceived(false);
            sItem.setReturnedToSource(false);
            sItem.setServiceOrder(sOrder);
            sItem.setaStatus(2);

            dbServiceOrderItem.saveDetails(sItem, user);
            dbServiceOrder.addTotalNumberOfItem(sOrder, 1F, user);

        }

        request.put("id", sOrder.getId());
        request.put("manualId", sOrder.getManualId());

        return request;
    }

    @Override
    /*
     * 
     * DO NOT USE THIS METHOD AS IT DOES NOT UPDATE ANY THING IS ONLY BLANK METHOD
     * 
     */
    @Deprecated
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.VIEW_ALL))
            return dbServiceOrder.advanceSearch(searchRequest, null, user);
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.VIEW))
            return dbServiceOrder.advanceSearch(searchRequest,
                    dbUserProfile.getLocationAndChildLocation(user.getUsername()), user);
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> getPendingProductForServiceOrder(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());

        return dbStock.getPendingFaultyProductItemforReturnOrder(sr.getLocation().getLocationId());
    }

    @Override
    public List<Map<String, Object>> getPendingToCustomerInventoryForSO(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());

        return dbCustomerInventoryIn.getPendingCustomerInventoryForServiceOrder(sr.getLocation().getLocationId());
    }

    @Override
    public List<Map<String, Object>> getPendingToCustomerInventoryForSOByProductId(Long productId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
        return dbCustomerInventoryIn
                .getPendingCustomerInventoryByProductIdForServiceOrder(sr.getLocation().getLocationId(), productId);
    }

    private String objToString(Object details) {
        return details == null ? "" : details.toString();
    }

    @Override
    public Page<Map<String, Object>> getPage(String filtername, Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if ((dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class, UserProfileRepository.VIEW_ALL)
                || dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrder.class,
                        UserProfileRepository.VIEW))) {
            ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
            if (filtername.equalsIgnoreCase("sendtome"))
                return dbServiceOrder.getSendToMe(sr.getLocation().getLocationId(), page);
            else if (filtername.equalsIgnoreCase("sendbyme"))
                return dbServiceOrder.getSendToMe(sr.getLocation().getLocationId(), page);
            else if (filtername.equalsIgnoreCase("pendingtoreceive"))
                return dbServiceOrder.getPendingToReceive(sr.getLocation().getLocationId(), page);
            else if (filtername.equalsIgnoreCase("received")) {
            	System.out.println("Received");
                return dbServiceOrder.getReceived(sr.getLocation().getLocationId(), page);}
            else if (filtername.equalsIgnoreCase("partiallyassigned"))
                return dbServiceOrder.getPartiallyAssigned(sr.getLocation().getLocationId(), page);
            else if (filtername.equalsIgnoreCase("fullyassigned"))
                return dbServiceOrder.getFullyAssigned(sr.getLocation().getLocationId(), page);
            else
                throw new BadRequestException("Invalid filter ");
        } else
            throw new UnauthoriseException("You are not authorised");

    }

    @Override
    public List<Map<String, Object>> getServiceOrderItem(Long serviceOrderId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItem.class, UserProfileRepository.VIEW_ALL)
                || dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItem.class,
                        UserProfileRepository.VIEW))
            return dbServiceOrderItem.getItemByServiceOrderId(serviceOrderId);
        else
            throw new UnauthoriseException("You are not authorised");

    }

    @Override
    public Page<Map<String, Object>> getServiceOrderItem(Long serviceOrderId, Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItem.class, UserProfileRepository.VIEW_ALL)
                || dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItem.class,
                        UserProfileRepository.VIEW))
            return dbServiceOrderItem.getItemPageByServiceOrderId(serviceOrderId, page);
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> getServiceOrderItemPendingToReceive(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
        return dbServiceOrderItem.getPendingitemToReceive(sr.getLocation().getLocationId());
    }

    @Override
    public List<Map<String, Object>> getServiceOrderItemPendingToAssign(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());

        return dbServiceOrderItem.getPendingItemToAssign(sr.getLocation().getLocationId());
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public void receiveServiceOrderItem(List<Map<String, Object>> serviceOrderitems, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (!dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItemReceive.class,
                UserProfileRepository.CREATE))
            throw new UnauthoriseException("You are not authorised");

        // ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());

        // Location destiLocation = sr.getLocation();
        for (Map<String, Object> item : serviceOrderitems) {
            receiveServiceOrderItem(item,  user);
            // Optional<BtServiceOrderItem> optionalItem = dbServiceOrderItem
            //         .findById(Long.parseLong(item.get("serviceOrderItemId").toString()));
            // if (!optionalItem.isPresent())
            //     throw new BadRequestException("Invalid service order item id");
            // BtServiceOrderItem _item = optionalItem.get();

            // BtServiceOrder serviceOrder = dbServiceOrder.findById(_item.getServiceOrder().getId()).get();
            // if (serviceOrder.getDestinationLocation().getLocationId() != destiLocation.getLocationId())
            //     throw new UnauthoriseException("You are not authorised");

            // Optional<BtServiceOrderItemReceive> optionalRItem = dbItemReceive.findById(_item.getId());
            // if (optionalRItem.isPresent())
            //     throw new BadRequestException("Item already received");

            // BtServiceOrderItemReceive receive = new BtServiceOrderItemReceive();
            // receive.setId(_item.getId());
            // receive.setOwner(new EmployeeDetails(user.getUsername()));
            // receive.setServiceOrderItem(_item);
            // dbItemReceive.saveDetails(receive, user);
            // _item.setReceived(true);
            // dbServiceOrderItem.updateDetails(_item, user);
            // dbStock.addQuantity(_item.getProduct(), destiLocation, 1F, _item.getCustomerInventory(), _item.getaStatus(),
            //         user);
        }
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public void receiveServiceOrderItem(Map<String, Object> item, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (!dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItemReceive.class,
                UserProfileRepository.CREATE))
            throw new UnauthoriseException("You are not authorised");

        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());

        Location destiLocation = sr.getLocation();

        Optional<BtServiceOrderItem> optionalItem = dbServiceOrderItem
                .findById(Long.parseLong(item.get("serviceOrderItemId").toString()));
        if (!optionalItem.isPresent())
            throw new BadRequestException("Invalid service order item id");
        BtServiceOrderItem _item = optionalItem.get();

        BtServiceOrder serviceOrder = dbServiceOrder.findById(_item.getServiceOrder().getId()).get();
        if (serviceOrder.getDestinationLocation().getLocationId() != destiLocation.getLocationId())
            throw new UnauthoriseException("You are not authorised");
        if(_item.getCustomerInventory()!=null&&_item.getInCustomerInventory()!=null){
            BtCustomerInventoryIn _in= dbCustomerInventoryIn.findById(_item.getInCustomerInventory().getId()).get();//_item.getInCustomerInventory();
            _in.setCustomerInventoryStatus("Under Repaired");
            _in.setCustomerInventoryStage("Service Order Received Confirmed");
            dbCustomerInventoryIn.updateDetails(_in, user);
        }

        Optional<BtServiceOrderItemReceive> optionalRItem = dbItemReceive.findById(_item.getId());
        if (optionalRItem.isPresent())
            throw new BadRequestException("Item already received");

        BtServiceOrderItemReceive receive = new BtServiceOrderItemReceive();
        receive.setId(_item.getId());
        receive.setOwner(new EmployeeDetails(user.getUsername()));
        receive.setServiceOrderItem(_item);
        dbItemReceive.saveDetails(receive, user);
        _item.setReceived(true);
        
        dbServiceOrderItem.updateDetails(_item, user);
        dbServiceOrder.addTotalNumberOfItemReceived(serviceOrder, 1F, user);
       // dbItemReceive.saveDetails(receive, user);

        dbStock.addQuantity(_item.getProduct(), destiLocation, 1F, _item.getCustomerInventory(), _item.getaStatus(),
                user);

    }

    @Override
    public List<Map<String, Object>> getRepairCenterList(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return dbServiceOrder.getRepairCenterList();
    }

    @Override
    public List<Map<String, Object>> advanceSearchItem(Long serviceOrderId,Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItem.class, UserProfileRepository.VIEW_ALL))
            return dbServiceOrderItem.advanceSearch(serviceOrderId, request, null, user);
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtServiceOrderItem.class, UserProfileRepository.VIEW))
            return dbServiceOrderItem.advanceSearch(serviceOrderId, request, dbUserProfile.getLocationAndChildLocation(user.getUsername()), user);
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> getServiceOrderItemPendingToReceive(Long serviceOrderId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
                return dbServiceOrderItem.getPendingitemToReceive(serviceOrderId,sr.getLocation().getLocationId());
    }

    @Override
    public List<Map<String, Object>> getServiceOrderItemPendingToAssign(Long serviceOrderId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
        return dbServiceOrderItem.getPendingItemToAssign(serviceOrderId,sr.getLocation().getLocationId());
    }

    @Override
    public List<Map<String, Object>> getPendingToCustomerInventoryForSOByProductId(Long productId, String challanNumber,
            LoginUser user) throws BadRequestException, UnauthoriseException {
                ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
                return dbCustomerInventoryIn
                        .getPendingCustomerInventoryByProductIdForServiceOrder(sr.getLocation().getLocationId(),productId,challanNumber);
    }

    @Override
    public BtPacking getPackingSo(Long id) {
        Optional<BtServiceOrder> optionalServiceOrder=dbServiceOrder.findById(id);
        if(!optionalServiceOrder.isPresent()) 
            throw new RuntimeException("Service Order not found, Invalid service Order Id");
        BtServiceOrder so=optionalServiceOrder.get();
        BtPacking packing=new BtPacking();
        Location dLocation = dbLocation.findByLocationId(so.getDestinationLocation().getLocationId());
        Location sLocation = dbLocation.findByLocationId(so.getSourceLocation().getLocationId());
        LegalEntity dLegalEntity= dbLegalEntity.findByLegalEntityId(dLocation.getLegalEntity().getLegalEntityId());
        LegalEntity sLegalEntity = dbLegalEntity.findByLegalEntityId(sLocation.getLegalEntity().getLegalEntityId());
        packing.setCreationDate(so.getCreatedDate());
        packing.setDestinationLegalEntity(dLegalEntity.getLegalEntityName());
        packing.setDestinationWarehouseName(dLocation.getLocationName());
        packing.setRef(so.getManualId());
        packing.setRefType("Service Order");
        packing.setSourceLegalEntity(sLegalEntity.getLegalEntityName());
        packing.setSrouceWarehouseName(sLocation.getLocationName());
        
        List<BtPackingItem> pItems=new ArrayList<BtPackingItem>();

        List<Map<String,Object>> soItems=dbServiceOrderItem.getItemByServiceOrderId(id);
        if(soItems!=null){
            if(!soItems.isEmpty()&&soItems.size()>0){
                for(Map<String,Object> obj:soItems){
                    BtPackingItem _item=new BtPackingItem();
                    _item.setDescription(MiscellaneousUtils.objToString(obj.get("productDescription")));
                    _item.setPartCode(MiscellaneousUtils.objToString(obj.get("productPartcode")));
                    String status="";
                    String _status=MiscellaneousUtils.objToString(obj.get("arriableStatus"));
                    if(_status.equalsIgnoreCase("1"))
                        status="OK";
                    if(_status.equalsIgnoreCase("2"))
                        status="Faulty";
                    if(_status.equalsIgnoreCase("5"))
                        status="BER";

                    _item.setProductStatus(status);
                    _item.setProductType(MiscellaneousUtils.objToString(obj.get("productType")));
                    _item.setQuantity(1F);
                    _item.setSerialNumber(MiscellaneousUtils.objToString(obj.get("serviceOrderItemSerialNumber")));
                    pItems.add(_item);
                }
            }
        }

        packing.setItems(pItems);


        return packing;
    }

    @Override
    public BtPacking getPackingSor(Long id) {
        Optional<BtServiceOrderReturn> optionaSor=dbSoReturn.findById(id);
        if(!optionaSor.isPresent())
            throw new RuntimeException("SO Return Order not found, Invalid id");
        BtServiceOrderReturn sor=optionaSor.get();
        BtPacking packing=new BtPacking();
        Location dLocation = dbLocation.findByLocationId(sor.getDestinationLocation().getLocationId());
        Location sLocation = dbLocation.findByLocationId(sor.getSourceLocation().getLocationId());
        LegalEntity dLegalEntity = dbLegalEntity.findByLegalEntityId(sLocation.getLegalEntity().getLegalEntityId());
        LegalEntity sLegalEntity = dbLegalEntity.findByLegalEntityId(dLocation.getLegalEntity().getLegalEntityId());

        packing.setCreationDate(sor.getCreatedDate());
        packing.setDestinationLegalEntity(dLegalEntity.getLegalEntityName());
        packing.setDestinationWarehouseName(dLocation.getLocationName());
        packing.setRef(sor.getManualId());
        packing.setRefType("ServiceOrderReturn");
        packing.setSourceLegalEntity(sLegalEntity.getLegalEntityName());
        packing.setSrouceWarehouseName(sLocation.getLocationName());
        List<BtPackingItem> lsItems=new ArrayList<BtPackingItem>();
        List<Map<String,Object>> items=dbItemReturn.getListbysoreturnId(id);
        if(items!=null&&!items.isEmpty()&&items.size()>0){
            for(Map<String,Object> obj:items){
                BtPackingItem _item=new BtPackingItem();
                _item.setDescription(MiscellaneousUtils.objToString(obj.get("productDescription")));
                _item.setPartCode(MiscellaneousUtils.objToString(obj.get("productPartcode")));
                String status="";
                String _status=MiscellaneousUtils.objToString(obj.get("soReturnedStatus"));
                if(_status.equalsIgnoreCase("1"))
                    status="OK";
                if(_status.equalsIgnoreCase("2"))
                    status="Faulty";
                if(_status.equalsIgnoreCase("5"))
                    status="BER";
                _item.setProductStatus(status);
                _item.setProductType(MiscellaneousUtils.objToString(obj.get("productType")));
                _item.setQuantity(1F);
                _item.setSerialNumber(MiscellaneousUtils.objToString(obj.get("itemSerialNumber")));
                lsItems.add(_item);
            }
        }
        packing.setItems(lsItems);

        return packing;
    }

    

}
