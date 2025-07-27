package com.project.studyplatform.controller.group.dto.response;

import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroupJoinRespDto {
    private Long groupId;
    private Long managerId;
    private Long memberId;
    private String groupName;
    private Integer maxParticipants;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public GroupJoinRespDto(Group group, Member member){
        this.groupId = group.getId();
        this.managerId = group.getManager().getId();
        this.memberId = member.getId();
        this.groupName = group.getName();
        this.maxParticipants = group.getMaxParticipants();
        this.createdAt = group.getCreatedAt();
        this.modifiedAt = group.getModifiedAt();
    }
}
