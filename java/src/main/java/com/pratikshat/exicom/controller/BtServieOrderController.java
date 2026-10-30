package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtServiceOrderService;

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
@RequestMapping("bt/api/serviceorder")
public class BtServieOrderController {
    
    @Autowired
    BtServiceOrderService dbServiceOrder;

    @GetMapping
    public Page<Map<String,Object>> getPage(@RequestParam(value = "filtername",required=false)String filterparam ,Pageable page,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        if(filterparam==null||filterparam.equalsIgnoreCase("")||filterparam.equalsIgnoreCase("All"))
            return dbServiceOrder.getPage(page, user);
        else
            return dbServiceOrder.getPage(filterparam, page, user) ;
    }

    @GetMapping("{id}")
    public Map<String,Object> getSingle(@PathVariable("id")Long serviceOrderId,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getSingle(serviceOrderId, user);
    }

    @GetMapping("{id}/items")
    public ResponseEntity<?> getListOrpage(@PathVariable("id")Long serviceOrderId, @RequestParam(value = "list")String list,Pageable page,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        if(list!=null&&list.equalsIgnoreCase("yes"))
            return ResponseEntity.ok(dbServiceOrder.getServiceOrderItem(serviceOrderId, user));
        else
            return ResponseEntity.ok(dbServiceOrder.getServiceOrderItem(serviceOrderId, page, user));
    }

    @GetMapping("items/pendingtoreceive")
    public List<Map<String,Object>> getPendingToReceive(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getServiceOrderItemPendingToReceive(user);
    }

    @GetMapping("items/pendingtoreceive/{id}")
    public List<Map<String,Object>> getPendingToReceive(@PathVariable("id")Long serviceOrderId, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getServiceOrderItemPendingToReceive(serviceOrderId,user);
    }

    @PutMapping("items/pendingtoreceive")
    public String receivePendingToReceive(@RequestBody List<Map<String,Object>> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbServiceOrder.receiveServiceOrderItem(request, user);
        return "{}";
    }

    @PutMapping("{id}/items/pendingtoreceive")
    public String receivePendingToReceive(@PathVariable("id")Long serviceOrderItemId, @RequestBody List<Map<String,Object>> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbServiceOrder.receiveServiceOrderItem(request, user);
        return "{}";
    }

    @GetMapping("items/pendingtoassign")
    public List<Map<String,Object>> getPendingToAssign(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getServiceOrderItemPendingToAssign(user);
    }

    @GetMapping("items/pendingtoassign/{serviceOrderId}")
    public List<Map<String,Object>> getPendingToAssign(@CurrentUser LoginUser user,@PathVariable("serviceOrderId")Long serviceOrderId) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getServiceOrderItemPendingToAssign(serviceOrderId,user);
    }

    @PostMapping
    public Map<String,Object> postServiceOrder(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
       
        return dbServiceOrder.saveObject(request, user);
    }

    @GetMapping("pendingstoc")
    public List<Map<String,Object>> getPendingStockForServiceOrder(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getPendingProductForServiceOrder(user);
    }
    @GetMapping("pendingstoc/customerinventory")
    public List<Map<String,Object>> getPendingStockCustomerInventory(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getPendingToCustomerInventoryForSO(user);
    }

    @GetMapping("pendingstoc/customerinventory/{productid}")
    public List<Map<String,Object>> getPendingStockforCustomerInentory(@PathVariable("productid")Long productId, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getPendingToCustomerInventoryForSOByProductId(productId, user);
    }

    @GetMapping("pendingstoc/customerinventorybychallannumber/{productid}")
    public List<Map<String,Object>> getPendingStockforCustomerInentory(@PathVariable("productid")Long productId,@RequestParam("challanNumber")String challanNumber, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getPendingToCustomerInventoryForSOByProductId(productId, challanNumber,user);
    }

    @GetMapping("servicerepaircenter")
    public List<Map<String,Object>> getRepariCenterList(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.getRepairCenterList(user);
    }

    @GetMapping("advancesearch")
    public List<Map<String,Object>> getAdvanceSearch(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.advanceSearch(request, user);
    }

    @GetMapping("{serviceorderid}/advancesearch")
    public List<Map<String,Object>> getAdvanceSearchitem(@PathVariable("serviceorderid")Long serviceOrderId, @RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbServiceOrder.advanceSearchItem(serviceOrderId,request, user);
    }
    
}
