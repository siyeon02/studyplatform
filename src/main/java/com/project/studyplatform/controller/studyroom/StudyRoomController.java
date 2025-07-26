package com.project.studyplatform.controller.studyroom;

import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomCreateReqDto;
import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomDeleteReqDto;
import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomEditReqDto;
import com.project.studyplatform.controller.studyroom.dto.response.StudyRoomCreateRespDto;
import com.project.studyplatform.controller.studyroom.dto.response.StudyRoomEditRespDto;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.StudyRoomService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyrooms")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @PostMapping
    public ResponseEntity<ApiResult<StudyRoomCreateRespDto>> createStudyRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody StudyRoomCreateReqDto reqDto) {
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(studyRoomService.createStudyRoom(member.getId(), reqDto)));
    }

    @PutMapping("/{studyroomId}")
    public ResponseEntity<ApiResult<StudyRoomEditRespDto>> editStudyRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long studyroomId, @Valid @RequestBody StudyRoomEditReqDto reqDto) {
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(studyRoomService.editStudyRoom(member.getId(), studyroomId, reqDto)));
    }

    @DeleteMapping("/{studyroomId}")
    public ResponseEntity<ApiResult<Void>> deleteStudyRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long studyroomId, @Valid @RequestBody StudyRoomDeleteReqDto reqDto){
        Member member = userDetails.getUser();
        studyRoomService.deleteStudyRoom(member.getId(), studyroomId, reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }
}
