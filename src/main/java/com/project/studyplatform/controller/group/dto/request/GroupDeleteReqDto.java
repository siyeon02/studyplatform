package com.project.studyplatform.controller.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupDeleteReqDto {
    @NotNull(message = "그룹 아이디를 입력하세요.")
    private Long groupId;
}
