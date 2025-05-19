package com.project.studyplatform.controller.note.dto.response;

import com.project.studyplatform.domain.note.Note;
import com.project.studyplatform.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoteCreateRespDto {

    private Long noteId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;

    public NoteCreateRespDto(Note note, User user) {
        this.noteId = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.createdAt = note.getCreatedAt();
        this.userId = user.getId();
    }
}
