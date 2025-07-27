package com.project.studyplatform.domain.timer.repository;

import com.project.studyplatform.domain.timer.Timer;
import com.project.studyplatform.domain.timer.TimerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimerRepository extends JpaRepository<Timer, Long> {
    Optional<Timer> findBySubjectIdAndTimerStatus(Long subjectId, TimerStatus timerStatus);

    Optional<Timer> findTopBySubjectIdOrderByStartTimeDesc(Long subjectId);
}
