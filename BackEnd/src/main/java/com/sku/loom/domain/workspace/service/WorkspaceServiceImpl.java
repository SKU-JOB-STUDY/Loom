package com.sku.loom.domain.workspace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class WorkspaceServiceImpl implements WorkspaceService{

    private final WorkspaceRepository workspaceRepository;
}
