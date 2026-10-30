package com.pratikshat.exicom.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtJobCardFile;
import com.pratikshat.exicom.entity.BtJobCardQc;
import com.pratikshat.exicom.entity.BtJobCardQcFile;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtJobCardFileRepository;
import com.pratikshat.exicom.repository.BtJobCardQcFileRepository;
import com.pratikshat.exicom.repository.BtJobCardQcRepository;
import com.pratikshat.exicom.repository.BtJobCardRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtJobCardQcFileService;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

@Service
public class BtJobCardQcFileServiceImpl implements BtJobCardQcFileService {

    @Value("${bt.jc.filelocation}")
    String fileLocation;

    @Autowired
    BtJobCardQcFileRepository dbJoBCardFile;
    @Autowired
    BtJobCardQcRepository dbJc;
    @Autowired
    UserProfileRepository dbProfile;

    private static final Logger LOGGER = LoggerFactory.getLogger(BtJobCardQcFileServiceImpl.class);

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},isolation = Isolation.SERIALIZABLE,propagation = Propagation.REQUIRES_NEW)
    public void uploadFile(Long id, MultipartFile file, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        if (dbProfile.hasObjectAccess(user.getUsername(), BtJobCardQcFile.class, UserProfileRepository.CREATE))
            throw new UnauthoriseException("You are not authorised");
        if (file.isEmpty())
            throw new BadRequestException("Invalid file");
        String fileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();

        if((file.getSize()/1024)>(1024*10))
            throw new BadRequestException("File size can not more then 10MB");

            if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".pdf")
            || fileName.endsWith(".png")
            || fileName.endsWith(".xlsx") || fileName.endsWith(".xlsb") || fileName.endsWith(".xltx")
            || fileName.endsWith(".xltm") || fileName.endsWith(".xls") || fileName.endsWith(".xlt")
            || fileName.endsWith(".xml") || fileName.endsWith(".xlam") || fileName.endsWith(".xla")
            || fileName.endsWith(".xlw") || fileName.endsWith(".xlr") || fileName.endsWith(".docx")
            || fileName.endsWith(".doc") || fileName.endsWith(".dot")) {


        } else
            throw new BadRequestException("Invalid file, " + fileName + " is not valid Image/PDF/WORD/EXCEL");

        Optional<BtJobCardQc> optionJc = dbJc.findById(id);
        if (!optionJc.isPresent())
            throw new BadRequestException("Invalid job card id");
        BtJobCardQcFile jFile = new BtJobCardQcFile();
        jFile.setFileName(file.getOriginalFilename());
        jFile.setFileType(file.getContentType());
        jFile.setJobCardQc(optionJc.get());
        jFile.setOwner(new EmployeeDetails(user.getUsername()));

        dbJoBCardFile.saveDetails (jFile,user);
        try {
            Path fillPath = Paths.get(fileLocation, "jcqc", "" + jFile.getId(), "jcqc_" + fileName);
            Path baseDir=Paths.get(fileLocation, "jcqc", "" + jFile.getId());
            
            if(!baseDir.toFile().exists())
                baseDir.toFile().mkdirs();


            Files.copy(file.getInputStream(), fillPath);
            jFile.setFilePath(fillPath.toString());
            dbJoBCardFile.updateDetails(jFile,user);
            
        } catch (IOException e) {
            LOGGER.error("error in image upload {}", e);
            e.printStackTrace();
            throw new BadRequestException("Unable to save file " + e.getMessage());
        }

    }

    @Override
    public Map<String, Object> getFilesByJobCardId(Long id, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Map<String, Object> details = dbJoBCardFile.getByJobCardQcId(id);
        Map<String,Object> changeDetails=new HashMap<>(details);
        changeDetails.put("file_path", "");
        // if (details != null && details.size() > 0) {
        //     for (Map<String, Object> obj : details) {

        //         Map<String,Object> _backMap=new HashMap<>(obj);

        //         FileInputStream inPut;
        //         try {
        //             inPut = new FileInputStream(obj.get("file_path").toString());
        //             try {
        //                 _backMap.put("file_path",
        //                         org.apache.commons.net.util.Base64.encodeBase64String(org.apache.commons.io.IOUtils.toByteArray(inPut),false));
        //             } catch (IOException e) {
        //                 LOGGER.error("error in image upload {}", e);
        //                 e.printStackTrace();
        //                 throw new BadRequestException("Unable to convert file to base 64");
        //             }
        //         } catch (FileNotFoundException e) {
        //             LOGGER.error("error in image upload {}", e);
        //             e.printStackTrace();
        //             throw new BadRequestException("Unable to load file, File not found");
        //         }
        //         changeDetails.add(_backMap);
        //     }
        // }
        return changeDetails;
    }

    @Override
    public void downLoadFile(Long id, HttpServletResponse response, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Optional<BtJobCardQcFile> optionalJobCard=dbJoBCardFile.findById(id);
        if(!optionalJobCard.isPresent())
                throw new BadRequestException("File not found");
        BtJobCardQcFile fl=optionalJobCard.get();
        
        try {
            FileInputStream fIn=new FileInputStream(Paths.get(fl.getFilePath()).toFile());
            response.setContentType(fl.getFileType());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fl.getFileName());
            IOUtils.copy(fIn, response.getOutputStream());   
            fIn.close(); 
            response.getOutputStream().close();
        } catch (Exception e) {
            throw new BadRequestException("Unable to read file");
        }
        
    }

}
