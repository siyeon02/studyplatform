package com.project.studyplatform.controller.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class GroupCreateReqDto {

    private String groupName;
    private Integer maxParticipants;
}
