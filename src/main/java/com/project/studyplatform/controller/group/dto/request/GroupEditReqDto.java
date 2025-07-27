package com.project.studyplatform.controller.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class GroupEditReqDto {
    @NotBlank(message = "그룹 명을 입력하세요.")
    @Length(max = 50, message = "그룹 명은 최대 50자입니다.")
    private String groupName;
    private Integer maxParticipants;
}
