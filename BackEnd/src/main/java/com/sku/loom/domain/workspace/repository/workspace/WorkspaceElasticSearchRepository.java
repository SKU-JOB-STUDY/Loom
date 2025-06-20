package com.sku.loom.domain.workspace.repository.workspace;

import com.sku.loom.domain.workspace.entity.workspace.document.WorkspaceSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface WorkspaceElasticSearchRepository extends ElasticsearchRepository<WorkspaceSearchDocument, String> {
    Optional<WorkspaceSearchDocument> findByWorkspaceCode(String workspaceCode);
}
