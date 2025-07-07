package com.project.studyplatform.controller.subject;

import com.project.studyplatform.controller.subject.dto.request.SubjectCreateReqDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectCreateRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.SubjectService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
