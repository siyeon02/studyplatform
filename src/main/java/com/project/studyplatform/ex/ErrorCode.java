package com.project.studyplatform.ex;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 유저를 찾을 수 없습니다."),
    USER_ALREADY_EXIST(HttpStatus.ALREADY_REPORTED.value(), "이미 존재하는 유저입니다."),
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message){
        this.status = status;
        this.message = message;
    }



}
