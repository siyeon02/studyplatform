package com.project.studyplatform.controller.timer.dto.response;

import com.project.studyplatform.domain.timer.Timer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TimerInfoRespDto {
    private Long timerId;
    private Long subjectId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String duration;

    public TimerInfoRespDto(Timer timer){
        this.timerId = timer.getId();
        this.subjectId = timer.getSubject().getId();
        this.startTime = timer.getStartTime();
        this.endTime = timer.getEndTime();
        this.duration = calculateDuration(timer.getStartTime(), timer.getEndTime());
    }

    private String calculateDuration(LocalDateTime startTime, LocalDateTime endTime){
        if(startTime ==null || endTime ==null){
            return "before start timer";
        }
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        long hours = seconds/3600;
        long minutes = (seconds%3600)%60;
        long remainingSeconds = seconds%60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

}
