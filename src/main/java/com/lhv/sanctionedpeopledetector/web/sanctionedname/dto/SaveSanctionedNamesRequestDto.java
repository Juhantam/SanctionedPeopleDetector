package com.lhv.sanctionedpeopledetector.web.sanctionedname.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class SaveSanctionedNamesRequestDto {
    Set<String> names;
}
