package com.project.studyplatform.controller.member.dto.response;

import com.project.studyplatform.domain.member.Status;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberProfileRespDto {
    private Long memberId;
    private String nickname;
    private Status status;
    private List<String> groupList;
    private List<String> studyroomList;

    public MemberProfileRespDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.status = member.getStatus();
        this.groupList = member.getGroupMembers().stream()
                .map(groupMember -> groupMember.getGroup().getName())
                .collect(Collectors.toList());
        this.studyroomList = member.getStudyRoomUsers().stream()
                .map(studyRoomUser -> studyRoomUser.getStudyRoom().getName())
                .collect(Collectors.toList());
    }

}
