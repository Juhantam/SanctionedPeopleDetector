package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.NameSuspicionLevel;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindAllSanctionedNamesPort;
import com.lhv.sanctionedpeopledetector.spi.UseCase;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class CalculateNameSuspicionLevel {
    private final PreProcessNames preProcessNames;
    private final FindAllSanctionedNamesPort findAllSanctionedNamesPort;
    private final Soundex soundex = new Soundex();
    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public NameSuspicionLevel execute(Request request) {
        if (request == null || request.getName() == null) {
            return null;
        }

        String normalizedName = preProcessNames.execute(PreProcessNames.Request.builder()
                        .names(Set.of(request.getName()))
                        .build())
                .values().stream()
                .findFirst()
                .orElseThrow();
        String phoneticKey = soundex.encode(normalizedName);
        Set<SanctionedName> sanctionedNames = findAllSanctionedNamesPort.execute();

        double highestSuspicion = 0.0;
        String closestSuspiciousName = null;

        for (SanctionedName sanctionedName : sanctionedNames) {
            double similarityScore = calculateSuspicionScore(
                    normalizedName,
                    sanctionedName.getNormalizedName(),
                    phoneticKey,
                    sanctionedName.getPhoneticKey()
            );

            if (similarityScore > highestSuspicion) {
                highestSuspicion = Math.min(similarityScore, 100.0);
                closestSuspiciousName = sanctionedName.getNormalizedName();
            }
        }

        return NameSuspicionLevel.builder()
                .checkedName(request.getName())
                .suspicionLevel(highestSuspicion)
                .closestSuspiciousName(closestSuspiciousName)
                .build();
    }

    private double calculateSuspicionScore(String inputName, String referenceNormalizedName, String inputPhoneticKey, String referencePhoneticKey) {
        String[] inputNames = inputName.split("\\s+");
        String[] referenceNames = referenceNormalizedName.split("\\s+");

        double maxNameMatch = 0.0;
        for (String name : inputNames) {
            for (String referenceName : referenceNames) {
                int distance = levenshteinDistance.apply(name, referenceName);
                double similarity = 1.0 - ((double) distance / Math.max(name.length(), referenceName.length()));
                if (name.equals(referenceName)) {
                    similarity = 1.0;
                }
                maxNameMatch = Math.max(maxNameMatch, similarity);
            }
        }

        double nameSimilarity = 1.0 - ((double) levenshteinDistance.apply(inputName, referenceNormalizedName) / Math.max(inputName.length(), referenceNormalizedName.length()));
        boolean phoneticMatch = inputPhoneticKey.equals(referencePhoneticKey);

        double nameMatchBoost = calculateNameMatchBoost(inputName, referenceNormalizedName);

        return (maxNameMatch * 20) + (nameSimilarity * 30) + (phoneticMatch ? 20 : 0) + nameMatchBoost;
    }

    private double calculateNameMatchBoost(String input, String reference) {
        String[] inputNames = input.split("\\s+");
        String[] referenceNames = reference.split("\\s+");
        double suspicionScore = 0.0;
        int matchedNames = 0;

        for (String inputName : inputNames) {
            boolean hasGoodMatch = false;
            boolean hasBestMatch = false;

            for (String refName : referenceNames) {
                int nameDistance = levenshteinDistance.apply(inputName, refName);

                if (nameDistance == 0) {
                    suspicionScore += 12;
                    hasBestMatch = true;
                } else if (!hasBestMatch && nameDistance == 1) {
                    suspicionScore += 11;
                    hasGoodMatch = true;
                } else if (!hasBestMatch && nameDistance == 2) {
                    suspicionScore += 10;
                    hasGoodMatch = true;
                } else if (!hasBestMatch && nameDistance == 3) {
                    suspicionScore += 5;
                    hasGoodMatch = true;
                } else if (!hasGoodMatch && !hasBestMatch && nameDistance >= 4) {
                    suspicionScore -= 2.5;
                }
            }

            if (hasGoodMatch || hasBestMatch) {
                matchedNames++;
            }
        }

        if (inputNames.length < referenceNames.length / 2) {
            suspicionScore += 5;
        }

        if (matchedNames >= inputNames.length - 1) {
            suspicionScore = Math.max(suspicionScore, 25);
        }

        return suspicionScore;
    }

    @Value
    @Builder
    public static class Request {
        String name;
    }
}

