package com.project.studyplatform.service;

import com.project.studyplatform.controller.group.dto.request.*;
import com.project.studyplatform.controller.group.dto.response.*;
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

import java.util.List;
import java.util.stream.Collectors;

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
                .maxParticipants(reqDto.getMaxParticipants())
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

        group.modify(reqDto.getGroupName(), reqDto.getMaxParticipants());

        return new GroupEditRespDto(group, member);

    }

    @Transactional
    public void deleteGroup(Long memberId, Long groupId, GroupDeleteReqDto reqDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_NOT_FOUND));

        if (!memberId.equals(group.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_DELETE);
        }

        groupRepository.delete(group);
    }


    public GroupInfoRespDto retrieveGroup(Long memberId, Long groupId) {
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

        boolean alreadyJoined = group.getGroupMembers().stream()
                .anyMatch(gm -> gm.getMember().getId().equals(memberId));
        if (alreadyJoined) {
            throw new BusinessException(ErrorCode.ALREADY_JOINED);
        }

        group.addGroupMember(member);

        groupRepository.save(group);

        return new GroupJoinRespDto(group, member);
    }

    public List<AllGroupInfoRespDto> retrieveAllGroups(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        List<Group> groupList = groupRepository.findAllGroupsByMember(member);

        return groupList.stream()
                .map(AllGroupInfoRespDto::new)
                .collect(Collectors.toList());

    }

    public List<GroupSearchRespDto> searchGroups(String name) {
        List<Group> groups = groupRepository.findByNameContaining(name);
        return groups.stream()
                .map(GroupSearchRespDto::new)
                .collect(Collectors.toList());
    }
}
