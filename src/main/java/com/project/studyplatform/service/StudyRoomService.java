package com.project.studyplatform.service;

import com.project.studyplatform.controller.studyroom.dto.request.StudyRoomCreateReqDto;
import com.project.studyplatform.controller.studyroom.dto.response.StudyRoomCreateRespDto;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.repository.MemberRepository;
import com.project.studyplatform.domain.studyroom.StudyRoom;
import com.project.studyplatform.domain.studyroom.StudyRoomUser;
import com.project.studyplatform.domain.studyroom.repository.StudyRoomRepository;
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
}
