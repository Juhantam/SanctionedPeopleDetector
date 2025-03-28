package com.lhv.sanctionedpeopledetector.core.sanctionedname.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NameSuspicionLevel {
    String checkedName;
    Double suspicionLevel;
    String closestSuspiciousName;
}
