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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@ActiveProfiles("test")
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
}
