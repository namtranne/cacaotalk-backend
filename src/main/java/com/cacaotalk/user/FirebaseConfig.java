package com.cacaotalk.user;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
@Configuration
public class FirebaseConfig {

    @Bean
    public Storage firebaseStorage() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream());
        StorageOptions options = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build();
        return options.getService();
    }
}