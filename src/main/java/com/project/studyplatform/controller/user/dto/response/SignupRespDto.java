package com.project.studyplatform.controller.user.dto.response;

import com.project.studyplatform.domain.user.User;
import lombok.Getter;

@Getter
public class SignupRespDto {
    private final Long userId;

    public SignupRespDto(User user) {
        this.userId = user.getId();
    }
}
