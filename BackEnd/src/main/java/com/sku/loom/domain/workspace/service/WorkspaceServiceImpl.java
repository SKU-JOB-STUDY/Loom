package com.sku.loom.domain.workspace.service;

import com.sku.loom.domain.channel.entity.channel.Channels;
import com.sku.loom.domain.channel.entity.profile_channel.ProfilesChannels;
import com.sku.loom.domain.channel.entity.profile_channel.role.ProfileChannelRole;
import com.sku.loom.domain.channel.repository.channel.ChannelJpaRepository;
import com.sku.loom.domain.channel.repository.profile_channel.ProfileChannelJpaRepository;
import com.sku.loom.domain.user.entity.Users;
import com.sku.loom.domain.user.repository.UserRepository;
import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import com.sku.loom.domain.workspace.entity.workspace.Workspaces;
import com.sku.loom.domain.workspace.entity.workspace_profile.role.WorkSpaceProfileRole;
import com.sku.loom.domain.workspace.repository.workspace_profile.WorkspaceProfileJpaRepository;
import com.sku.loom.domain.workspace.repository.workspace.WorkspaceJpaRepository;
import com.sku.loom.global.exception.user.NotFoundUserException;
import com.sku.loom.global.exception.workspace.NotFoundWorkspaceException;
import com.sku.loom.global.service.s3.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class WorkspaceServiceImpl implements WorkspaceService{

    private final UserRepository userRepository;
    private final WorkspaceJpaRepository workspaceJpaRepository;
    private final WorkspaceProfileJpaRepository workspaceProfileJpaRepository;
    private final ChannelJpaRepository channelJpaRepository;
    private final ProfileChannelJpaRepository profileChannelJpaRepository;
    private final S3Service s3Service;

    @Override
    public List<WorkspaceResponse> getWorkspaces(Long userId) {
        return workspaceProfileJpaRepository.findWorkspaceResponsesByUserId(userId);
    }

    @Override
    @Transactional
    public void postWorkspace(long userId, String workspaceName, MultipartFile image) throws IOException {
        // CREATE WORKSPACES
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String workspaceImg = "WORKSPACE BASIC S3 URL";

        if(image != null && !image.isEmpty())
            workspaceImg = s3Service.uploadS3(image, "/workspaces/img");

        Workspaces newWorkspace = Workspaces.builder()
                .workspaceName(workspaceName)
                .workspaceImg(workspaceImg)
                .workspaceCode(generateWorkspaceCode())
                .workspaceCreatedAt(now)
                .workspaceUpdatedAt(now)
                .build();

        workspaceJpaRepository.save(newWorkspace);

        // CREATE WORKSPACE-PROFILES
        String workspaceProfileImg = "WORKSPACE PROFILES BASIC S3 URL";
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundUserException());

        WorkspaceProfiles newWorkspaceProfile = WorkspaceProfiles.builder()
                .user(user)
                .workspace(newWorkspace)
                .workspaceProfileName(user.getUserEmail().split("@")[0])
                .workspaceProfileImg(workspaceProfileImg)
                .workSpaceProfileRole(WorkSpaceProfileRole.OWNER)
                .workspaceProfileCreatedAt(now)
                .workspaceProfileUpdatedAt(now)
                .build();

        workspaceProfileJpaRepository.save(newWorkspaceProfile);

        // CREATE CHANNEL
        Channels newBasicChannel = Channels.builder()
                .channelName(newWorkspace.getWorkspaceName() + "- 전체 채널")
                .channelOpened(true)
                .channelDefault(true)
                .channelCreatedAt(now)
                .channelUpdatedAt(now)
                .build();

        channelJpaRepository.save(newBasicChannel);

        // CREATE PROFILE_CHANNEL
        ProfilesChannels newProfileChannel = ProfilesChannels.builder()
                .workspaceProfile(newWorkspaceProfile)
                .channel(newBasicChannel)
                .profileChannelRole(ProfileChannelRole.OWNER)
                .build();

        profileChannelJpaRepository.save(newProfileChannel);
    }

    @Override
    @Transactional
    public void postWorkspaceJoin(long userId, String workspaceCode) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String workspaceProfileImg = "WORKSPACE PROFILES BASIC S3 URL";
        Users newUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundUserException());
        Workspaces workspace = workspaceJpaRepository.findByWorkspaceCode(workspaceCode)
                .orElseThrow(() -> new NotFoundWorkspaceException());

        WorkspaceProfiles newWorkspaceProfile = WorkspaceProfiles.builder()
                .user(newUser)
                .workspace(workspace)
                .workspaceProfileName(newUser.getUserEmail().split("@")[0])
                .workspaceProfileImg(workspaceProfileImg)
                .workSpaceProfileRole(WorkSpaceProfileRole.MEMBER)
                .workspaceProfileCreatedAt(now)
                .workspaceProfileUpdatedAt(now)
                .build();

        workspaceProfileJpaRepository.save(newWorkspaceProfile);
        
        // 프로필 채널 만들기 -> 기본 채널(querydsl) 찾아서 멤버 추가
    }

    private String generateWorkspaceCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new SecureRandom();
        String code;
        do {
            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            code = sb.toString();
        } while (workspaceJpaRepository.existsByWorkspaceCode(code));

        return code;
    }
}
