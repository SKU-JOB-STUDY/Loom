package com.sku.loom.domain.workspace.service;

import com.sku.loom.domain.user.entity.Users;
import com.sku.loom.domain.user.repository.UserRepository;
import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import com.sku.loom.domain.workspace.entity.WorkspaceProfiles;
import com.sku.loom.domain.workspace.entity.Workspaces;
import com.sku.loom.domain.workspace.entity.role.WorkSpaceProfileRole;
import com.sku.loom.domain.workspace.repository.WorkspaceProfileJpaRepository;
import com.sku.loom.domain.workspace.repository.WorkspaceRepository;
import com.sku.loom.global.exception.user.NotFoundUserException;
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
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceProfileJpaRepository workspaceProfileJpaRepository;
    private final S3Service s3Service;

    @Override
    public List<WorkspaceResponse> getWorkspaces(Long userId) {
        return workspaceProfileJpaRepository.findWorkspaceResponsesByUserId(userId);
    }

    @Override
    @Transactional
    public void postWorkspace(long userId, String workspaceName, MultipartFile image) throws IOException {
        // CREATE WORKSPACES
        String workspaceImg = "WORKSPACE BASIC S3 URL";

        if(image != null && !image.isEmpty())
            workspaceImg = s3Service.uploadS3(image, "/workspaces/img");

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Workspaces newWorkspace = Workspaces.builder()
                .workspaceName(workspaceName)
                .workspaceImg(workspaceImg)
                .workspaceCode(generateWorkspaceCode())
                .workspaceCreatedAt(now)
                .workspaceUpdatedAt(now)
                .build();

        workspaceRepository.save(newWorkspace);

        // CREATE WORKSPACE-PROFILES
        String workspaceProfileImg = "WORKSPACE PROFILES BASIC S3 URL";
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundUserException());

        WorkspaceProfiles newWorkspaceProfile = WorkspaceProfiles.builder()
                .user(user)
                .workspace(newWorkspace)
                .workspaceProfileName(user.getUserEmail().split("@")[0])
                .workSpaceProfileRole(WorkSpaceProfileRole.OWNER)
                .workspaceProfileCreatedAt(now)
                .workspaceProfileUpdatedAt(now)
                .build();

        workspaceProfileJpaRepository.save(newWorkspaceProfile);
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
        } while (workspaceRepository.existsByWorkspaceCode(code));

        return code;
    }
}
