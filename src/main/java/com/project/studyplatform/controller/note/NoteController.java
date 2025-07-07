package com.project.studyplatform.controller.note;

import com.project.studyplatform.controller.note.dto.requst.NoteCreateReqDto;
import com.project.studyplatform.controller.note.dto.requst.NoteEditReqDto;
import com.project.studyplatform.controller.note.dto.response.NoteCreateRespDto;
import com.project.studyplatform.controller.note.dto.response.NoteEditRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.NoteService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public ResponseEntity<ApiResult<NoteCreateRespDto>> createNote(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody NoteCreateReqDto dto) {
        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(noteService.createNote(user.getId(), dto)));
    }

    @PutMapping("/notes/{noteId}")
    public ResponseEntity<ApiResult<NoteEditRespDto>> editNote(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long noteId, @Valid @RequestBody NoteEditReqDto dto){

        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(noteService.editNote(user.getId(), noteId, dto)));
    }
}
