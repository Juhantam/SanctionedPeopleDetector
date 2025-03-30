package com.lhv.sanctionedpeopledetector.integration.sanctionedname;


import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.entity.SanctionedNameEntity;
import com.lhv.sanctionedpeopledetector.config.integration.BaseIntegrationTest;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindSanctionedNamesByNormalizedNamesPort;
import com.lhv.sanctionedpeopledetector.util.SanctionedNameFixture;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DeleteSanctionedNamesByIdsIntegrationTest extends BaseIntegrationTest {
    private static final String URL_TEMPLATE = "/api/web/sanctioned-names/delete?ids={ids}";

    @Resource
    private SanctionedNameFixture sanctionedNameFixture;
    @Resource
    private FindSanctionedNamesByNormalizedNamesPort findSanctionedNamesByNormalizedNamesPort;

    @Test
    void delete_withExistingSanctionedNames_deletesSanctionedNames() throws Exception {
        SanctionedNameEntity sanctionedNameEntity1 = sanctionedNameFixture.save(entity -> entity.normalizedName("test name1"));
        SanctionedNameEntity sanctionedNameEntity2 = sanctionedNameFixture.save(entity -> entity.normalizedName("test name2"));

        mockMvc.perform(delete(URL_TEMPLATE,
                        sanctionedNameEntity1.getId() + "," + sanctionedNameEntity2.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(findSanctionedNamesByNormalizedNamesPort.execute(
                FindSanctionedNamesByNormalizedNamesPort.Request.of(Set.of(sanctionedNameEntity1.getNormalizedName(), sanctionedNameEntity2.getNormalizedName()))
        )).isEmpty();
    }

    @Test
    void delete_withExistingSanctionedNamesButRequestIdNotMatching_noSanctionedNamesDeleted() throws Exception {
        SanctionedNameEntity sanctionedNameEntity1 = sanctionedNameFixture.save(entity -> entity.normalizedName("test name1"));
        SanctionedNameEntity sanctionedNameEntity2 = sanctionedNameFixture.save(entity -> entity.normalizedName("test name2"));

        mockMvc.perform(delete(URL_TEMPLATE, -1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(findSanctionedNamesByNormalizedNamesPort.execute(
                FindSanctionedNamesByNormalizedNamesPort.Request.of(Set.of(sanctionedNameEntity1.getNormalizedName(), sanctionedNameEntity2.getNormalizedName()))
        )).containsExactlyInAnyOrder(sanctionedNameEntity1.toDomain(), sanctionedNameEntity2.toDomain());
    }
}
