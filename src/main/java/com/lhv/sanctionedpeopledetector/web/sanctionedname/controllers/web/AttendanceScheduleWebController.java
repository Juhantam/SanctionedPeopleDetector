package com.lhv.sanctionedpeopledetector.web.sanctionedname.controllers.web;

import com.lhv.sanctionedpeopledetector.web.sanctionedname.dto.SaveSanctionedNamesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/web/sanctioned-names")
public class AttendanceScheduleWebController {

    @GetMapping
    public String test() {
        return "Jee";
    }

    @PostMapping("/save")
    public void save(@RequestBody SaveSanctionedNamesRequestDto request) {

    }
}
