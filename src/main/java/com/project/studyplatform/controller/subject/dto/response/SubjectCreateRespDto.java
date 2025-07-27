package com.project.studyplatform.controller.subject.dto.response;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubjectCreateRespDto {
    private Long subjectId;
    private Long userId;
    private String subjectName;
    private LocalDateTime createdAt;

    public SubjectCreateRespDto(Subject subject, Member member){
        this.subjectId = subject.getId();
        this.userId = member.getId();
        this.subjectName = subject.getName();
        this.createdAt = subject.getCreatedAt();
    }
}
