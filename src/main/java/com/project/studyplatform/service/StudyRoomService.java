package com.project.studyplatform.service;

import com.project.studyplatform.controller.group.dto.response.GroupEditRespDto;
import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomCreateReqDto;
import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomDeleteReqDto;
import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomEditReqDto;
import com.project.studyplatform.controller.studyroom.dto.response.StudyRoomCreateRespDto;
import com.project.studyplatform.controller.studyroom.dto.response.StudyRoomEditRespDto;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final MemberRepository memberRepository;

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

    public StudyRoomEditRespDto editStudyRoom(Long memberId, Long studyroomId, StudyRoomEditReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        StudyRoom studyRoom = studyRoomRepository.findById(studyroomId)
                .orElseThrow(()-> new BusinessException(ErrorCode.STUDYROOM_NOT_FOUND));

        if (!member.getId().equals(studyRoom.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        studyRoom.modify(reqDto.getStudyRoomName(), reqDto.getDescription(), reqDto.getPassword(), reqDto.getMaxParticipants());

        return new StudyRoomEditRespDto(studyRoom);
    }

    public void deleteStudyRoom(Long memberId, Long studyroomId, StudyRoomDeleteReqDto reqDto) {
        StudyRoom studyRoom = studyRoomRepository.findById(studyroomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDYROOM_NOT_FOUND));

        if (!memberId.equals(studyRoom.getManager().getId())) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_DELETE);
        }

        studyRoomRepository.delete(studyRoom);
    }
}
