package com.project.studyplatform.controller.studyroom;

import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomCreateReqDto;
import com.project.studyplatform.controller.studyroom.dto.response.StudyRoomCreateRespDto;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.StudyRoomService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyroom")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @PostMapping
    public ResponseEntity<ApiResult<StudyRoomCreateRespDto>> createStudyRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid@RequestBody StudyRoomCreateReqDto reqDto){
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(studyRoomService.createStudyRoom(member.getId(), reqDto)));
    }
}
