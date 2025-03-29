package com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname;

import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.entity.SanctionedNameEntity;
import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.repository.SanctionedNameRepository;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.SaveSanctionedNamesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class SaveSanctionedNamesAdapter implements SaveSanctionedNamesPort {
    private final SanctionedNameRepository repository;

    @Override
    public Set<SanctionedName> execute(Request request) {
        return repository.saveAll(request.getSanctionedNames()
                                         .stream()
                                         .map(SanctionedNameEntity::of)
                                         .collect(Collectors.toList()))
                         .stream()
                         .map(SanctionedNameEntity::toDomain)
                         .collect(Collectors.toSet());
    }
}
