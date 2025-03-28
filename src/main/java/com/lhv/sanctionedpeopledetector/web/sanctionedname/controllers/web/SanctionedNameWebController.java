package com.lhv.sanctionedpeopledetector.web.sanctionedname.controllers.web;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindSanctionedNamesByNormalizedNamesPort;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase.CalculateNameSuspicionLevel;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase.SaveSanctionedNames;
import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.NameSuspicionLevelDto;
import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.SanctionedNameDto;
import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.SaveSanctionedNamesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/web/sanctioned-names")
public class SanctionedNameWebController {
    private final CalculateNameSuspicionLevel calculateNameSuspicionLevel;
    private final SaveSanctionedNames saveSanctionedNames;
    private final FindSanctionedNamesByNormalizedNamesPort findSanctionedNamesByNormalizedNames;

    @GetMapping
    public Set<String> test() {
        return findSanctionedNamesByNormalizedNames.execute(FindSanctionedNamesByNormalizedNamesPort.Request.of(Set.of("osama bin laden")))
                                                   .stream()
                                                   .map(SanctionedName::getFullName)
                                                   .collect(Collectors.toSet());
    }

    @GetMapping("/name-suspicion-level")
    public NameSuspicionLevelDto getNameSuspicionLevel(@RequestParam String name) {
        return NameSuspicionLevelDto.of(calculateNameSuspicionLevel.execute(CalculateNameSuspicionLevel.Request.builder()
                                                                                                               .name(name)
                                                                                                               .build()));
    }

    @PostMapping("/save")
    public Set<SanctionedNameDto> save(@RequestBody SaveSanctionedNamesRequestDto request) {
        return saveSanctionedNames.execute(SaveSanctionedNames.Request.builder()
                                                                      .names(request.getNames())
                                                                      .build())
                                  .stream()
                                  .map(SanctionedNameDto::of)
                                  .collect(Collectors.toSet());
    }
}
