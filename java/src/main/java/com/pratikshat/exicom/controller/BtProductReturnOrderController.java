package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtProductItemService;
import com.pratikshat.exicom.services.BtReturnOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bt/api/returnorder")
public class BtProductReturnOrderController {
    
    @Autowired
    BtReturnOrderService dbReturnOrderService;

    @Autowired
    BtProductItemService dbStock;

    @GetMapping
    public Page<Map<String,Object>> getReturnOrder(@CurrentUser LoginUser user,Pageable page,@RequestParam(value="filtername",required = false)String filtername) throws BadRequestException,UnauthoriseException{
        //FILTER NAME ARE LIKE Send To Me, Send By Me, Draft, Submitted, Received
        if(filtername!=null&&!filtername.equalsIgnoreCase(""))
            return dbReturnOrderService.getPageByFilterName(filtername, page, user);
        return dbReturnOrderService.getPage(page, user);
    }
    @GetMapping("{id}")
    public Map<String,Object> getSingle(@PathVariable("id")Long id,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturnOrderService.getSingle(id, user);
    }
    @GetMapping("searchforlist")
    public List<Map<String,Object>> searchReturnOrder(@RequestParam("search")String search,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturnOrderService.searchForList(search, user);
    }
    @GetMapping("advancesearch")
    public List<Map<String,Object>> advanceSearch(@RequestParam Map<String,Object> searchrequest,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturnOrderService.advanceSearch(searchrequest, user);
    }

    @GetMapping("{id}/items")
    public ResponseEntity<?> getItems(@PathVariable("id")Long returnOrderId,@RequestParam(value="view",required = false)String view,@CurrentUser LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException{
        if(view!=null&&view.equalsIgnoreCase("page"))
            return ResponseEntity.ok(dbReturnOrderService.getItemByReturnOrderId(returnOrderId, user,page));
        return ResponseEntity.ok(dbReturnOrderService.getListItemByReturnOrderId(returnOrderId, user));
    }

    @PostMapping
    public Map<String,Object> doReturnOrder(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbReturnOrderService.saveObject(request, user);
    }
    @PutMapping("{id}/changestatus")
    public Map<String,Object> changeStatus(@RequestBody Map<String,Object> changereq,@CurrentUser LoginUser user,@PathVariable("id")Long returnOrderId) throws BadRequestException,UnauthoriseException{
        if(!returnOrderId.equals(Long.parseLong(changereq.get("returnOrderId").toString())))
            throw new BadRequestException("Invalid return order id");
       return dbReturnOrderService.updateObject(changereq, user);
    }

    @PostMapping("{id}/items")
    public String addItem(@PathVariable("id")Long returnOrderId,@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbReturnOrderService.addItem(returnOrderId, request, user);
        return "{}";
    }

    @DeleteMapping("{id}/items/{itemid}")
    public String deleteItem(@PathVariable("id")Long returnOrderId,@PathVariable("itemid")Long itemid,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbReturnOrderService.deleteItem(returnOrderId,itemid, user);
        return "{}";
    }

    @GetMapping("{id}/items/advancesearch")
    public List<Map<String,Object>> itemAdvanceSearch(@RequestParam Map<String,Object> request,@PathVariable("id")Long returnOrderId,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        //dbReturnOrderService
        return dbReturnOrderService.advanceSearchOfItemsByReturnOrderId(returnOrderId, request, user);
    }

    @GetMapping("pendingitemtoreturn")
    public List<Map<String,Object>> getPendingItemToReturn(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbStock.getFaultyProductItemForReturnOrder( user);
    }

    @GetMapping("pendingitemtoreceive")
    public List<Map<String,Object>> getPendingItemToReceive(@CurrentUser LoginUser user,@RequestParam(value="returnorderid",required = false)Long returnOrderId) throws BadRequestException,UnauthoriseException{
        if(returnOrderId!=null&&returnOrderId>0L)
            return dbReturnOrderService.getPendingToreceive(returnOrderId, user);
        return dbReturnOrderService.getPendingToreceive(user);
    }

    @PostMapping("pendingitemtoreceive")
    public String receiveItem(@RequestBody List<Map<String,Object>> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException {
        dbReturnOrderService.receiveItem(request, user);
        return "{}";
    }


}

