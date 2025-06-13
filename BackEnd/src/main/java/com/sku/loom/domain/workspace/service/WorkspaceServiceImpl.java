package com.sku.loom.domain.workspace.service;

import com.sku.loom.domain.channel.entity.channel.Channels;
import com.sku.loom.domain.channel.entity.profile_channel.ProfilesChannels;
import com.sku.loom.domain.channel.entity.profile_channel.role.ProfileChannelRole;
import com.sku.loom.domain.channel.repository.channel.ChannelCustomRepository;
import com.sku.loom.domain.channel.repository.channel.ChannelJpaRepository;
import com.sku.loom.domain.channel.repository.profile_channel.ProfileChannelJpaRepository;
import com.sku.loom.domain.user.entity.Users;
import com.sku.loom.domain.user.repository.UserRepository;
import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import com.sku.loom.domain.workspace.entity.workspace.Workspaces;
import com.sku.loom.domain.workspace.entity.workspace_profile.role.WorkSpaceProfileRole;
import com.sku.loom.domain.workspace.repository.workspace_profile.WorkspaceProfileCustomRepository;
import com.sku.loom.domain.workspace.repository.workspace_profile.WorkspaceProfileJpaRepository;
import com.sku.loom.domain.workspace.repository.workspace.WorkspaceJpaRepository;
import com.sku.loom.global.exception.user.NotFoundUserException;
import com.sku.loom.global.exception.workspace.AlreadyExistsWorkspaceUserException;
import com.sku.loom.global.exception.workspace.NotFoundWorkspaceException;
import com.sku.loom.global.exception.workspace.WorkspaceOwnerRequiredException;
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
    private final WorkspaceProfileCustomRepository workspaceProfileCustomRepository;
    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelCustomRepository channelCustomRepository;
    private final ProfileChannelJpaRepository profileChannelJpaRepository;
    private final S3Service s3Service;

    @Override
    public List<WorkspaceResponse> getWorkspaces(Long userId) {
        return workspaceProfileCustomRepository.findWorkspaceResponsesByUserId(userId);
    }

    @Override
    @Transactional
    public void postWorkspace(long userId, String workspaceName, MultipartFile image) throws IOException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundUserException());

        // CREATE WORKSPACES
        String workspaceImg = "WORKSPACE BASIC S3 URL";

//        if(image != null && !image.isEmpty())
//            workspaceImg = s3Service.uploadS3(image, "/workspaces/img");

        Workspaces newWorkspace = Workspaces.builder()
                .workspaceName(workspaceName)
                .workspaceImg(workspaceImg)
                .workspaceCode(generateWorkspaceCode())
                .workspaceCreatedAt(now)
                .workspaceUpdatedAt(now)
                .build();

        workspaceJpaRepository.save(newWorkspace);

        // CREATE WORKSPACE-PROFILES
        WorkspaceProfiles newWorkspaceProfile = createWorkspaceProfile(user, newWorkspace, now, WorkSpaceProfileRole.OWNER);

        // CREATE CHANNEL
        Channels newBasicChannel = createChannel(newWorkspace, now);

        // CREATE PROFILE_CHANNEL
        createProfileChannel(newWorkspaceProfile, newBasicChannel, ProfileChannelRole.OWNER);
    }

    @Override
    @Transactional
    public void postWorkspaceJoin(long userId, String workspaceCode) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Users newUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundUserException());
        Workspaces targetWorkspace = workspaceJpaRepository.findByWorkspaceCode(workspaceCode)
                .orElseThrow(() -> new NotFoundWorkspaceException());
        Channels basicChannel = channelCustomRepository.findBasicChannelsByWorkspaceCode(workspaceCode);

        if(chkExists(newUser, targetWorkspace))
            throw new AlreadyExistsWorkspaceUserException();

        // CREATE WORKSPACE-PROFILES
        WorkspaceProfiles newWorkspaceProfile = createWorkspaceProfile(newUser, targetWorkspace, now, WorkSpaceProfileRole.MEMBER);

        // CREATE PROFILE_CHANNEL
        createProfileChannel(newWorkspaceProfile, basicChannel, ProfileChannelRole.MEMBER);
    }

    @Override
    @Transactional
    public void postWorkspaceMembers(long userId, long workspaceId, String userEmail) {
        if(chkWorkspaceOwner(userId, workspaceId))
            throw new WorkspaceOwnerRequiredException();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Users addUser = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new NotFoundUserException());
        Workspaces targetWorkspace = workspaceJpaRepository.findByWorkspaceId(workspaceId)
                .orElseThrow(() -> new NotFoundWorkspaceException());
        Channels basicChannel = channelCustomRepository.findBasicChannelsByWorkspaceId(workspaceId);

        if(chkExists(addUser, targetWorkspace))
            throw new AlreadyExistsWorkspaceUserException();

        // CREATE WORKSPACE-PROFILES
        WorkspaceProfiles newWorkspaceProfile = createWorkspaceProfile(addUser, targetWorkspace, now, WorkSpaceProfileRole.MEMBER);

        // CREATE PROFILE_CHANNEL
        createProfileChannel(newWorkspaceProfile, basicChannel, ProfileChannelRole.MEMBER);
    }

    public boolean chkExists(Users user, Workspaces workspace) {
        return workspaceProfileJpaRepository.existsByUserAndWorkspace(user, workspace);
    }

        /**
         * profiles_channels를 생성하는 메소드
         * @param workspaceProfile 타겟 워크스페이스-프로필
         * @param channel 타겟 채널
         */
    public void createProfileChannel(WorkspaceProfiles workspaceProfile, Channels channel, ProfileChannelRole role) {
        ProfilesChannels newProfileChannel = ProfilesChannels.builder()
                .workspaceProfile(workspaceProfile)
                .channel(channel)
                .profileChannelRole(role)
                .build();

        profileChannelJpaRepository.save(newProfileChannel);
    }

    /**
     * channels를 생성하는 메소드
     * @param workspaces 타겟 워크스페이스
     * @param now 현재 시간
     * @return 생성된 channels
     */
    public Channels createChannel(Workspaces workspaces, Timestamp now) {
        Channels newChannel = Channels.builder()
                .channelName(workspaces.getWorkspaceName() + "- 전체 채널")
                .channelOpened(true)
                .channelDefault(true)
                .channelCreatedAt(now)
                .channelUpdatedAt(now)
                .build();

        channelJpaRepository.save(newChannel);

        return newChannel;
    }

    /**
     * workspace_profiles을 생성하는 메소드
     * @param user 타겟 유저
     * @param workspace 타겟 워크스페이스
     * @param now 현재 시간
     * @return 생성된 workspace_profiles
     */
    private WorkspaceProfiles createWorkspaceProfile(Users user, Workspaces workspace, Timestamp now, WorkSpaceProfileRole role) {
        String workspaceProfileImg = "WORKSPACE PROFILES BASIC S3 URL";

        WorkspaceProfiles newWorkspaceProfile = WorkspaceProfiles.builder()
                .user(user)
                .workspace(workspace)
                .workspaceProfileName(user.getUserEmail().split("@")[0])
                .workspaceProfileImg(workspaceProfileImg)
                .workSpaceProfileRole(role)
                .workspaceProfileCreatedAt(now)
                .workspaceProfileUpdatedAt(now)
                .build();

        workspaceProfileJpaRepository.save(newWorkspaceProfile);

        return newWorkspaceProfile;
    }

    /**
     * 워크스페이스 소유자 여부를 확인하는 메소드
     * @param userId
     * @param workspaceId
     * @return true: 소유자, false: 멤버
     */
    private boolean chkWorkspaceOwner(long userId, long workspaceId) {
        return workspaceProfileCustomRepository.findWorkspaceProfileRoleByUserIdAndWorkspaceId(userId, workspaceId).getCode() == 0 ? false : true;
    }

    /**
     * 워크스페이스 초대 코드를 생성하는 메소드
     * @return 워크스페이스 코드 6자리
     */
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
