package com.lhv.sanctionedpeopledetector.util;

import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.entity.SanctionedNameEntity;
import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.entity.SanctionedNameEntity.SanctionedNameEntityBuilder;
import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.repository.SanctionedNameRepository;
import com.lhv.sanctionedpeopledetector.config.integration.FixtureService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.UnaryOperator;

@Component
public class SanctionedNameFixture implements FixtureService<SanctionedNameEntity, SanctionedNameEntityBuilder> {
    private final SanctionedNameRepository repository;

    public SanctionedNameFixture(SanctionedNameRepository repository) {
        this.repository = repository;
    }

    @Override
    public SanctionedNameEntityBuilder defaultEntityBuilder() {
        return SanctionedNameEntity.builder()
                .fullName("Osama bin Muhammad bin Awad bin Ladin")
                .normalizedName("osama bin muhammad bin awad bin ladin")
                .phoneticKey("O251")
                .createdAt(LocalDateTime.now());
    }

    @Override
    public SanctionedNameEntity save() {
        return save(UnaryOperator.identity());
    }

    @Override
    public SanctionedNameEntity save(UnaryOperator<SanctionedNameEntityBuilder> operator) {
        SanctionedNameEntity entity = operator.apply(defaultEntityBuilder())
                .build();
        return repository.saveAndFlush(entity);
    }
}
