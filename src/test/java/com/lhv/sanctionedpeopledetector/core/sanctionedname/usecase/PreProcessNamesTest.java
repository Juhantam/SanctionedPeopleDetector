package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PreProcessNamesTest {
    private PreProcessNames useCase;

    @BeforeEach
    void setUp() {
        useCase = new PreProcessNames();
    }

    @Test
    void execute_nameHasThePrefix_returnsPreProcessedName() {
        String name = "The Godric Gryffindor";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("godric gryffindor");
    }

    @Test
    void execute_nameHasPrefixMrs_returnsPreProcessedName() {
        String name = "Mrs. Hermione Granger";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("hermione granger");
    }


    @Test
    void execute_nameHasPrefixDr_returnsPreProcessedName() {
        String name = "Dr. John Watson";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("john watson");
    }

    @Test
    void execute_nameHasPrefixSir_returnsPreProcessedName() {
        String name = "Sir Arthur Conan Doyle";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("arthur conan doyle");
    }

    @Test
    void execute_nameHasPrefixLord_returnsPreProcessedName() {
        String name = "Lord Voldemort";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("voldemort");
    }

    @Test
    void execute_nameHasPrefixCountess_returnsPreProcessedName() {
        String name = "Countess Elizabeth Bennet";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("elizabeth bennet");
    }

    @Test
    void execute_nameHasNoPrefix_returnsOriginalName() {
        String name = "Sherlock Holmes";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("sherlock holmes");
    }

    @Test
    void execute_nameHasMultiplePrefixes_returnsPreProcessedName() {
        String name = "Dr. Sir John Smith Jr.";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("john smith");
    }

    @Test
    void execute_nameHasPrefixAnd_returnsPreProcessedName() {
        String name = "Charles and Diana";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("charles  diana");
    }

    @Test
    void execute_nameHasPrefixTo_returnsPreProcessedName() {
        String name = "To Kill";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("kill");
    }

    @Test
    void execute_nameHasPrefixII_returnsPreProcessedName() {
        String name = "Henry VIII II";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("henry viii");
    }

    @Test
    void execute_nameHasPrefixEsq_returnsPreProcessedName() {
        String name = "John Doe Esq.";
        Set<String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrder("john doe");
    }
}