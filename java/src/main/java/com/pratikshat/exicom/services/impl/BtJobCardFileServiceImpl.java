package com.pratikshat.exicom.services.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtJobCardFile;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtJobCardFileRepository;
import com.pratikshat.exicom.repository.BtJobCardRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtJobCardFileService;

import org.apache.commons.io.IOUtils;
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

@Service
public class BtJobCardFileServiceImpl implements BtJobCardFileService {

    @Value("${bt.jc.filelocation}")
    String fileLocation;

    @Autowired
    BtJobCardFileRepository dbJoBCardFile;
    @Autowired
    BtJobCardRepository dbJc;
    @Autowired
    UserProfileRepository dbProfile;

    private static final Logger LOGGER = LoggerFactory.getLogger(BtJobCardFileServiceImpl.class);

    @Override
    @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, SQLException.class,
            RuntimeException.class,
            Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void uploadFile(Long id, MultipartFile file, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbProfile.hasObjectAccess(user.getUsername(), BtJobCardFile.class, UserProfileRepository.CREATE))
            throw new UnauthoriseException("You are not authorised");
        if (file.isEmpty())
            throw new BadRequestException("Invalid file");
        String fileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();

        if ((file.getSize() / 1024) > (1024 * 10))
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

        Optional<BtJobCard> optionJc = dbJc.findById(id);
        if (!optionJc.isPresent())
            throw new BadRequestException("Invalid job card id");
        BtJobCardFile jFile = new BtJobCardFile();
        jFile.setFileName(file.getOriginalFilename());
        jFile.setFileType(file.getContentType());
        jFile.setJobCard(optionJc.get());
        jFile.setOwner(new EmployeeDetails(user.getUsername()));

        dbJoBCardFile.saveDetails(jFile, user);
        try {
            Path fillPath = Paths.get(fileLocation, "jc", "" + jFile.getId(), "jc_" + fileName);
            Path baseDir = Paths.get(fileLocation, "jc", "" + jFile.getId());

            if (!baseDir.toFile().exists())
                baseDir.toFile().mkdirs();

            Files.copy(file.getInputStream(), fillPath);
            jFile.setFilePath(fillPath.toString());
            dbJoBCardFile.updateDetails(jFile, user);
        } catch (IOException e) {
            LOGGER.error("error in image upload {}", e);
            e.printStackTrace();
            throw new BadRequestException("Unable to save file " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getFilesByJobCardId(Long id, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        Map<String, Object> _details = dbJoBCardFile.getByJobCardId(id);
        Map<String, Object> details = new HashMap<>(_details);
        details.put("file_path", "");
        // List<Map<String,Object>> changeDetails=new ArrayList<Map<String,Object>>();
        // if (details != null && details.size() > 0) {
        // for (Map<String, Object> obj : details) {

        // Map<String,Object> _backMap=new HashMap<>(obj);

        // FileInputStream inPut;
        // try {
        // inPut = new FileInputStream(obj.get("file_path").toString());
        // try {
        // _backMap.put("file_path",
        // org.apache.commons.net.util.Base64.encodeBase64String(org.apache.commons.io.IOUtils.toByteArray(inPut),false));
        // } catch (IOException e) {
        // LOGGER.error("error in image upload {}", e);
        // e.printStackTrace();
        // throw new BadRequestException("Unable to convert file to base 64");
        // }
        // } catch (FileNotFoundException e) {
        // LOGGER.error("error in image upload {}", e);
        // e.printStackTrace();
        // throw new BadRequestException("Unable to load file, File not found");
        // }
        // changeDetails.add(_backMap);
        // }
        // }
        return details;
    }

    @Override
    public void downLoadFile(Long id, HttpServletResponse response, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Optional<BtJobCardFile> optFile = dbJoBCardFile.findById(id);
        if (!optFile.isPresent())
            throw new BadRequestException("File not found");
        FileInputStream inputStream;

        try {
            inputStream = new FileInputStream(Paths.get(optFile.get().getFilePath()).toFile());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + optFile.get().getFileName());
            response.setContentType(optFile.get().getFileType());
            IOUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();
            response.getOutputStream().close();

        } catch (IOException e) {
            throw new BadRequestException("File not found, Unable to open stream");
        }
    }

}