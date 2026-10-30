package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtProductItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bt/api/invnetory")
public class BtProductItemController {
    @Autowired
    BtProductItemService dbStock;

    @GetMapping
    Page<Map<String,Object>> getStock(@CurrentUser LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException{
        return dbStock.getPage(page, user);
    }
    @GetMapping("{id}")
    Page<Map<String,Object>> getStockOfLocation(@PathVariable("id")Long locationId,@CurrentUser LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException{
        return dbStock.getPage(page, locationId, user);
    }

    @GetMapping("search")
    List<Map<String,Object>> getAdvanceSearch(@CurrentUser LoginUser user,@RequestParam Map<String,Object> request) throws BadRequestException,UnauthoriseException{
       return dbStock.advanceSearchMyStock(request,user);
    }

}
