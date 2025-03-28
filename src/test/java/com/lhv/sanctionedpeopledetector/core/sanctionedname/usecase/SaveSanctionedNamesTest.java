package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindSanctionedNamesByNormalizedNamesPort;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.SaveSanctionedNamesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class SaveSanctionedNamesTest {
    private static final Long SANCTIONED_NAME_ID = 69L;
    private static final String NAME = "Pimm Pomm";
    private static final String NORMALIZED_NAME = "pimm pomm";
    private static final String NAME_PHONETIC_KEY = "P515";

    @Spy
    private PreProcessNames preProcessNames;
    @Mock
    private SaveSanctionedNamesPort saveSanctionedNamesPort;
    @Mock
    private FindSanctionedNamesByNormalizedNamesPort findSanctionedNamesByNormalizedNamesPort;

    private SaveSanctionedNames useCase;

    @BeforeEach
    void setUp() {
        useCase =
                new SaveSanctionedNames(preProcessNames, saveSanctionedNamesPort,
                                        findSanctionedNamesByNormalizedNamesPort);
    }

    @Test
    void execute_nameDoesNotYetExistInTheBase_savesSanctionedNameAndReturnsIt() {
        doReturn(Set.of()).when(findSanctionedNamesByNormalizedNamesPort)
                          .execute(FindSanctionedNamesByNormalizedNamesPort.Request.of(Set.of(NORMALIZED_NAME)));
        LocalDateTime createdAt = LocalDateTime.now();
        try (MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                        .thenReturn(createdAt);
            SanctionedName sanctionedNameToBeSaved = SanctionedName.builder()
                                                                   .fullName(NAME)
                                                                   .normalizedName(NORMALIZED_NAME)
                                                                   .phoneticKey(NAME_PHONETIC_KEY)
                                                                   .createdAt(createdAt)
                                                                   .build();
            SanctionedName savedSanctionedName = sanctionedNameToBeSaved.toBuilder()
                                                                        .id(SANCTIONED_NAME_ID)
                                                                        .build();
            doReturn(Set.of(savedSanctionedName)).when(saveSanctionedNamesPort)
                                                 .execute(SaveSanctionedNamesPort.Request.of(Set.of(sanctionedNameToBeSaved)));

            Set<SanctionedName> result = useCase.execute(SaveSanctionedNames.Request.builder()
                                                                                    .names(Set.of(NAME))
                                                                                    .build());


            assertThat(result).containsExactlyInAnyOrder(savedSanctionedName);
        }
    }

    @Test
    void execute_nameAlreadyExistsInTheBase_returnsEmptySet() {
        SanctionedName sanctionedName = SanctionedName.builder()
                                                      .id(SANCTIONED_NAME_ID)
                                                      .fullName(NAME)
                                                      .normalizedName(NORMALIZED_NAME)
                                                      .phoneticKey(NAME_PHONETIC_KEY)
                                                      .createdAt(LocalDateTime.now())
                                                      .build();
        doReturn(Set.of(sanctionedName)).when(findSanctionedNamesByNormalizedNamesPort)
                                        .execute(FindSanctionedNamesByNormalizedNamesPort.Request.of(Set.of(NORMALIZED_NAME)));
        doReturn(Set.of()).when(saveSanctionedNamesPort)
                          .execute(SaveSanctionedNamesPort.Request.of(Set.of()));

        Set<SanctionedName> result = useCase.execute(SaveSanctionedNames.Request.builder()
                                                                                .names(Set.of(NAME))
                                                                                .build());

        assertThat(result).isEmpty();
    }

}