package com.project.studyplatform.controller.group.dto.response;

import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroupEditRespDto {
    private Long groupId;
    private Long managerId;
    private String groupName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public GroupEditRespDto(Group group, Member member) {
        this.groupId = group.getId();
        this.managerId = member.getId();
        this.groupName = group.getName();
        this.createdAt = group.getCreatedAt();
        this.modifiedAt = group.getModifiedAt();
    }
}
