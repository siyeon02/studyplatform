package com.project.studyplatform.controller.group;

import com.project.studyplatform.controller.group.dto.request.GroupCreateReqDto;
import com.project.studyplatform.controller.group.dto.request.GroupDeleteReqDto;
import com.project.studyplatform.controller.group.dto.request.GroupEditReqDto;
import com.project.studyplatform.controller.group.dto.response.GroupCreateRespDto;
import com.project.studyplatform.controller.group.dto.response.GroupEditRespDto;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.GroupService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<ApiResult<GroupCreateRespDto>> createGroup(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody GroupCreateReqDto reqDto) {

        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(groupService.createGroup(member.getId(), reqDto)));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResult<GroupEditRespDto>> editGroup(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupId, @Valid @RequestBody GroupEditReqDto reqDto) {
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(groupService.editGroup(member.getId(), groupId, reqDto)));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResult<Void>> deleteGroup(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupId, @Valid @RequestBody GroupDeleteReqDto reqDto) {
        Member member = userDetails.getUser();
        groupService.deleteGroup(member.getId(), groupId, reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }
}
