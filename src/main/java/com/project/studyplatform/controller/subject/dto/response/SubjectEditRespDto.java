package com.project.studyplatform.controller.subject.dto.response;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubjectEditRespDto {
    private Long subjectId;
    private Long userId;
    private String subjectName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public SubjectEditRespDto(Subject subject, User user){
        this.subjectId = subject.getId();
        this.userId = user.getId();
        this.subjectName = subject.getName();
        this.createdAt = subject.getCreatedAt();
        this.modifiedAt = subject.getModifiedAt();
    }
}
