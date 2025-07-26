package com.project.studyplatform.controller.studyroom.dto.response;

import com.project.studyplatform.domain.studyroom.StudyRoom;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyRoomEditRespDto {
    private Long studyRoomId;
    private String studyRoomName;
    private String description;
    private String password;
    private Integer maxParticipants;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public StudyRoomEditRespDto(StudyRoom studyRoom){
        this.studyRoomId = studyRoom.getId();
        this.studyRoomName = studyRoom.getName();
        this.description = studyRoom.getDescription();
        this.password = studyRoom.getPassword();
        this.maxParticipants = studyRoom.getMaxParticipants();
        this.createdAt = studyRoom.getCreatedAt();
        this.modifiedAt = studyRoom.getModifiedAt();
    }
}
