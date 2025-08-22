package com.project.studyplatform.service;

import com.project.studyplatform.controller.group.dto.response.GroupSearchRespDto;
import com.project.studyplatform.controller.studyroom.dto.request.*;
import com.project.studyplatform.controller.studyroom.dto.response.*;
import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.repository.MemberRepository;
import com.project.studyplatform.domain.studyroom.StudyRoom;
import com.project.studyplatform.domain.studyroom.StudyRoomUser;
import com.project.studyplatform.domain.studyroom.repository.StudyRoomRepository;
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
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public StudyRoomCreateRespDto createStudyRoom(Long memberId, StudyRoomCreateReqDto reqDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        StudyRoom studyRoom = StudyRoom.builder()
                .name(reqDto.getStudyRoomName())
                .manager(member)
                .zoomLink(reqDto.getZoomLink())
                .password(reqDto.getPassword())
                .description(reqDto.getDescription())
                .maxParticipants(reqDto.getMaxParticipants())
                .build();

        StudyRoomUser studyRoomUser = new StudyRoomUser(member, studyRoom);
        studyRoom.getParticipants().add(studyRoomUser);
        member.getStudyRoomUsers().add(studyRoomUser);

        StudyRoom savedStudyRoom = studyRoomRepository.save(studyRoom);
        return new StudyRoomCreateRespDto(savedStudyRoom, member);
    }

    @Transactional
    public StudyRoomEditRespDto editStudyRoom(Long memberId, Long studyroomId, StudyRoomEditReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        StudyRoom studyRoom = studyRoomRepository.findById(studyroomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDYROOM_NOT_FOUND));

        if (!member.getId().equals(studyRoom.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        studyRoom.modify(reqDto.getStudyRoomName(), reqDto.getDescription(), reqDto.getPassword(), reqDto.getMaxParticipants());

        return new StudyRoomEditRespDto(studyRoom);
    }

    @Transactional
    public void deleteStudyRoom(Long memberId, Long studyroomId, StudyRoomDeleteReqDto reqDto) {
        StudyRoom studyRoom = studyRoomRepository.findById(studyroomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDYROOM_NOT_FOUND));

        if (!memberId.equals(studyRoom.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_DELETE);
        }

        studyRoomRepository.delete(studyRoom);
    }

    @Transactional
    public StudyRoomJoinRespDto joinStudyRoom(Long memberId, Long studyroomId, StudyRoomJoinReqDto reqDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        StudyRoom studyRoom = studyRoomRepository.findById(studyroomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDYROOM_NOT_FOUND));

        boolean alreadyJoined = studyRoom.getParticipants().stream()
                .anyMatch(participant -> participant.getMember().getId().equals(memberId));

        if (alreadyJoined) {
            throw new BusinessException(ErrorCode.ALREADY_JOINED);
        }

        if (studyRoom.getParticipants().size() >= studyRoom.getMaxParticipants()) {
            throw new BusinessException(ErrorCode.MAX_PARTICIPANTS);
        }

        StudyRoomUser studyRoomUser = StudyRoomUser.builder()
                .member(member)
                .studyRoom(studyRoom)
                .build();

        studyRoom.getParticipants().add(studyRoomUser);

        return new StudyRoomJoinRespDto(studyRoom, member);

    }

    public StudyRoomInfoRespDto retrieveStudyRoom(Long memberId, Long studyroomId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        StudyRoom studyRoom = studyRoomRepository.findById(studyroomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDYROOM_NOT_FOUND));

        return new StudyRoomInfoRespDto(studyRoom);

    }

    public List<AllStudyRoomInfoRespDto> retrieveAllStudyRooms(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        List<StudyRoom> studyRoomList = studyRoomRepository.findStudyRoomsByMember(member);

        return studyRoomList.stream()
                .map(AllStudyRoomInfoRespDto::new)
                .collect(Collectors.toList());
    }

    public List<StudyRoomSearchRespDto> searchStudyRooms(String name) {
        List<StudyRoom> studyRooms = studyRoomRepository.findByNameContaining(name);
        return studyRooms.stream()
                .map(StudyRoomSearchRespDto::new)
                .collect(Collectors.toList());
    }
}
