package com.pratikshat.exicom.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


import com.pratikshat.exicom.utils.BtProductPackingPdfGen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bt/api/packinglist")
public class BtPackingListController {
    
    @Autowired
    BtProductPackingPdfGen pdf;

    @GetMapping("{id}")
    public void getPdf(@PathVariable("id")Long id,@RequestParam("type")String type,HttpServletResponse response) throws IOException, Exception{
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=packing_list_"+type+"_"+id+".pdf");
        if(type.equalsIgnoreCase("PT"))
            pdf.getTransferDocumentPT(id, response.getOutputStream());
        if(type.equalsIgnoreCase("RO"))
            pdf.getTransferDocumentRO(id, response.getOutputStream());
        if(type.equalsIgnoreCase("SO"))
            pdf.getTransferDocumentSO(id, response.getOutputStream());
        if(type.equalsIgnoreCase("SOR"))
            pdf.getTransferDocumentSOR(id, response.getOutputStream());

        response.setHeader(HttpHeaders.CONTENT_TYPE,"application/pdf");
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
