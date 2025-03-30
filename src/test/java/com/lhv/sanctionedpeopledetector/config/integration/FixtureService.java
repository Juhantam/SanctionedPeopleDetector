package com.lhv.sanctionedpeopledetector.config.integration;

import java.util.function.UnaryOperator;

public interface FixtureService<E, B> {

    B defaultEntityBuilder();

    E save();

    E save(UnaryOperator<B> operator);
}
