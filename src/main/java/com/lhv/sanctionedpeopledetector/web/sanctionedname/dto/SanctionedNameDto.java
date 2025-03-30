package com.lhv.sanctionedpeopledetector.web.sanctionedname.dto;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class SanctionedNameDto {
    Long id;
    String fullName;
    String normalizedName;
    String phoneticKey;
    LocalDateTime createdAt;

    public static SanctionedNameDto of(SanctionedName sanctionedName) {
        if (sanctionedName == null) {
            return null;
        }
        return SanctionedNameDto.builder()
                                .id(sanctionedName.getId())
                                .fullName(sanctionedName.getFullName())
                                .normalizedName(sanctionedName.getNormalizedName())
                                .phoneticKey(sanctionedName.getPhoneticKey())
                                .createdAt(sanctionedName.getCreatedAt())
                                .build();
    }
}
