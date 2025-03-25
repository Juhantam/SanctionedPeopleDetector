package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
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
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "godric gryffindor"));
    }

    @Test
    void execute_nameHasPrefixMrs_returnsPreProcessedName() {
        String name = "Mrs. Hermione Granger";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "hermione granger"));
    }


    @Test
    void execute_nameHasPrefixDr_returnsPreProcessedName() {
        String name = "Dr. John Watson";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "john watson"));
    }

    @Test
    void execute_nameHasPrefixSir_returnsPreProcessedName() {
        String name = "Sir Arthur Conan Doyle";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "arthur conan doyle"));
    }

    @Test
    void execute_nameHasPrefixLord_returnsPreProcessedName() {
        String name = "Lord Voldemort";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "voldemort"));
    }

    @Test
    void execute_nameHasPrefixCountess_returnsPreProcessedName() {
        String name = "Countess Elizabeth Bennet";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "elizabeth bennet"));
    }

    @Test
    void execute_nameHasNoPrefix_returnsOriginalName() {
        String name = "Sherlock Holmes";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "sherlock holmes"));
    }

    @Test
    void execute_nameHasMultiplePrefixes_returnsPreProcessedName() {
        String name = "Dr. Sir John Smith Jr.";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "john smith"));
    }

    @Test
    void execute_nameHasPrefixAnd_returnsPreProcessedName() {
        String name = "Charles and Diana";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "charles  diana"));
    }

    @Test
    void execute_nameHasPrefixTo_returnsPreProcessedName() {
        String name = "To Kill";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "kill"));
    }

    @Test
    void execute_nameHasPrefixII_returnsPreProcessedName() {
        String name = "Henry VIII II";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "henry viii"));
    }

    @Test
    void execute_nameHasPrefixEsq_returnsPreProcessedName() {
        String name = "John Doe Esq.";
        Map<String, String> result = useCase.execute(PreProcessNames.Request.builder()
                                                                    .names(Set.of(name))
                                                                    .build());

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(name, "john doe"));
    }
}