package com.cacaotalk.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cacaotalk.user.service", "com.cacaotalk.user.controller", "com.cacaotalk.user.security", "com.cacaotalk.user"})  // quét qua package com.shoppingcart.admin.* để
public class CacaoSocialMediaApplication {

	public static void main(String[] args) throws IOException {
        // Initialize Firebase credentials=
		
        if (FirebaseApp.getApps().isEmpty()) {
        	FileInputStream serviceAccount = new FileInputStream("/Users/tranhainam/Downloads/user/src/main/resources/serviceAccountKey.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }

        // Start the Spring Boot application
        SpringApplication.run(CacaoSocialMediaApplication.class, args);
    }
	
	
}
