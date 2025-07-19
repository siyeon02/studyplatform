package com.project.studyplatform.controller.member.dto.request;

import com.project.studyplatform.domain.member.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileEditReqDto {

    @NotBlank(message = "이름을 입력하세요.")
    @Length(max = 20, message = "이름은 최대 20자입니다.")
    private String name;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "올바른 이메일 형식을 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickname;

    @NotNull
    private Status status;
}
