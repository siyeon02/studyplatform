package com.project.studyplatform.controller.user.dto.response;

import com.project.studyplatform.domain.user.Status;
import com.project.studyplatform.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserProfileRespDto {
    private String nickname;
    private Status status;
    private List<String> groupList;
    private List<String> studyroomList;

    public UserProfileRespDto(User user) {
        this.nickname = user.getNickname();
        this.status = user.getStatus();
    }

}
