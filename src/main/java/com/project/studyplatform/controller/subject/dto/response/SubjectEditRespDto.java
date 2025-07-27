package com.project.studyplatform.controller.subject.dto.response;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubjectEditRespDto {
    private Long subjectId;
    private Long userId;
    private String subjectName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public SubjectEditRespDto(Subject subject, Member member){
        this.subjectId = subject.getId();
        this.userId = member.getId();
        this.subjectName = subject.getName();
        this.createdAt = subject.getCreatedAt();
        this.modifiedAt = subject.getModifiedAt();
    }
}
