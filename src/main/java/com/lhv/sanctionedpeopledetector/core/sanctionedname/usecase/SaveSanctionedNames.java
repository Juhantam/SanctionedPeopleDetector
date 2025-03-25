package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindSanctionedNamesByNormalizedNamesPort;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.SaveSanctionedNamesPort;
import com.lhv.sanctionedpeopledetector.spi.UseCase;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.codec.language.Soundex;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class SaveSanctionedNames {
    private final PreProcessNames preProcessNames;
    private final SaveSanctionedNamesPort saveSanctionedNamesPort;
    private final FindSanctionedNamesByNormalizedNamesPort findSanctionedNamesByNormalizedNamesPort;
    private final Soundex soundex = new Soundex();

    public Set<SanctionedName> execute(Request request) {
        if (request == null || request.getNames() == null) {
            return null;
        }

        Map<String, String> normalizedNamesByFullNames = preProcessNames.execute(PreProcessNames.Request.builder()
                .names(request.getNames())
                .build());
        Set<SanctionedName> knownSanctionedNames = findSanctionedNamesByNormalizedNamesPort
                .execute(FindSanctionedNamesByNormalizedNamesPort.Request.of(new HashSet<>(normalizedNamesByFullNames.values())));
        Set<String> knownNormalizedNames = knownSanctionedNames.stream()
                .map(SanctionedName::getNormalizedName)
                .collect(Collectors.toSet());

        normalizedNamesByFullNames.entrySet().removeIf(entry -> knownNormalizedNames.contains(entry.getValue()));


        return saveSanctionedNamesPort.execute(SaveSanctionedNamesPort.Request.of(normalizedNamesByFullNames.entrySet()
                        .stream()
                .map(entry -> this.composeSanctionedName(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet())));
    }

    private SanctionedName composeSanctionedName(String fullName, String normalizedName) {
        return SanctionedName.builder()
                .fullName(fullName)
                .normalizedName(normalizedName)
                .phoneticKey(soundex.encode(normalizedName))
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Value
    @Builder
    public static class Request {
        Set<String> names;
    }
}
