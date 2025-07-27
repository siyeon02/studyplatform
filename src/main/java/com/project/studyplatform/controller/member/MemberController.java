package com.project.studyplatform.controller.member;

import com.project.studyplatform.controller.member.dto.request.MemberDeleteReqDto;
import com.project.studyplatform.controller.member.dto.request.MemberProfileEditReqDto;
import com.project.studyplatform.controller.member.dto.response.MemberProfileRespDto;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.MemberService;
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
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/users/profile")
    public ResponseEntity<ApiResult<MemberProfileRespDto>> userProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(memberService.findUserProfile(member.getEmail())));
    }

    @PutMapping("/users/profile")
    public ResponseEntity<ApiResult<Void>> editUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody MemberProfileEditReqDto dto) {
        Member member = userDetails.getUser();
        memberService.userProfileEdit(member.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

    @DeleteMapping("/users/profile")
    public ResponseEntity<ApiResult<Void>> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody MemberDeleteReqDto dto) {
        Member member = userDetails.getUser();
        memberService.deleteUser(member.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

}
