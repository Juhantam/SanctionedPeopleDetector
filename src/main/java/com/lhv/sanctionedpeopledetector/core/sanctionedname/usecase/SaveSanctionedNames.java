package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.SaveSanctionedNamesPort;
import com.lhv.sanctionedpeopledetector.spi.UseCase;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class SaveSanctionedNames {
    private final PreProcessNames preProcessNames;
    private final SaveSanctionedNamesPort saveSanctionedNamesPort;

    public Set<SanctionedName> execute(Request request) {
        if (request == null || request.getSanctionedNames() == null) {
            return null;
        }

        Set<String> preprocessedNames = preProcessNames.execute(PreProcessNames.Request.builder()
                                                                               .names(request.getSanctionedNames())
                                                                               .build());
        return saveSanctionedNamesPort.execute(SaveSanctionedNamesPort.Request.of(Set.of()));
    }

    @Value
    @Builder
    public static class Request {
        Set<String> sanctionedNames;
    }
}
