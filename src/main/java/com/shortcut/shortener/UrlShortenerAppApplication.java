package com.shortcut.shortener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class UrlShortenerAppApplication {

	public static void main(String[] args) throws IOException {

		ClassPathResource classPathResource = new ClassPathResource("serviceAccountKey.json");
		InputStream inputStream = classPathResource.getInputStream();
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(inputStream))
				.build();
		FirebaseApp.initializeApp(options);
		SpringApplication.run(UrlShortenerAppApplication.class, args);
	}

}
