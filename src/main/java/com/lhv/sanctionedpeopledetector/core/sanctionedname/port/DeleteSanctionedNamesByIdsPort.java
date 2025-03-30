package com.lhv.sanctionedpeopledetector.core.sanctionedname.port;

import lombok.Value;

import java.util.Set;

@FunctionalInterface
public interface DeleteSanctionedNamesByIdsPort {
    void execute(Request request);

    @Value(staticConstructor = "of")
    class Request {
        Set<Long> ids;
    }
}
