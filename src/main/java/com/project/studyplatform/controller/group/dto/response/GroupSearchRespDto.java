package com.project.studyplatform.controller.group.dto.response;

import com.project.studyplatform.domain.group.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GroupSearchRespDto {
    private Long groupId;
    private Long managerId;
    private List<Long> memberIds;
    private String groupName;
    private Integer maxParticipants;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public GroupSearchRespDto(Group group) {
        this.groupId = group.getId();
        this.managerId = group.getManager().getId();
        this.memberIds = group.getGroupMembers().stream().map(groupMember -> groupMember.getMember().getId()).collect(Collectors.toList());
        this.groupName = group.getName();
        this.maxParticipants = group.getMaxParticipants();
        this.createdAt = group.getCreatedAt();
        this.modifiedAt = group.getModifiedAt();
    }
}
