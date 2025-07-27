package com.project.studyplatform.controller.timer.dto.response;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.timer.Timer;
import com.project.studyplatform.domain.timer.TimerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StartTimeRespDto {
    private Long timerId;
    private Long subjectId;
    private LocalDateTime startTime;
    private TimerStatus timerStatus;

    public StartTimeRespDto(Timer timer, Subject subject) {
        this.timerId = timer.getId();
        this.subjectId = subject.getId();
        this.startTime = timer.getStartTime();
        this.timerStatus = timer.getTimerStatus();
    }
}
