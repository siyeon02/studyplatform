package com.project.studyplatform.domain.subject.repository;

import com.project.studyplatform.domain.subject.Subject;
import com.project.studyplatform.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s JOIN FETCH s.user WHERE s.id = :subjectId")
    Optional<Subject> findByIdWithUser(@Param("subjectId") Long subjectId);

    List<Subject> findAllByUser(Member member);

}
