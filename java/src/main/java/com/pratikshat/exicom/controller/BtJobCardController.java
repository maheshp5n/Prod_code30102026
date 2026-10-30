package com.pratikshat.exicom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtJobCardFileService;
import com.pratikshat.exicom.services.BtJobCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("bt/api/jobcard")
public class BtJobCardController {
    
    @Autowired BtJobCardService dbJobcard;
    @Autowired BtJobCardFileService dbJobCardFile;

    @GetMapping("repariengineer")
    public List<Map<String,Object>> getRepariEngineer(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbJobcard.getRepariEngineerList(user);


    }

    @GetMapping
    public Page<Map<String,Object>> getpage(@CurrentUser LoginUser user,Pageable page,@RequestParam(value="filtername",required = false)String filterparam) throws BadRequestException,UnauthoriseException{
        if(filterparam==null|| filterparam.equalsIgnoreCase("all")||filterparam.equalsIgnoreCase(""))
            return dbJobcard.getPage(page, user);
        else if(filterparam.equalsIgnoreCase("tome"))
            return dbJobcard.getAssignedToMeJobCard(user, page);
        else if (filterparam.equalsIgnoreCase("tomepending"))
            return dbJobcard.getAssignedToMeJobCardPending(user, page);
        else if(filterparam.equalsIgnoreCase("rejectedtome"))
            return dbJobcard.getAssignedToMeRejected(user, page);
        else if(filterparam.equalsIgnoreCase("submittoqc"))
        	return dbJobcard.getSubmitToQc(user,page);
        else return dbJobcard.getPage(page, user);
    }

    @GetMapping("{jobcardid}")
    public Map<String,Object> getSingle(@PathVariable("jobcardid")Long id,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbJobcard.getSingle(id, user);
    }

    @PostMapping
    public Map<String,Object> createJobCard(@RequestBody List<Map<String,Object>> requests,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbJobcard.saveObject(requests, user);
        return new HashMap<>();
    }

    @PostMapping("single")
    public Map<String,Object> createJobCard(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbJobcard.saveObject(request, user);
        return request;
    }

    @PutMapping("{jobcardid}")
    public Map<String,Object> updateJobcard(@PathVariable("jobcardid")Long id,@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbJobcard.updateObject(request, user);
        return request;
    }

    @GetMapping("{jobcardid}/productconsumed")
    public ResponseEntity<?> getProductconsumedList(@PathVariable("jobcardid")Long jobcardid,@CurrentUser LoginUser user,@RequestParam(value = "list",required = false)String listView,Pageable page) throws BadRequestException,UnauthoriseException{
        if(listView!=null&&listView.equalsIgnoreCase("yes"))
            return ResponseEntity.ok(dbJobcard.getProductConsumedList(jobcardid, user));
        return ResponseEntity.ok(dbJobcard.getProductConsumed(jobcardid, page, user));
    }

    @GetMapping("{jobcardid}/qc")
    public ResponseEntity<?> getqcList(@PathVariable("jobcardid")Long jobcardid,@CurrentUser LoginUser user,@RequestParam(value = "list",required = false)String listView,Pageable page) throws BadRequestException,UnauthoriseException{
        if(listView!=null&&listView.equalsIgnoreCase("yes"))
            return ResponseEntity.ok(dbJobcard.getJobCardQcDetailsList(jobcardid, user));
        return ResponseEntity.ok(dbJobcard.getJobCardQcDetails(jobcardid, user,page));
    }

    @GetMapping("dropdown/resolution")
    public List<Map<String,Object>> getResolution(){
        return dbJobcard.getResolutionMethod();
    }
    @GetMapping("dropdown/batterybalanceat")
    public List<Map<String,Object>> getBatteryBalanceAt(){
        return dbJobcard.getBatteryBalanceAt();
    }
    @GetMapping("dropdown/berremarks")
    public List<Map<String,Object>> getBerRemrks(){
        return dbJobcard.getBerRemarks();
    }
    @GetMapping("dropdown/correctiveaction")
    public List<Map<String,Object>> getCorrective(){
        return dbJobcard.getCorrectiveAction();
    }
    @GetMapping("dropdown/engremarks")
    public List<Map<String,Object>> getRemarks(){
        return dbJobcard.getEngRemarks();
    }

    @PostMapping("uploadfile/{id}")
    public String uploadFiles(@PathVariable("id") Long id,MultipartFile file,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        dbJobCardFile.uploadFile(id, file, user);
        return "{}";
    }
    @GetMapping("files/{id}")
    public Map<String,Object> getFiles(@PathVariable("id")Long id,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbJobCardFile.getFilesByJobCardId(id, user);
    }

    @GetMapping("files/download/{id}")
    public void dowlnaloadFile(@CurrentUser LoginUser user,@PathVariable("id")Long id,HttpServletResponse response) throws BadRequestException,UnauthoriseException{
       dbJobCardFile.downLoadFile(id, response, user);
    }
    
    //Ruchit
    
    @GetMapping("advancesearch")
    public List<Map<String,Object>> advanceSearch(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException {
        return dbJobcard.advanceSearch(request, user);
    }
    
    //Ruchit
    @PutMapping("reassign/{id}")
    public Map<String,Object> jobCardReassign(@PathVariable("id")Long id,@RequestBody Map<String, Object> request, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
    	return dbJobcard.jobCardReassign(id,request,user);
    }
    
}
