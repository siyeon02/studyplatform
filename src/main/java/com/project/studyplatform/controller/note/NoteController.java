package com.project.studyplatform.controller.note;

import com.project.studyplatform.controller.note.dto.requst.NoteCreateReqDto;
import com.project.studyplatform.controller.note.dto.requst.NoteDeleteReqDto;
import com.project.studyplatform.controller.note.dto.requst.NoteEditReqDto;
import com.project.studyplatform.controller.note.dto.response.AllNoteInfoRespDto;
import com.project.studyplatform.controller.note.dto.response.NoteCreateRespDto;
import com.project.studyplatform.controller.note.dto.response.NoteEditRespDto;
import com.project.studyplatform.controller.note.dto.response.NoteInfoRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.NoteService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResult<NoteEditRespDto>> editNote(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long noteId, @Valid @RequestBody NoteEditReqDto dto) {

        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(noteService.editNote(user.getId(), noteId, dto)));
    }

    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<ApiResult<Void>> deleteNote(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long noteId, @Valid @RequestBody NoteDeleteReqDto dto){
        User user = userDetails.getUser();
        noteService.deleteNote(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(null));
    }

    @GetMapping("/notes/{noteId}")
    public ResponseEntity<ApiResult<NoteInfoRespDto>> retrieveNote(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long noteId) {
        User user = userDetails.getUser();
        return ResponseEntity.status((HttpStatus.OK)).body(ApiResult.success(noteService.retrieveNote(user.getId(), noteId)));
    }

    @GetMapping("/notes/all")
    public ResponseEntity<ApiResult<List<AllNoteInfoRespDto>>> retrieveAllNotes(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        List<AllNoteInfoRespDto> noteList =  noteService.retrieveAllNotes(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(noteList));
    }
}
