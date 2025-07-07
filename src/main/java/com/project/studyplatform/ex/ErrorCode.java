package com.project.studyplatform.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 유저를 찾을 수 없습니다."),
    USER_ALREADY_EXIST(HttpStatus.ALREADY_REPORTED.value(), "이미 존재하는 유저입니다."),
    TOKEN_MISSING(HttpStatus.BAD_REQUEST.value(), "토큰이 존재하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST.value(), "토큰이 만료되었습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST.value(), "토큰의 타입이 잘못되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST.value(), "토큰의 형식이 잘못되었습니다."),
    INVALID_TOKEN_SIGNATURE(HttpStatus.BAD_REQUEST.value(), "서명 검증이 실패했습니다."),
    INVALID_TOKEN_PARSING(HttpStatus.BAD_REQUEST.value(), "토큰 파싱이 잘못되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류입니다."),
    PASSWORD_NOT_MATCHING(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "노트를 찾을 수 없습니다."),
    NO_PERMISSION_TO_EDIT(HttpStatus.FORBIDDEN.value(), "노트를 수정할 권한이 없습니다."),
    SUBJECT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "과목을 찾을 수 없습니다."),
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message){
        this.status = status;
        this.message = message;
    }



}
