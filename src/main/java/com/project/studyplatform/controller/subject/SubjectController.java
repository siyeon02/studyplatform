package com.project.studyplatform.controller.subject;

import com.project.studyplatform.controller.subject.dto.request.SubjectCreateReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectEditReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectInfoReqDto;
import com.project.studyplatform.controller.subject.dto.response.AllSubjectInfoRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectCreateRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectEditRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectInfoRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.SubjectService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {

    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService) { this.subjectService = subjectService; }

    @PostMapping("/subjects")
    public ResponseEntity<ApiResult<SubjectCreateRespDto>> createSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody SubjectCreateReqDto reqDto) {
        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(subjectService.createSubject(user.getId(), reqDto)));
    }

    @PutMapping("/subjects/{subjectId}")
    public ResponseEntity<ApiResult<SubjectEditRespDto>> editSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long subjectId, @Valid @RequestBody SubjectEditReqDto reqDto){
        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(subjectService.editSubject(user.getId(), subjectId, reqDto)));
    }

    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<ApiResult<Void>> deleteSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long subjectId){
        User user = userDetails.getUser();
        subjectService.deleteSubject(user.getId(), subjectId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<ApiResult<SubjectInfoRespDto>> retrieveSubject(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long subjectId, @Valid @RequestBody SubjectInfoReqDto reqDto){
        User user = userDetails.getUser();
        return ResponseEntity.status((HttpStatus.OK)).body(ApiResult.success(subjectService.retrieveSubject(user.getId(), subjectId, reqDto)));    }


    @GetMapping("/subjects/all")
    public ResponseEntity<ApiResult<List<AllSubjectInfoRespDto>>> retrieveAllSubjects(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        List<AllSubjectInfoRespDto> subjectList =  subjectService.retrieveAllSubjects(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(subjectList));
    }


}
