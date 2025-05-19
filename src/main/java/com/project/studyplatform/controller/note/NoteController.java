package com.project.studyplatform.controller.note;

import com.project.studyplatform.controller.note.dto.requst.NoteCreateReqDto;
import com.project.studyplatform.controller.note.dto.response.NoteCreateRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.service.NoteService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public ResponseEntity<ApiResult<NoteCreateRespDto>> createNote(@AuthenticationPrincipal(expression = "user") User user, @Valid @RequestBody NoteCreateReqDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(noteService.createNote(user.getId(), dto)));
    }
}
