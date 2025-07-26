package com.project.studyplatform.controller.studyroom.dto.response;

import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.studyroom.StudyRoom;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyRoomJoinRespDto {
    private Long studyRoomId;
    private Long managerId;
    private Long memberId;
    private String studyRoomName;
    private Integer maxParticipants;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public StudyRoomJoinRespDto(StudyRoom studyRoom, Member member){
        this.studyRoomId = studyRoom.getId();
        this.managerId = studyRoom.getManager().getId();
        this.memberId = member.getId();
        this.studyRoomName = studyRoom.getName();
        this.maxParticipants = studyRoom.getMaxParticipants();
        this.createdAt = studyRoom.getCreatedAt();
        this.modifiedAt = studyRoom.getModifiedAt();
    }
}
