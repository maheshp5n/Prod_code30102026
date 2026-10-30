package com.pratikshat.exicom.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.exception.FileStorageException;
import com.pratikshat.exicom.exception.MyFileNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            if(Files.exists(this.fileStorageLocation.resolve(fileName))) {
            	Optional<String> fileNameWithoutExtension = getFileNameWithoutExtension(fileName);
            	Optional<String> fileExtension = getFileExtension(fileName);
            	if(fileNameWithoutExtension.isPresent() && fileExtension.isPresent()) {
            		
            		long timeMillisecond = new Date().getTime();
                	String newFileName = fileNameWithoutExtension.get()+"_"+timeMillisecond+"."+fileExtension.get();
                	fileName = StringUtils.cleanPath(newFileName);
            		
            	}else {
            		throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);	
            	}
            }
            
            
         // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            
            Files.copy(file.getInputStream(), targetLocation);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
    
    public Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
    
    public Optional<String> getFileNameWithoutExtension(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(0,filename.lastIndexOf(".")));
    }
}
