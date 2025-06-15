package com.sku.loom.domain.channel.repository.section;

import com.sku.loom.domain.channel.entity.section.Sections;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionJpaRepository extends JpaRepository<Sections, Long> {
    Optional<Sections> findBySectionId(Long sectionId);
}