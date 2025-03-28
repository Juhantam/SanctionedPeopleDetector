package com.lhv.sanctionedpeopledetector.core.sanctionedname.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class SanctionedName {
    Long id;
    String fullName;
    String normalizedName;
    String phoneticKey;
    LocalDateTime createdAt;
}
