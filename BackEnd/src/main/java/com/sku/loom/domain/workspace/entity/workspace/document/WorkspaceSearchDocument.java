package com.sku.loom.domain.workspace.entity.workspace.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "workspace_search")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSearchDocument {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long workspaceId; // MySQL 조회용 ID

    @Field(type = FieldType.Keyword)
    private String workspaceCode; // 검색 대상
}
