package com.project.studyplatform.domain.timer.repository;

import com.project.studyplatform.domain.timer.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, Long> {
}
