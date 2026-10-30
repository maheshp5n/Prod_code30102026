package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtProductStatusChangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bt/api/productstatuschange")
public class BtProductStatusChangeController {
    
    @Autowired BtProductStatusChangeService dbStatusChange;

    @GetMapping
    public Page<Map<String,Object>> getList(Pageable page,@CurrentUser LoginUser user,@RequestParam(value="filterparam",required = false)String filterparam) throws BadRequestException,UnauthoriseException{
        return dbStatusChange.getPage(page, user);
    }

    @GetMapping("{id}")
    public Map<String,Object> getSingle(@PathVariable("id")Long id,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbStatusChange.getSingle(id, user);
    }

    @PostMapping
    public Map<String,Object> doChange(@RequestBody Map<String,Object> request, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbStatusChange.saveObject(request, user);
    }
}
