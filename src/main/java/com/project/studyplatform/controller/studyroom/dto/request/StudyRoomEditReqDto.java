package com.project.studyplatform.controller.studyroom.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyRoomEditReqDto {
    private String studyRoomName;
    private String description;
    private String password;
    private Integer maxParticipants;
}
