package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.DctRepairBulkConsumption;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.DctRepairBulkConsumptionRepository;
import com.pratikshat.exicom.repository.LocationProductItemRepository;
import com.pratikshat.exicom.repository.LocationProductLineItemRepository;
import com.pratikshat.exicom.repository.ProductLineItemRepository;
import com.pratikshat.exicom.repository.ProductRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.services.DctRepairBulkConsumptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DctRepairBulkConsumptionServiceImpl implements DctRepairBulkConsumptionService {

    @Autowired
    ProductRepository dbProduct;
    @Autowired
    LocationProductItemRepository dbStock;
    @Autowired
    LocationProductLineItemRepository dbLineItemStock;
    @Autowired
    ProductLineItemRepository dbProductItem;
    @Autowired
    ServiceRecourseRepository dbServiceResource;
    @Autowired
    DctRepairBulkConsumptionRepository dbConsumed;

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {

        return null;
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        return null;
    }

    @Override
    @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
            Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        

        Optional<Products> optionalConsumedProduct = dbProduct
                .findById(request.get("consumedProductPartcode").toString());
        if (!optionalConsumedProduct.isPresent())
            throw new BadRequestException(
                    "Consumed product partcode " + request.get("consumedProductPartcode") + " not found");

        Float consumeQuan = 0F;
        try {
            consumeQuan = Float.parseFloat(MiscellaneousUtils.objToString(request.get("consumedQuantity")));

        } catch (Exception e) {
            throw new BadRequestException("Invalid quantity");
        }

        if (consumeQuan.equals(0F) || consumeQuan < 0F)
            throw new BadRequestException("Invalid quantity");
        
        Products consumedproduct = optionalConsumedProduct.get();

        if (!consumedproduct.getProductType().equalsIgnoreCase("Component"))
            throw new BadRequestException("You can not consume product other then component");

        ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
        if (sr == null || sr.getLocation() == null)
            throw new UnauthoriseException("You are not authorised");
        LocationProductItem item = dbStock.findByProductAndLocation(consumedproduct, sr.getLocation());
        if (item == null)
            throw new BadRequestException("Stock does not exists for part code " + consumedproduct.getProductPartCode()
                    + " at your location");
        Float tQty = item.getOkQuantity();
        tQty = tQty - consumeQuan;
        if (tQty < 0)
            throw new BadRequestException("Stock does not exists for part code " + consumedproduct.getProductPartCode()
                    + " at your location");
        Float tFinal = item.getQuantityInOnHand();
        tFinal = tFinal - consumeQuan;
        if (tFinal < 0)
            throw new BadRequestException("Stock does not exists for part code " + consumedproduct.getProductPartCode()
                    + " at your location");
        item.setOkQuantity(tQty);
        item.setQuantityInOnHand(tFinal);
        dbStock.save(item);

        DctRepairBulkConsumption consumption = new DctRepairBulkConsumption();
        consumption.setConsumedProduct(consumedproduct);
        consumption.setConsumedQuantity(consumeQuan);
        consumption.setOwner(new EmployeeDetails(user.getUsername()));
        consumption.setRemarks(MiscellaneousUtils.objToString(request.get("remarks")));
        consumption.setLocation(sr.getLocation());
        dbConsumed.saveDetails(consumption, user);


        request.put("id", consumption.getId());
        request.put("manualId", consumption.getManualId());

        return request;
    }

    @Override
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        return null;
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

    // @Override
    // @Transactional(rollbackFor =
    // {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation
    // = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    // public List<Map<String, Object>> saveObject(List<Map<String, Object>>
    // request, LoginUser user)
    // throws BadRequestException, UnauthoriseException {
    // if(request!=null&&!request.isEmpty()){
    // for(Map<String,Object> _req:request){
    // saveObject(_req, user);
    // }
    // }
    // return request;
    // }

}
