package com.sku.loom.domain.channel.repository.section;

import com.sku.loom.domain.channel.entity.section.Sections;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionJpaRepository extends JpaRepository<Sections, Long> {
}