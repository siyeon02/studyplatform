package com.project.studyplatform.controller.studyroom.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class StudyRoomCreateReqDto {
    @NotBlank(message = "스터디룸 명을 입력하세요.")
    @Length(max = 50, message = "스터디룸 명은 최대 50자입니다.")
    private String studyRoomName;
    private String description;
    private String password;
    private Integer maxParticipants;
    private String zoomLink;

}
