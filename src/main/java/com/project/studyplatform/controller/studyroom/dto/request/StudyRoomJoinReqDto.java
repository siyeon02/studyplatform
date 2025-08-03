package com.project.studyplatform.controller.studyroom.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyRoomJoinReqDto {
    private Long memberId;
}
