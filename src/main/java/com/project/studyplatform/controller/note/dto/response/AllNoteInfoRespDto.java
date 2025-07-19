package com.project.studyplatform.controller.note.dto.response;

import com.project.studyplatform.domain.note.Note;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AllNoteInfoRespDto {
    private Long noteId;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public AllNoteInfoRespDto(Note note) {
        this.noteId = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.createAt = note.getCreatedAt();
        this.modifiedAt = note.getModifiedAt();
    }
}
