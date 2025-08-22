package com.project.studyplatform.controller.member.dto.response;

import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

@Getter
public class SignupRespDto {
    private final Long userId;
    private final String accessToken;

    public SignupRespDto(Member member, String accessToken) {
        this.userId = member.getId();
        this.accessToken = accessToken;
    }
}
