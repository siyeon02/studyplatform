package com.project.studyplatform.controller.subject.dto.response;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubjectCreateRespDto {
    private Long subjectId;
    private Long userId;
    private String subjectName;
    private LocalDateTime createdAt;

    public SubjectCreateRespDto(Subject subject, User user){
        this.subjectId = subject.getId();
        this.userId = user.getId();
        this.subjectName = subject.getName();
        this.createdAt = subject.getCreatedAt();
    }
}
