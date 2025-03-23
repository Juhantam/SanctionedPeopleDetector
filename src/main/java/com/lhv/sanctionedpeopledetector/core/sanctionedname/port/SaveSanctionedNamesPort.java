package com.lhv.sanctionedpeopledetector.core.sanctionedname.port;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import lombok.Value;

import java.util.Set;

@FunctionalInterface
public interface SaveSanctionedNamesPort {
    Set<SanctionedName> execute(Request request);

    @Value(staticConstructor = "of")
    class Request {
        Set<SanctionedName> sanctionedNames;
    }
}
