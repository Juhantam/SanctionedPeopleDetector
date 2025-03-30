package com.lhv.sanctionedpeopledetector.config.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.lhv.sanctionedpeopledetector.SanctionedPeopleDetectorApplication;
import com.lhv.sanctionedpeopledetector.config.ClockTestConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.time.Clock;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import({ClockTestConfiguration.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SanctionedPeopleDetectorApplication.class)
@Transactional
public class BaseIntegrationTest {

    @RegisterExtension
    protected static final WireMockExtension WIRE_MOCK_EXTENSION = WireMockExtension.newInstance()
            .options(wireMockConfig().port(9876))
            .configureStaticDsl(true)
            .build();
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected Clock clock;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected void commit() {
        TransactionSynchronizationManager.getSynchronizations()
                .forEach(TransactionSynchronization::afterCommit);
    }

    protected String performMvcGetRequestWithUriVariables(HttpHeaders httpHeaders, String baseUrl,
                                                          Object... uriVariables)
            throws Exception {
        return mockMvc.perform(get(baseUrl, uriVariables).headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcGetRequestWithQueryParams(HttpHeaders httpHeaders, String baseUrl,
                                                         MultiValueMap<String, String> queryParams)
            throws Exception {
        return mockMvc.perform(get(baseUrl).queryParams(queryParams)
                        .headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcPostRequestWithUriVariablesAndContentAsJsonString(HttpHeaders httpHeaders,
                                                                                 String baseUrl,
                                                                                 String contentAsJsonString,
                                                                                 Object... uriVariables)
            throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl, uriVariables)
                        .headers(httpHeaders)
                        .content(contentAsJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcPostRequestWithContentAsJsonString(HttpHeaders httpHeaders, String baseUrl,
                                                                  String contentAsJsonString)
            throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .headers(httpHeaders)
                        .content(contentAsJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcPostRequestWithContentAsJsonString(String baseUrl, String contentAsJsonString)
            throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .content(contentAsJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcPutRequestWithContentAsJsonString(HttpHeaders httpHeaders, String baseUrl,
                                                                 String contentAsJsonString)
            throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(baseUrl)
                        .headers(httpHeaders)
                        .content(contentAsJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcPostRequestWithUriVariables(HttpHeaders httpHeaders, String baseUrl,
                                                           String... uriVariables)
            throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl, (Object[]) uriVariables)
                        .headers(httpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcPostRequestWithUriVariables(String baseUrl, String... uriVariables) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl, (Object[]) uriVariables)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected String performMvcPutRequestWithUriVariablesAndContentAsJsonString(HttpHeaders httpHeaders,
                                                                                String baseUrl,
                                                                                String contentAsJsonString,
                                                                                String... uriVariables)
            throws Exception {
        return mockMvc.perform(put(baseUrl, (Object[]) uriVariables).headers(httpHeaders)
                        .content(contentAsJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    protected String performMvcGetRequestWithUriVariablesAndCustomResultExpectation(String baseUrl,
                                                                                    ResultMatcher customMatcher,
                                                                                    String... uriVariables)
            throws Exception {
        return mockMvc.perform(get(baseUrl, (Object[]) uriVariables).accept(MediaType.APPLICATION_JSON))
                .andExpect(customMatcher)
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    protected void performMvcDeleteRequestWithUriVariables(HttpHeaders httpHeaders, String baseUrl,
                                                           String... uriVariables)
            throws Exception {
        mockMvc.perform(delete(baseUrl, (Object[]) uriVariables).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    protected ResultActions performMvcCustomRequestWithQueryParams(String url, HttpMethod httpMethod,
                                                                   String... queryParams)
            throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.request(httpMethod, url, (Object[]) queryParams));
    }

    protected ResultActions performMvcCustomRequestWithoutQueryParams(String url, HttpMethod httpMethod)
            throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.request(httpMethod, url));
    }

}
