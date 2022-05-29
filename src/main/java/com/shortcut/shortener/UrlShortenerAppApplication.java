package com.shortcut.shortener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Starting point of the application.
 * This application has a user registration , login functionality.
 * once logged in you can shorten the url and use the shortened url anywhere
 * to load your orignal url
 * Database connection, and tomcat server startup initialized here.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@SpringBootApplication
public class UrlShortenerAppApplication {

	private static Logger logger = LoggerFactory.getLogger(UrlShortenerAppApplication.class);

	public static void main(String[] args) throws IOException {

		// Loading the serviceAccountKey for firestore db
		ClassPathResource classPathResource = new ClassPathResource("serviceAccountKey.json");
		InputStream inputStream = classPathResource.getInputStream();
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(inputStream))
				.build();
		FirebaseApp.initializeApp(options);
		logger.info("Connection to firestore db successful");
		// Application initialization
		SpringApplication.run(UrlShortenerAppApplication.class, args);
		logger.info("Application start up successful");
	}

}
