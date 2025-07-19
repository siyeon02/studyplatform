package com.project.studyplatform.domain.note.repository;

import com.project.studyplatform.domain.note.Note;
import com.project.studyplatform.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("SELECT n FROM Note n JOIN FETCH n.user WHERE n.id = :noteId")
    Optional<Note> findByIdWithUser(@Param("noteId") Long noteId);

    List<Note> findAllByUser(Member member);
}
