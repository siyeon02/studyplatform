package com.project.studyplatform.controller.note.dto.response;

import com.project.studyplatform.domain.note.Note;
import com.project.studyplatform.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoteInfoRespDto {
    private Long noteId;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public NoteInfoRespDto(Note note, Member member){
        this.noteId = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.userId = member.getId();
        this.createdAt = note.getCreatedAt();
        this.modifiedAt = note.getModifiedAt();
    }
}
