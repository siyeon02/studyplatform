package com.project.studyplatform.controller.studyroom.dto.response;

import com.project.studyplatform.domain.studyroom.StudyRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StudyRoomSearchRespDto {
    private Long studyRoomId;
    private String studyRoomName;
    private Long managerId;
    private List<Long> memberIds;
    private String description;
    private Integer maxParticipants;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public StudyRoomSearchRespDto(StudyRoom studyRoom){
        this.studyRoomId = studyRoom.getId();
        this.studyRoomName = studyRoom.getName();
        this.managerId = studyRoom.getManager().getId();
        this.memberIds = studyRoom.getParticipants().stream().map(participants -> participants.getMember().getId()).collect(Collectors.toList());
        this.description = studyRoom.getDescription();
        this.maxParticipants = studyRoom.getMaxParticipants();
        this.createdAt = studyRoom.getCreatedAt();
        this.modifiedAt = studyRoom.getModifiedAt();
    }
}
