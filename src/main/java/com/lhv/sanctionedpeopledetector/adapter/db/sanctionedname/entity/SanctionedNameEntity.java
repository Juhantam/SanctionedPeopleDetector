package com.lhv.sanctionedpeopledetector.adapter.db.sanctionedname.entity;

import com.lhv.sanctionedpeopledetector.core.sanctionedname.model.SanctionedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "sanctioned_name")
public class SanctionedNameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "normalized_name", nullable = false)
    private String normalizedName;
    @Column(name = "phonetic_key", nullable = false)
    private String phoneticKey;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static SanctionedNameEntity of(SanctionedName sanctionedName) {
        return SanctionedNameEntity.builder()
                                   .id(sanctionedName.getId())
                                   .fullName(sanctionedName.getFullName())
                                   .normalizedName(sanctionedName.getNormalizedName())
                                   .phoneticKey(sanctionedName.getPhoneticKey())
                                   .createdAt(sanctionedName.getCreatedAt())
                                   .build();
    }

    public SanctionedName toDomain() {
        return SanctionedName.builder()
                             .id(getId())
                             .fullName(getFullName())
                             .normalizedName(getNormalizedName())
                             .phoneticKey(getPhoneticKey())
                             .createdAt(getCreatedAt())
                             .build();
    }

}
