package com.project.studyplatform.service;

import com.project.studyplatform.controller.group.dto.request.*;
import com.project.studyplatform.controller.group.dto.response.GroupCreateRespDto;
import com.project.studyplatform.controller.group.dto.response.GroupEditRespDto;
import com.project.studyplatform.controller.group.dto.response.GroupInfoRespDto;
import com.project.studyplatform.controller.group.dto.response.GroupJoinRespDto;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Transactional
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

    @Transactional
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

    @Transactional
    public void deleteGroup(Long memberId, Long groupId, GroupDeleteReqDto reqDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new BusinessException(ErrorCode.GROUP_NOT_FOUND));

        if (!memberId.equals(group.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_DELETE);
        }

        groupRepository.delete(group);
    }


    public GroupInfoRespDto retrieveGroup(Long memberId, Long groupId, GroupInfoReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_NOT_FOUND));

        return new GroupInfoRespDto(group);
    }

    @Transactional
    public GroupJoinRespDto joinGroup(Long memberId, Long groupId, GroupJoinReqDto reqDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_NOT_FOUND));

        // 중복 가입 방지 (옵션)
        boolean alreadyJoined = group.getGroupMembers().stream()
                .anyMatch(gm -> gm.getMember().getId().equals(memberId));
        if (alreadyJoined) {
            throw new BusinessException(ErrorCode.ALREADY_JOINED);
        }

        group.addGroupMember(member);

        groupRepository.save(group); // Cascade.ALL이므로 GroupMember도 저장됨

        return new GroupJoinRespDto(group, member);
    }
}
