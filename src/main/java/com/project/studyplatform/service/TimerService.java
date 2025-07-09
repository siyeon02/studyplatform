package com.project.studyplatform.service;

import com.project.studyplatform.controller.timer.dto.request.EndTimeReqDto;
import com.project.studyplatform.controller.timer.dto.request.StartTimeReqDto;
import com.project.studyplatform.controller.timer.dto.request.TimerInfoReqDto;
import com.project.studyplatform.controller.timer.dto.response.EndTimeRespDto;
import com.project.studyplatform.controller.timer.dto.response.StartTimeRespDto;
import com.project.studyplatform.controller.timer.dto.response.TimerInfoRespDto;
import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.subject.repository.SubjectRepository;
import com.project.studyplatform.domain.timer.Timer;
import com.project.studyplatform.domain.timer.TimerStatus;
import com.project.studyplatform.domain.timer.repository.TimerRepository;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.domain.user.repository.UserRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimerService {
    private final TimerRepository timerRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public StartTimeRespDto startTimer(Long userId, Long subjectId, StartTimeReqDto reqDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        Timer timer = Timer.builder()
                .subject(subject)
                .startTime(LocalDateTime.now())
                .timerStatus(TimerStatus.IN_PROGRESS)
                .build();

        Timer savedTimer = timerRepository.save(timer);

        return new StartTimeRespDto(savedTimer, subject);

    }

    public EndTimeRespDto endTimer(Long userId, Long subjectId, EndTimeReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SUBJECT_NOT_FOUND));

        Timer timer = timerRepository.findBySubjectIdAndTimerStatus(subjectId, TimerStatus.IN_PROGRESS)
                .orElseThrow(() -> new BusinessException(ErrorCode.TIMER_NOT_FOUND));

        timer.endTimer(LocalDateTime.now());

        Timer savedTimer = timerRepository.save(timer);

        return new EndTimeRespDto(savedTimer, subject);

    }

    public TimerInfoRespDto retrieveTimer(Long userId, Long subjectId, TimerInfoReqDto reqDto) {
        Timer timer = timerRepository.findTopBySubjectIdOrderByStartTimeDesc(subjectId)
                .orElseThrow(()-> new BusinessException(ErrorCode.TIMER_NOT_FOUND));

        return new TimerInfoRespDto(timer);

    }
}
