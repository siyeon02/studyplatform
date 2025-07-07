package com.project.studyplatform.service;

import com.project.studyplatform.controller.subject.dto.request.SubjectCreateReqDto;
import com.project.studyplatform.controller.subject.dto.response.SubjectCreateRespDto;
import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.subject.repository.SubjectRepository;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.domain.user.repository.UserRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
