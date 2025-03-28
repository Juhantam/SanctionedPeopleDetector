package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.NameSuspicionLevel;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindAllSanctionedNamesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CalculateNameSuspicionLevelTest {
    @Spy
    private PreProcessNames preProcessNames;
    @Mock
    private FindAllSanctionedNamesPort findAllSanctionedNamesPort;

    private CalculateNameSuspicionLevel useCase;

    @BeforeEach
    void setUp() {
        useCase = new CalculateNameSuspicionLevel(preProcessNames, findAllSanctionedNamesPort);
    }

    @Test
    void execute_inputNameIsOsamaLaden_returnsSuspicionLevelAbove50Percent() {
        String name = "Osama Laden";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsOsamaBinLaden_returnsSuspicionLevelAbove50Percent() {
        String name = "Osama Bin Laden";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsBinLadenOsama_returnsSuspicionLevelAbove50Percent() {
        String name = "Bin Laden, Osama";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsLadenOsamaBin_returnsSuspicionLevelAbove50Percent() {
        String name = "Laden Osama Bin";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsToTheOsamaBinLaden_returnsSuspicionLevelAbove50Percent() {
        String name = "to the osama bin laden";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsOsamaAndBindLaden_returnsSuspicionLevelAbove50Percent() {
        String name = "osama and bin laden";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsBenOsamaLadn_returnsSuspicionLevelAbove50Percent() {
        String name = "Ben Osama Ladn";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsLadnTheAsoma_returnsSuspicionLevelAbove50Percent() {
        String name = "Ladn The Asoma";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isGreaterThan(50.0);
    }

    @Test
    void execute_inputNameIsPeeter_returnsSuspicionLevelBelow50Percent() {
        String name = "Peeter";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isLessThan(50.0);
    }

    @Test
    void execute_inputNameIsLatinSpeaker_returnsSuspicionLevelBelow50Percent() {
        String name = "Latin Speaker";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isLessThan(50.0);
    }

    @Test
    void execute_inputNameIsHospitalBeenLacking_returnsSuspicionLevelBelow50Percent() {
        String name = "Hospital Been lacking";
        doReturn(composeSanctionedNames()).when(findAllSanctionedNamesPort).execute();

        NameSuspicionLevel result = useCase.execute(CalculateNameSuspicionLevel.Request.builder()
                .name(name)
                .build());

        assertThat(result.getSuspicionLevel()).isLessThan(50.0);
    }

    private Set<SanctionedName> composeSanctionedNames() {
        return Set.of(SanctionedName.builder()
                .fullName("Osama Bin Laden")
                .normalizedName("osama bin laden")
                .phoneticKey("O251")
                .build());
    }
}