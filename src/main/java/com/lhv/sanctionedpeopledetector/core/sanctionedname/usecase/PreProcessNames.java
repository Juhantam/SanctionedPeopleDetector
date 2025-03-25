package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import com.lhv.sanctionedpeopledetector.spi.UseCase;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class PreProcessNames {
    private static final String PREFIX_REGEX =
            "\\b(the|to|an|a|and|or|but|nor|for|on|at|by|with|about|against|between|into|through" +
                    "|during|before|after|above|below|over|under|mrs|mr|miss|ms|dr|sir|madam|jr|sr|ii|iii" +
                    "|iv|v|vi|lord|lady|baron|baroness|count|countess|duke|duchess|earl|viscount" +
                    "|viscountess|marquis|marquess|prince|princess|king|queen|knight|esq|hon|rev|prof)\\b";

    public Map<String, String> execute(Request request) {
        if (request == null || request.getNames() == null) {
            return null;
        }

        return request.getNames()
                .stream()
                .collect(Collectors.toMap(Function.identity(), this::preprocessName));
    }

    private String preprocessName(String name) {
        return name.toLowerCase()
                .replaceAll(PREFIX_REGEX, "")
                .replaceAll("[^a-z ]", "")
                .trim();
    }

    @Value
    @Builder
    public static class Request {
        Set<String> names;
    }
}
