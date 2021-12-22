package it.unisalento.mylinkedin;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class MyLinkedinApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MyLinkedinApplication.class, args);
		FileInputStream serviceAccount = new FileInputStream("./mylinked-5bda1-firebase-adminsdk-8zex4-d92186d152.json");
    	FirebaseOptions options = FirebaseOptions.builder()
    			.setCredentials(GoogleCredentials.fromStream(serviceAccount))
    			.setProjectId("mylinked-5bda1")
    			.build();
    	FirebaseApp.initializeApp(options);
	}

}
