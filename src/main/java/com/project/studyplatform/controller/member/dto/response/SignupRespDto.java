package com.project.studyplatform.controller.member.dto.response;

import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

@Getter
public class SignupRespDto {
    private final Long userId;

    public SignupRespDto(Member member) {
        this.userId = member.getId();
    }
}
