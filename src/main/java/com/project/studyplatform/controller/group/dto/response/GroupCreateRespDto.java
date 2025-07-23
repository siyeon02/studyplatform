package com.project.studyplatform.controller.group.dto.response;

import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;
import org.apache.catalina.Manager;

import java.time.LocalDateTime;

@Getter
public class GroupCreateRespDto {
    private Long groupId;
    private Long managerId;
    private String groupName;
    private LocalDateTime createdAt;

    public GroupCreateRespDto(Group group, Member member) {
     this.groupId = group.getId();
     this.managerId = member.getId();
     this.groupName = group.getName();
     this.createdAt = group.getCreatedAt();
    }
}
