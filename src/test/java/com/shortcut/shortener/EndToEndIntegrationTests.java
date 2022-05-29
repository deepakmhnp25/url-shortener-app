package com.shortcut.shortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.shortcut.shortener.common.TestParent;
import com.shortcut.shortener.constants.EndpointConstants;
import com.shortcut.shortener.domains.UrlDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration test class for the application endpoints
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShortenerAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EndToEndIntegrationTests extends TestParent {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    /**
     * End to End integration test for url shortening endpoint
     *
     * @throws Exception
     */
    @WithMockUser("testuser")
    @Test
    void shortenUrl() throws Exception {
        String requestJson = initShortenerRequest();
        mockMvc.perform(post(EndpointConstants.SHORTEN)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test end to end workflow to redirect to original url
     * from shortened url
     *
     * @throws Exception
     */
    @Test
    void getOriginalUrl() throws Exception {
        initializeFirebase();
        // checking if the shortened url getting redirected to original url
        mockMvc.perform(get("/3d0c7328"))
                .andExpect(status().is3xxRedirection());
    }

    /**
     * End to end integration Test
     * to load the page for registering the user
     *
     * @throws Exception
     */
    @Test
    void registerUserPageLoad() throws Exception {
        initializeFirebase();
        // checking if the page for registration is loading or not
        mockMvc.perform(get(EndpointConstants.HOME))
                .andExpect(status().isOk());
    }



    @Test
    void registerUser() throws Exception {
        String userDetails = objectMapper.writeValueAsString(getUserDetails());
        initializeFirebase();
        mockMvc.perform(post(EndpointConstants.REGISTER)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(userDetails).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Method to initialize request to url shortening end point
     *
     * @return request json
     * @throws IOException
     */
    private String initShortenerRequest() throws IOException {
        UrlDetails urlDetails = getUrlDetails();
        // set shortened url to null in test data.
        urlDetails.setShortenedUrl(null);
        String requestJson = objectMapper.writeValueAsString(urlDetails);
        initializeFirebase();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        return requestJson;
    }

    /**
     * Initializes firebase connection if not already connected.
     *
     * @throws IOException
     */
    private void initializeFirebase() throws IOException {
        // initialize firebase only if it not already connected.
        if (CollectionUtils.isEmpty(FirebaseApp.getApps())) {
            ClassPathResource classPathResource = new ClassPathResource("serviceAccountKey.json");
            InputStream inputStream = classPathResource.getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }

}
