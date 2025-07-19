package com.project.studyplatform.controller.subject;

import com.project.studyplatform.controller.subject.dto.request.SubjectCreateReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectEditReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectInfoReqDto;
import com.project.studyplatform.controller.subject.dto.response.AllSubjectInfoRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectCreateRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectEditRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectInfoRespDto;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.SubjectService;
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
@RequestMapping("/api")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/subjects")
    public ResponseEntity<ApiResult<SubjectCreateRespDto>> createSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody SubjectCreateReqDto reqDto) {
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(subjectService.createSubject(member.getId(), reqDto)));
    }

    @PutMapping("/subjects/{subjectId}")
    public ResponseEntity<ApiResult<SubjectEditRespDto>> editSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long subjectId, @Valid @RequestBody SubjectEditReqDto reqDto){
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(subjectService.editSubject(member.getId(), subjectId, reqDto)));
    }

    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<ApiResult<Void>> deleteSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long subjectId){
        Member member = userDetails.getUser();
        subjectService.deleteSubject(member.getId(), subjectId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<ApiResult<SubjectInfoRespDto>> retrieveSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long subjectId, @Valid @RequestBody SubjectInfoReqDto reqDto){
        Member member = userDetails.getUser();
        return ResponseEntity.status((HttpStatus.OK)).body(ApiResult.success(subjectService.retrieveSubject(member.getId(), subjectId, reqDto)));    }


    @GetMapping("/subjects/all")
    public ResponseEntity<ApiResult<List<AllSubjectInfoRespDto>>> retrieveAllSubjects(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getUser();
        List<AllSubjectInfoRespDto> subjectList =  subjectService.retrieveAllSubjects(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(subjectList));
    }


}
