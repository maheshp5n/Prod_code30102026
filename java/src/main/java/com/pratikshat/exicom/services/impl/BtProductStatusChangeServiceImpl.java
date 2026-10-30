package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.entity.BtProductStatusChange;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductStatusChangeRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductStatusChangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtProductStatusChangeServiceImpl implements BtProductStatusChangeService {

    @Autowired BtProductStatusChangeRepository dbProductChange;
    @Autowired BtProductsRepository dbProduct;
    @Autowired BtProductItemRepository dbStock;
    @Autowired ServiceRecourseRepository dbSr;
    @Autowired UserProfileRepository dbUserProfile;

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
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(!dbUserProfile.hasObjectAccess(user.getUsername(), BtProductStatusChange.class, UserProfileRepository.CREATE))
                throw new UnauthoriseException("You are not authorised");

        Long productId=Long.parseLong(request.get("productId").toString());
        Float quantity=Float.parseFloat(request.get("quantity").toString());
        Integer fromState=Integer.parseInt(request.get("fromState").toString());
        Integer toState=Integer.parseInt(request.get("toState").toString());
        String remarks=request.get("remarks").toString();

        ServiceRecource sr=dbSr.getByEmail(user.getUsername());
        if(sr==null || sr.getLocation()==null)
                throw new UnauthoriseException("You are not authorised");
        Optional<BtProducts> optionalProduct=dbProduct.findById(productId);
        if(!optionalProduct.isPresent())
                throw new BadRequestException("Product not found");
        
        BtProducts products=optionalProduct.get();

        dbStock.subQuantity(products, sr.getLocation(), quantity, false, fromState, user);
        dbStock.addQuantity(products, sr.getLocation(), quantity, false, toState,user);

        BtProductStatusChange _ch=new BtProductStatusChange();
        _ch.setFromState(fromState);
        _ch.setLocation(sr.getLocation());
        _ch.setOwner(new EmployeeDetails(user.getUsername()));
        _ch.setProduct(products);
        _ch.setToState(toState);
        _ch.setRemarks(remarks);    

        dbProductChange.saveDetails(_ch, user);
        request.put("id", _ch.getId());
        request.put("manualId", _ch.getManualId());
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
