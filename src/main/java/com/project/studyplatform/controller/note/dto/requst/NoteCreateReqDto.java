package com.project.studyplatform.controller.note.dto.requst;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class NoteCreateReqDto {

    @NotBlank(message = "제목을 입력하세요.")
    @Length(max = 50, message = "제목은 최대 50자입니다.")
    private String title;
    private String content;
}
