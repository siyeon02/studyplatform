package com.project.studyplatform.domain.subject.repository;

import com.project.studyplatform.domain.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
