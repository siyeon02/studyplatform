package com.project.studyplatform.controller.studyroom.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyRoomDeleteReqDto {
    private Long studyRoomId;
    private Long managerId;
    private String password;
}
