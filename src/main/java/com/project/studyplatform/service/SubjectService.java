package com.project.studyplatform.service;

import com.project.studyplatform.controller.subject.dto.request.SubjectCreateReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectEditReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectInfoReqDto;
import com.project.studyplatform.controller.subject.dto.response.AllSubjectInfoRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectCreateRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectEditRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectInfoRespDto;
import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.subject.repository.SubjectRepository;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.repository.MemberRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;

    public SubjectCreateRespDto createSubject(Long memberId, SubjectCreateReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->{
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Subject subject = Subject.builder()
                .member(member)
                .name(reqDto.getSubjectName())
                .build();

        Subject savedSubject = subjectRepository.save(subject);

        return new SubjectCreateRespDto(savedSubject, member);

    }

    public SubjectEditRespDto editSubject(Long userId, Long subjectId, SubjectEditReqDto reqDto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Subject subject = subjectRepository.findByIdWithUser(subjectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        if(!userId.equals(subject.getMember().getId())){
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        subject.modify(reqDto.getSubjectName());

        return new SubjectEditRespDto(subject, member);

    }

    public void deleteSubject(Long userId, Long subjectId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        subjectRepository.delete(subject);
    }

    public SubjectInfoRespDto retrieveSubject(Long userId, Long subjectId, SubjectInfoReqDto reqDto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        if (!subject.getMember().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_VIEW);
        }

        return new SubjectInfoRespDto(subject, member);
    }

    public List<AllSubjectInfoRespDto> retrieveAllSubjects(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        List<Subject> subjectList = subjectRepository.findAllByUser(member);

        return subjectList.stream()
                .map(AllSubjectInfoRespDto::new)
                .collect(Collectors.toList());

    }
}
