package com.project.studyplatform.controller.studyroom.dto.response;

import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.studyroom.StudyRoom;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyRoomCreateRespDto {
    private Long studyRoomId;
    private Long managerId;
    private String studyRoomName;
    private String zoomLink;
    private String password;
    private LocalDateTime createdAt;

    public StudyRoomCreateRespDto(StudyRoom studyRoom, Member member){
        this.studyRoomId = studyRoom.getId();
        this.managerId = member.getId();
        this.studyRoomName = studyRoom.getName();
        this.zoomLink = studyRoom.getZoomLink();
        this.password = studyRoom.getPassword();
        this.createdAt = studyRoom.getCreatedAt();
    }
}
