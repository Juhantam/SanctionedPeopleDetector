package com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname;

import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.repository.SanctionedNameRepository;
import com.lhv.sanctionedpeopledetector.core.sanctionedname.port.DeleteSanctionedNamesByIdsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class DeleteSanctionedNamesByIdsAdapter implements DeleteSanctionedNamesByIdsPort {
    private final SanctionedNameRepository repository;

    @Override
    public void execute(Request request) {
        repository.deleteAllById(request.getIds().stream().toList());
    }
}
