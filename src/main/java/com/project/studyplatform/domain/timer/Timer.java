package com.project.studyplatform.domain.timer;

import com.project.studyplatform.domain.BaseEntity;
import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "timers")
@Getter
@NoArgsConstructor
public class Timer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId")
    private Subject subject;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Enumerated(value = EnumType.STRING)
    TimerStatus timerStatus;

    @Builder
    public Timer(Subject subject, LocalDateTime startTime, LocalDateTime endTime, TimerStatus timerStatus) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timerStatus = timerStatus;
    }

    public void endTimer(LocalDateTime endTime){
        this.endTime = endTime;
        this.timerStatus = TimerStatus.END;
    }

}
