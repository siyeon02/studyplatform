package com.project.studyplatform.domain.note.repository;

import com.project.studyplatform.domain.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
