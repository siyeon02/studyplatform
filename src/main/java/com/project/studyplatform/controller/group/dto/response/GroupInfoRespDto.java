package com.project.studyplatform.controller.group.dto.response;

import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GroupInfoRespDto {
    private Long groupId;
    private Long managerId;
    private List<Long> memberIds;
    private String groupName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public GroupInfoRespDto(Group group) {
        this.groupId = group.getId();
        this.managerId = group.getManager().getId();
        this.memberIds = group.getGroupMembers().stream().map(groupMember -> groupMember.getMember().getId()).collect(Collectors.toList());
        this.groupName = group.getName();
        this.createdAt = group.getCreatedAt();
        this.modifiedAt = group.getModifiedAt();
    }
}
