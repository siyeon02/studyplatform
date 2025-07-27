package com.project.studyplatform.controller.timer.dto.response;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.timer.Timer;
import com.project.studyplatform.domain.timer.TimerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EndTimeRespDto {
    private Long timerId;
    private Long subjectId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TimerStatus timerStatus;

    public EndTimeRespDto(Timer timer, Subject subject) {
        this.timerId = timer.getId();
        this.subjectId = subject.getId();
        this.startTime = timer.getStartTime();
        this.endTime = timer.getEndTime();
        this.timerStatus = timer.getTimerStatus();
    }
}
