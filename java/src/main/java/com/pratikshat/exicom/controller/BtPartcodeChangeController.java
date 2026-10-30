package com.pratikshat.exicom.controller;

import java.util.Map;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtPartcodeChangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bt/api/partcodechange")
public class BtPartcodeChangeController {
    
    @Autowired BtPartcodeChangeService dbpartcodechange;

    @GetMapping
    public Page<Map<String,Object>> getPage(@CurrentUser LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException{
        return dbpartcodechange.getPage(page, user);
    }

    @GetMapping("{id}")
    public Map<String,Object> getSingle(@PathVariable("id")Long id,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbpartcodechange.getSingle(id, user) ;
    }

    @PostMapping
    public Map<String,Object> postData(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbpartcodechange.saveObject(request, user);
    }

}
