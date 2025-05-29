package com.project.studyplatform.controller.user;

import com.project.studyplatform.controller.user.dto.request.LoginReqDto;
import com.project.studyplatform.controller.user.dto.request.SignupReqDto;
import com.project.studyplatform.controller.user.dto.response.LoginRespDto;
import com.project.studyplatform.controller.user.dto.response.SignupRespDto;
import com.project.studyplatform.service.UserService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResult<SignupRespDto>> signup(@Valid @RequestBody SignupReqDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(userService.signup(dto)));
    }

}
