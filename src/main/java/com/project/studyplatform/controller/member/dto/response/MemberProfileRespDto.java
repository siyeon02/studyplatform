package com.project.studyplatform.controller.member.dto.response;

import com.project.studyplatform.domain.member.Status;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberProfileRespDto {
    private String nickname;
    private Status status;
    private List<String> groupList;
    private List<String> studyroomList;

    public MemberProfileRespDto(Member member) {
        this.nickname = member.getNickname();
        this.status = member.getStatus();
    }

}
