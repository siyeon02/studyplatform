package com.project.studyplatform.controller.studyroom;

import com.project.studyplatform.controller.studyroom.dto.request.*;
import com.project.studyplatform.controller.studyroom.dto.response.*;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.StudyRoomService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResult<Void>> deleteStudyRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long studyroomId, @Valid @RequestBody StudyRoomDeleteReqDto reqDto) {
        Member member = userDetails.getUser();
        studyRoomService.deleteStudyRoom(member.getId(), studyroomId, reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

    @PostMapping("/{studyroomId}/member")
    public ResponseEntity<ApiResult<StudyRoomJoinRespDto>> joinStudyRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long studyroomId, @Valid @RequestBody StudyRoomJoinReqDto reqDto) {
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(studyRoomService.joinStudyRoom(member.getId(), studyroomId, reqDto)));
    }

    @GetMapping("{studyroomId}")
    public ResponseEntity<ApiResult<StudyRoomInfoRespDto>> retrieveStudyRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long studyroomId, @Valid @RequestBody StudyRoomInfoReqDto reqDto) {
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(studyRoomService.retrieveStudyRoom(member.getId(), studyroomId, reqDto)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResult<List<AllStudyRoomInfoRespDto>>> retrieveAllStudyRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        List<AllStudyRoomInfoRespDto> studyRoomList = studyRoomService.retrieveAllStudyRooms(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(studyRoomList));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResult<List<StudyRoomSearchRespDto>>> searchStudyRooms(@RequestParam("name") String name) {
        List<StudyRoomSearchRespDto> result = studyRoomService.searchStudyRooms(name);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(result));
    }
}
