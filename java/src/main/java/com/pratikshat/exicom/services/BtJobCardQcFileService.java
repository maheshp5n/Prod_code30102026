package com.pratikshat.exicom.services;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import org.springframework.web.multipart.MultipartFile;

public interface BtJobCardQcFileService {
    public void uploadFile(Long id,MultipartFile file,LoginUser user) throws BadRequestException,UnauthoriseException;
    public Map<String,Object> getFilesByJobCardId(Long id,LoginUser user ) throws BadRequestException,UnauthoriseException;

    public void downLoadFile(Long id,HttpServletResponse out,LoginUser user) throws BadRequestException,UnauthoriseException;
}
