package com.project.studyplatform.service;

import com.project.studyplatform.controller.group.dto.request.GroupCreateReqDto;
import com.project.studyplatform.controller.group.dto.request.GroupDeleteReqDto;
import com.project.studyplatform.controller.group.dto.request.GroupEditReqDto;
import com.project.studyplatform.controller.group.dto.response.GroupCreateRespDto;
import com.project.studyplatform.controller.group.dto.response.GroupEditRespDto;
import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.group.repository.GroupRepository;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.repository.MemberRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    public GroupCreateRespDto createGroup(Long memberId, GroupCreateReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Group group = Group.builder()
                .name(reqDto.getGroupName())
                .manager(member)
                .build();

        group.addGroupMember(member);

        Group savedGroup = groupRepository.save(group);
        return new GroupCreateRespDto(savedGroup, member);
    }

    public GroupEditRespDto editGroup(Long memberId, Long groupId, GroupEditReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_NOT_FOUND));

        if (!memberId.equals(group.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        group.modify(reqDto.getGroupName());

        return new GroupEditRespDto(group, member);

    }

    public void deleteGroup(Long memberId, Long groupId, GroupDeleteReqDto reqDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new BusinessException(ErrorCode.GROUP_NOT_FOUND));

        if (!memberId.equals(group.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_DELETE);
        }

        groupRepository.delete(group);
    }


}
