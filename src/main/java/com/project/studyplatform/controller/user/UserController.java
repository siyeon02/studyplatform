package com.project.studyplatform.controller.user;

import com.project.studyplatform.controller.user.dto.request.SignupReqDto;
import com.project.studyplatform.controller.user.dto.request.UserDeleteReqDto;
import com.project.studyplatform.controller.user.dto.request.UserProfileEditReqDto;
import com.project.studyplatform.controller.user.dto.response.UserProfileRespDto;
import com.project.studyplatform.controller.user.dto.response.SignupRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.service.UserService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResult<SignupRespDto>> signup(@Valid @RequestBody SignupReqDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(userService.signup(dto)));
    }

    @GetMapping("/users/profile")
    public ResponseEntity<ApiResult<UserProfileRespDto>> userProfile(@AuthenticationPrincipal(expression = "user") User user) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(userService.findUserProfile(user.getEmail())));
    }

    @PutMapping("/users/profile")
    public ResponseEntity<ApiResult<Void>> editUser(@AuthenticationPrincipal(expression = "user") User user, @Valid @RequestBody UserProfileEditReqDto dto) {
        userService.userProfileEdit(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

    @DeleteMapping("/users/profile")
    public ResponseEntity<ApiResult<Void>> deleteUser(@AuthenticationPrincipal(expression = "user") User user, @Valid @RequestBody UserDeleteReqDto dto) {
        userService.deleteUser(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

}
