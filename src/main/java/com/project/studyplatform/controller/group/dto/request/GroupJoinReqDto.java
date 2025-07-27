package com.project.studyplatform.controller.group.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupJoinReqDto {
    @NotNull
    private Long memberId;
}
