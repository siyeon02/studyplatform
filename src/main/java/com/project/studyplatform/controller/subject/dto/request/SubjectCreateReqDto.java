package com.project.studyplatform.controller.subject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class SubjectCreateReqDto {
    @NotBlank(message = "과목 명을 입력하세요.")
    @Length(max = 50, message = "과목 명은 최대 50자입니다.")
    private String subjectName;
}
