package com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.repository;

import com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.entity.SanctionedNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanctionedNameRepository extends JpaRepository<SanctionedNameEntity, Long> {

}
