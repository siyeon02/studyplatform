package com.project.studyplatform.ex;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * API응답 상태에 대한 정보를 제공하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
public class ErrorRespDto {
    private String date;
    private int state;
    private String message;
    private String url;

    public ErrorRespDto(BusinessException ex, String requestUrl) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = ex.getErrorCode().getStatus();
        message = ex.getMessage();
        url = requestUrl;
    }

    public ErrorRespDto(ErrorCode errorCode, String requestUrl) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = errorCode.getStatus();
        message = errorCode.getMessage();
        url = requestUrl;
    }

    public ErrorRespDto(ErrorCode errorCode, String requestUrl, String detailMsg) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = errorCode.getStatus();
        message = errorCode.getMessage() + ": " + detailMsg;
        url = requestUrl;
    }
}

