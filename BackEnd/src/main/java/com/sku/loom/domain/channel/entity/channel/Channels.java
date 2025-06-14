package com.sku.loom.domain.channel.entity.channel;


import com.sku.loom.domain.channel.entity.section.Sections;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity
@Table(name = "channels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Channels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id", nullable = false)
    @Schema(description = "채널 아이디", example = "1")
    private long channelId;

    @ManyToOne
    @JoinColumn(name = "section_id")
    @Schema(description = "섹션 아이디", example = "1")
    private Sections section;

    @Column(name = "channel_name", nullable = false)
    @Schema(description = "채널 이름", example = "채널 이름 20자 이하")
    private String channelName;

    @Column(name = "channel_opened", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("true")
    @Schema(description = "채널 공개 여부", example = "1")
    private boolean channelOpened;

    @Column(name = "channel_default", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("false")
    @Schema(description = "채널 기본 여부", example = "0")
    private boolean channelDefault;

    @Column(name = "channel_created_at", nullable = false, updatable = false)
    @Schema(description = "채널 생성일자", example = "2025-06-09 00:00:00")
    private Timestamp channelCreatedAt;

    @Column(name = "channel_updated_at", nullable = false)
    @Schema(description = "채널 수정일자", example = "2025-06-09 00:00:00")
    private Timestamp channelUpdatedAt;

    @Override
    public String toString() {
        return "Channels{" +
                "channelId=" + channelId +
                ", section=" + section +
                ", channelName='" + channelName + '\'' +
                ", channelOpened=" + channelOpened +
                ", channelDefault=" + channelDefault +
                ", channelCreatedAt=" + channelCreatedAt +
                ", channelUpdatedAt=" + channelUpdatedAt +
                '}';
    }
}
