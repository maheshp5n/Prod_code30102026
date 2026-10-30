package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtJobCardQcFileService;
import com.pratikshat.exicom.services.BtJobCardQcService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("bt/api/jobcardqc")
public class BtJobCardQcController {

    @Autowired
    BtJobCardQcService dbQc;
    @Autowired BtJobCardQcFileService dbQcFile;

    @GetMapping
    Page<Map<String, Object>> getPage(Pageable page, @CurrentUser LoginUser user)
            throws BadRequestException, UnauthoriseException {
        return dbQc.getPage(page, user);
    }

    @GetMapping("{id}")
    Map<String, Object> getSingle(@PathVariable("id") Long id, @CurrentUser LoginUser user)
            throws BadRequestException, UnauthoriseException {
        return dbQc.getSingle(id, user);
    }

    @PostMapping
    Map<String, Object> postData(@RequestBody Map<String, Object> request, @CurrentUser LoginUser user)
            throws BadRequestException, UnauthoriseException {
        return dbQc.saveObject(request, user);
    }

    @PutMapping("{id}")
    Map<String, Object> updateDetails(@RequestBody Map<String, Object> request, @CurrentUser LoginUser user)
            throws BadRequestException, UnauthoriseException {
        return dbQc.updateObject(request, user);
    }

    @GetMapping("jobcard/{id}")
    ResponseEntity<?> getQcByJobCard(@PathVariable("id") Long id,
            @RequestParam(value = "list", required = false) String list, Pageable page, @CurrentUser LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (list != null && list.equalsIgnoreCase("yes"))
            return ResponseEntity.ok(dbQc.getQcListByJobcardid(id, user));// dbQc.getQcListByJobcardid(id, user);
        return ResponseEntity.ok(dbQc.getQcPageByJobcardId(id, page, user));
    }

    @PostMapping("uploadfile/{id}")
    public String uploadFiles(@PathVariable("id") Long id,MultipartFile file,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbQcFile.uploadFile(id, file, user);
        return "{}";
    }
    @GetMapping("files/{id}")
    public Map<String,Object> getFiles(@PathVariable("id")Long id,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbQcFile.getFilesByJobCardId(id, user);
    }

    @GetMapping("files/download/{id}")
    public void dowlnaloadFile(@CurrentUser LoginUser user,@PathVariable("id")Long id,HttpServletResponse response) throws BadRequestException,UnauthoriseException{
        dbQcFile.downLoadFile(id, response, user);
    }
    
    //Ruchit
    
    @GetMapping("advancesearch")
    public List<Map<String,Object>> advanceSearch(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException {
        return dbQc.advanceSearch(request, user);
    }

}
