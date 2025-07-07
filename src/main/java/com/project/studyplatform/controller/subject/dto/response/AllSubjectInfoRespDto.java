package com.project.studyplatform.controller.subject.dto.response;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AllSubjectInfoRespDto {
    private Long subjectId;
    private Long userId;
    private String subjectName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AllSubjectInfoRespDto(Subject subject){
        this.subjectId = subject.getId();
        this.userId = subject.getUser().getId();
        this.subjectName = subject.getName();
        this.createdAt = subject.getCreatedAt();
        this.createdAt = subject.getModifiedAt();
    }

}
