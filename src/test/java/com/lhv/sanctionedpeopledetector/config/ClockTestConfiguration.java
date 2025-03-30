package com.lhv.sanctionedpeopledetector.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class ClockTestConfiguration {

    @Bean
    @Primary
    Clock testClock() {
        return Clock.fixed(Instant.parse("2023-09-19T10:10:10Z"), ZoneId.systemDefault());
    }
}
