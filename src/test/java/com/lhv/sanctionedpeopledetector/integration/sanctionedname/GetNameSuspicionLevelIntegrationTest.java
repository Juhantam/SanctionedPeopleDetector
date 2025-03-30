package com.lhv.sanctionedpeopledetector.integration.sanctionedname;


import com.lhv.sanctionedpeopledetector.config.integration.BaseIntegrationTest;
import com.lhv.sanctionedpeopledetector.util.JsonTestUtils;
import com.lhv.sanctionedpeopledetector.util.SanctionedNameFixture;
import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.NameSuspicionLevelDto;
import jakarta.annotation.Resource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GetNameSuspicionLevelIntegrationTest extends BaseIntegrationTest {
    private static final String URL_TEMPLATE = "/api/web/sanctioned-names/name-suspicion-level?name={name}";

    @Resource
    private SanctionedNameFixture sanctionedNameFixture;

    @ParameterizedTest
    @CsvSource(value = {
            "lord osama bin muhaMMad BIN awad bin ladin",
            "Osama Laden",
            "Laden Bin",
            "Osama Muhammad"
    }, nullValues = "null")
    void getSuspicionLevel_inputNameIsSuspicious_returnsSuspicionLevelAbove50Percent(String inputName) throws Exception {
        sanctionedNameFixture.save();

        String actualResponse = mockMvc.perform(get(URL_TEMPLATE, inputName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        NameSuspicionLevelDto result = JsonTestUtils.getObjectFromJsonString(actualResponse, NameSuspicionLevelDto.class);

        assertThat(result.getSuspicionLevel()).isGreaterThan(50);
    }


    @ParameterizedTest
    @CsvSource(value = {
            "Peeter Majakovski",
            "Vladislav Tšernenko",
            "Heli Lääts",
            "George Bush Nixon Obama"
    }, nullValues = "null")
    void getSuspicionLevel_inputNameIsNotSuspicious_returnsSuspicionLevelBelow50Percent(String inputName) throws Exception {
        sanctionedNameFixture.save();

        String actualResponse = mockMvc.perform(get(URL_TEMPLATE, inputName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        NameSuspicionLevelDto result = JsonTestUtils.getObjectFromJsonString(actualResponse, NameSuspicionLevelDto.class);

        assertThat(result.getSuspicionLevel()).isLessThan(50);
    }
}
