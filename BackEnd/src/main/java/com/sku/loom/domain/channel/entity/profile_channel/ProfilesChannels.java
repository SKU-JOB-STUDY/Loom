package com.sku.loom.domain.channel.entity.profile_channel;

import com.sku.loom.domain.channel.entity.channel.Channels;
import com.sku.loom.domain.channel.entity.profile_channel.role.ProfileChannelRole;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import com.sku.loom.domain.workspace.entity.workspace_profile.role.WorkSpaceProfileRoleConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles_channels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProfilesChannels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_channel_id", nullable = false)
    @Schema(description = "프로필-채널 아이디", example = "1")
    private long profileChannelId;

    @ManyToOne
    @JoinColumn(name = "workspace_profile_id", nullable = false)
    @Schema(description = "워크스페이스-프로필 아이디", example = "1")
    private WorkspaceProfiles workspaceProfile;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    @Schema(description = "채널 아이디", example = "1")
    private Channels channel;

    @Column(name = "profile_channel_role", nullable = false, columnDefinition = "TINYINT(1)")
    @Enumerated(EnumType.ORDINAL)
    @Convert(converter = WorkSpaceProfileRoleConverter.class)
    @Schema(description = "프로필-채널 역할", example = "0")
    private ProfileChannelRole profileChannelRole;
}
