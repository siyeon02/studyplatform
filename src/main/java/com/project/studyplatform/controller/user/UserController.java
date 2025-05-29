package com.project.studyplatform.controller.user;

import com.project.studyplatform.controller.user.dto.request.SignupReqDto;
import com.project.studyplatform.controller.user.dto.request.UserDeleteReqDto;
import com.project.studyplatform.controller.user.dto.request.UserProfileEditReqDto;
import com.project.studyplatform.controller.user.dto.response.UserProfileRespDto;
import com.project.studyplatform.controller.user.dto.response.SignupRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.UserService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<ApiResult<UserProfileRespDto>> userProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(userService.findUserProfile(user.getEmail())));
    }

    @PutMapping("/users/profile")
    public ResponseEntity<ApiResult<Void>> editUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UserProfileEditReqDto dto) {
        User user = userDetails.getUser();
        userService.userProfileEdit(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

    @DeleteMapping("/users/profile")
    public ResponseEntity<ApiResult<Void>> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UserDeleteReqDto dto) {
        User user = userDetails.getUser();
        userService.deleteUser(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

}
