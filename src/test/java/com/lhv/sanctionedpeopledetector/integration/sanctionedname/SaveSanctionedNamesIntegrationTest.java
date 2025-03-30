package com.lhv.sanctionedpeopledetector.integration.sanctionedname;


import com.fasterxml.jackson.core.type.TypeReference;
import com.lhv.sanctionedpeopledetector.config.integration.BaseIntegrationTest;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindSanctionedNamesByNormalizedNamesPort;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.SaveSanctionedNamesPort;
import com.lhv.sanctionedpeopledetector.util.JsonTestUtils;
import com.lhv.sanctionedpeopledetector.util.SanctionedNameFixture;
import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.SanctionedNameDto;
import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.SaveSanctionedNamesRequestDto;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SaveSanctionedNamesIntegrationTest extends BaseIntegrationTest {
    private static final String URL_TEMPLATE = "/api/web/sanctioned-names/save";

    @Resource
    private SanctionedNameFixture sanctionedNameFixture;

    @Test
    void save_namesNotYetPresentInBase_savesSanctionedNames() throws Exception {
        Set<String> namesToSave = Set.of("PImM Pomm", "Simm Somm");
        SaveSanctionedNamesRequestDto requestDto = SaveSanctionedNamesRequestDto.builder()
                .names(namesToSave)
                .build();

        String actualResponse = mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Set<SanctionedNameDto> result = JsonTestUtils.getObjectFromJsonString(actualResponse, new TypeReference<>() {});

        assertThat(result).extracting(SanctionedNameDto::getNormalizedName).containsExactlyInAnyOrder("pimm pomm", "simm somm");
    }

    @Test
    void save_nameAlreadyPresentInBase_nameIsNotSavedToBase() throws Exception {
        sanctionedNameFixture.save(entity -> entity.normalizedName("test name"));
        Set<String> namesToSave = Set.of("TEsT NAMe");
        SaveSanctionedNamesRequestDto requestDto = SaveSanctionedNamesRequestDto.builder()
                .names(namesToSave)
                .build();

        String actualResponse = mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Set<SanctionedNameDto> result = JsonTestUtils.getObjectFromJsonString(actualResponse, new TypeReference<>() {});

        assertThat(result).isEmpty();
    }

}
