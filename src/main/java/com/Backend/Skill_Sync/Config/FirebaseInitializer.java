package com.Backend.Skill_Sync.Config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FirebaseInitializer {

    @PostConstruct
    public void initialize() {
        try {
            // Loads the custom-named Firebase credentials file from src/main/resources.
            InputStream serviceAccount = this.getClass().getResourceAsStream("/skill-sync-e74c3-firebase-adminsdk-fbsvc-57034ab85c.json");
            if (serviceAccount == null) {
                throw new IOException("Firebase service account file not found in resources.");
            }


            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase has been initialized");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing Firebase", e);
        }
    }
}
