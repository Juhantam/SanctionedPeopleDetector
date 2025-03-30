package com.lhv.sanctionedpeopledetector.web.sanctionedname.dto;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.NameSuspicionLevel;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class NameSuspicionLevelDto {
    String checkedName;
    Double suspicionLevel;
    String closestSuspiciousName;

    public static NameSuspicionLevelDto of(NameSuspicionLevel nameSuspicionLevel) {
        if (nameSuspicionLevel == null) {
            return null;
        }
        return NameSuspicionLevelDto.builder()
                .checkedName(nameSuspicionLevel.getCheckedName())
                .suspicionLevel(nameSuspicionLevel.getSuspicionLevel())
                .closestSuspiciousName(nameSuspicionLevel.getClosestSuspiciousName())
                .build();
    }
}
