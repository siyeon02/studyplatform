package com.project.studyplatform.controller.group;

import com.project.studyplatform.controller.group.dto.request.*;
import com.project.studyplatform.controller.group.dto.response.*;
import com.project.studyplatform.controller.subject.dto.response.AllSubjectInfoRespDto;
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

import java.util.List;

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

    @PostMapping("/{groupId}/member")
    public ResponseEntity<ApiResult<GroupJoinRespDto>> joinGroup(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupId, @Valid@RequestBody GroupJoinReqDto reqDto){
        Member member= userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(groupService.joinGroup(member.getId(), groupId, reqDto)));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResult<GroupInfoRespDto>> retrieveGroup(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupId, @Valid @RequestBody GroupInfoReqDto reqDto){
        Member member = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(groupService.retrieveGroup(member.getId(), groupId, reqDto)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResult<List<AllGroupInfoRespDto>>> retrieveAllGroups(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getUser();
        List<AllGroupInfoRespDto> groupList =  groupService.retrieveAllGroups(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(groupList));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResult<List<GroupSearchRespDto>>> searchGroups(@RequestParam("name") String name){
        List<GroupSearchRespDto> result = groupService.searchGroups(name);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(result));
    }
}
