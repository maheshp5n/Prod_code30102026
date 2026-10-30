package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtPartcodeChange;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtPartcodeChangeRepository;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtPartcodeChangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtPartcodeChangeServiceImpl implements BtPartcodeChangeService {

    @Autowired UserProfileRepository dbUserProfile;
    @Autowired BtPartcodeChangeRepository dbPartcodeChange;
    @Autowired BtProductItemRepository dbStock;
    @Autowired ServiceRecourseRepository dbResource;
    @Autowired BtProductsRepository dbProduct;

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        if(dbUserProfile.hasObjectAccess(user.getUsername(), BtPartcodeChange.class, UserProfileRepository.VIEW_ALL))
            return dbPartcodeChange.getSingle(Long.parseLong(id.toString()));
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtPartcodeChange.class, UserProfileRepository.VIEW))
            return dbPartcodeChange.getSingle(Long.parseLong(id.toString()), dbUserProfile.getLocationAndChildLocation(user.getUsername()), dbUserProfile.getListOfUserAndChildByUser(user.getUsername()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
                if(dbUserProfile.hasObjectAccess(user.getUsername(), BtPartcodeChange.class, UserProfileRepository.VIEW_ALL))
                return dbPartcodeChange.getPage(page);
            else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtPartcodeChange.class, UserProfileRepository.VIEW))
                return dbPartcodeChange.getPage(dbUserProfile.getLocationAndChildLocation(user.getUsername()), dbUserProfile.getListOfUserAndChildByUser(user.getUsername()),page);
            else
                throw new UnauthoriseException("You are not authorised");
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE )
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(!dbUserProfile.hasObjectAccess(user.getUsername(), BtPartcodeChange.class, UserProfileRepository.CREATE))
                throw new UnauthoriseException("You are not authorised");
        ServiceRecource sr=dbResource.getByEmail(user.getUsername());
        if(sr==null||sr.getLocation()==null)
                throw new UnauthoriseException("You are not authorised");
        Optional<BtProducts> optionalFromProduct=dbProduct.findById(Long.parseLong(request.get("fromProductId").toString()));
        if(!optionalFromProduct.isPresent())
                throw new BadRequestException("Invalid from product id");
        Optional<BtProducts> optionalToProduct=dbProduct.findById(Long.parseLong(request.get("toProductId").toString()));
        if(!optionalToProduct.isPresent())
                throw new BadRequestException("Invalid to product id");
        int productState=Integer.parseInt(request.get("productStateId").toString());
        float qty=Float.parseFloat(request.get("qty").toString());
        BtProducts fromProduct=optionalFromProduct.get();
        BtProducts toProduct=optionalToProduct.get();

        if(fromProduct.getId().equals(toProduct.getId()))
                throw new BadRequestException("Both product can not be same");
        BtPartcodeChange partChange=new BtPartcodeChange();
        partChange.setFromProduct(fromProduct);
        partChange.setLocation(sr.getLocation());
        partChange.setOwner(new EmployeeDetails(user.getUsername()));
        partChange.setProductState(productState);
        partChange.setQuantity(qty);
        partChange.setToProduct(toProduct);
        partChange.setRemarks(MiscellaneousUtils.objToString(request.get("remarks")));
        
        dbPartcodeChange.saveDetails(partChange, user);

        dbStock.subQuantity(fromProduct, sr.getLocation(), qty , false, productState, user);
        dbStock.addQuantity(toProduct, sr.getLocation(), qty, false, productState,user);

        request.put("id", partChange.getId());
        request.put("manualId", partChange.getManualId());

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
    
}
