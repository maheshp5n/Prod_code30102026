package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BtServiceOrderItemReturn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtServiceOrderReturnService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bt/api/serviceorder/return")
public class BtServiceOrderReturnController {
    
    @Autowired BtServiceOrderReturnService dbReturn;


    @GetMapping
    public Page<Map<String,Object>> getPage(@CurrentUser LoginUser user,Pageable page,@RequestParam(value = "filterparam", required = false) String filterparam) throws BadRequestException,UnauthoriseException{
        return dbReturn.getPage(page, user,filterparam);
    }

    @GetMapping("{id}")
    public Map<String,Object> getById(@PathVariable("id")Long id, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturn.getSingle(id, user);
    }

    @GetMapping("pendinglocation")
    public List<Map<String,Object>> getPendingLocation(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturn.getPendingLocationToTransfer(user);
    }

    @GetMapping("pendingitems/{locationid}")
    public List<Map<String,Object>> getPendingItemToReturnByLocation(@PathVariable("locationid")Long locationId,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturn.getPendingItemToReturn(user, locationId);
    }
    @PostMapping
    public String soReturn(@RequestBody Map<String,Object> request, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbReturn.saveObject(request, user);
        return "{}";
    }

    @PutMapping
    public String soReturnReceive(@RequestBody  List<Map<String,Object>> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbReturn.updateReceive(request, user);
        return "{}";
    }

    @GetMapping("items/{id}")
    public ResponseEntity<?> getItems(@PathVariable("id")Long id,@RequestParam(value = "list",required = false)String list, @CurrentUser LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException{
        if(list!=null&&list.equalsIgnoreCase("yes"))
            return ResponseEntity.ok(dbReturn.getItemList(id, user));
        return ResponseEntity.ok(dbReturn.getItemPage(id, page, user));
    }

    @GetMapping("pendingitemtoreceive")
    public List<Map<String,Object>> getpendingitemtoreceive(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturn.getPendingItemToReceive(user);
    }
}
