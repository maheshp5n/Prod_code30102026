package com.pratikshat.exicom.services;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FCMInitializer {

	@Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
	@Value("${app.firbase.dburl}")
	private String firbaseDbUrl;
	
    final static Logger logger = LogManager.getLogger(FCMInitializer.class);
    //new ClassPathResource(firebaseConfigPath).getInputStream()
    
    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(firebaseConfigPath)))
                    .setDatabaseUrl(firbaseDbUrl)
                    .build();
            
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
