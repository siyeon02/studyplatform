package com.project.studyplatform.service;

import com.project.studyplatform.controller.note.dto.response.AllNoteInfoRespDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectCreateReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectEditReqDto;
import com.project.studyplatform.controller.subject.dto.request.SubjectInfoReqDto;
import com.project.studyplatform.controller.subject.dto.response.AllSubjectInfoRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectCreateRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectEditRespDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectInfoRespDto;
import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.subject.repository.SubjectRepository;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.domain.user.repository.UserRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public SubjectCreateRespDto createSubject(Long userId, SubjectCreateReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Subject subject = Subject.builder()
                .user(user)
                .name(reqDto.getSubjectName())
                .build();

        Subject savedSubject = subjectRepository.save(subject);

        return new SubjectCreateRespDto(savedSubject, user);

    }

    public SubjectEditRespDto editSubject(Long userId, Long subjectId, SubjectEditReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Subject subject = subjectRepository.findByIdWithUser(subjectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        if(!userId.equals(subject.getUser().getId())){
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        subject.modify(reqDto.getSubjectName());

        return new SubjectEditRespDto(subject,user);

    }

    public void deleteSubject(Long userId, Long subjectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        subjectRepository.delete(subject);
    }

    public SubjectInfoRespDto retrieveSubject(Long userId, Long subjectId, SubjectInfoReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        if (!subject.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_VIEW);
        }

        return new SubjectInfoRespDto(subject, user);
    }

    public List<AllSubjectInfoRespDto> retrieveAllSubjects(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        List<Subject> subjectList = subjectRepository.findAllByUser(user);

        return subjectList.stream()
                .map(AllSubjectInfoRespDto::new)
                .collect(Collectors.toList());

    }
}
