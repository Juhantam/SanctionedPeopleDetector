package com.lhv.sanctionedpeopledetector.web.sanctionedname.controllers.web;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.FindSanctionedNamesByNormalizedNamesPort;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase.SaveSanctionedNames;
import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.SaveSanctionedNamesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/web/sanctioned-names")
public class AttendanceScheduleWebController {
    private final SaveSanctionedNames saveSanctionedNames;
    private final FindSanctionedNamesByNormalizedNamesPort findSanctionedNamesByNormalizedNames;

    @GetMapping
    public Set<String> test() {
        return findSanctionedNamesByNormalizedNames.execute(FindSanctionedNamesByNormalizedNamesPort.Request.of(Set.of("osama bin laden"))).stream()
                .map(SanctionedName::getFullName)
                .collect(Collectors.toSet());
    }

    @PostMapping("/save")
    public void save(@RequestBody SaveSanctionedNamesRequestDto request) {
        saveSanctionedNames.execute(SaveSanctionedNames.Request.builder()
                .names(request.getNames())
                .build());
    }
}
