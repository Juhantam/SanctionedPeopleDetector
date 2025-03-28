package com.lhv.sanctionedpeopledetector.core.sanctionedname.port;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;

import java.util.Set;

@FunctionalInterface
public interface FindAllSanctionedNamesPort {
    Set<SanctionedName> execute();
}
