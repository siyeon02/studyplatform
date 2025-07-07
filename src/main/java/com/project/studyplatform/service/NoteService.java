package com.project.studyplatform.service;

import com.project.studyplatform.controller.note.dto.requst.NoteCreateReqDto;
import com.project.studyplatform.controller.note.dto.requst.NoteDeleteReqDto;
import com.project.studyplatform.controller.note.dto.requst.NoteEditReqDto;
import com.project.studyplatform.controller.note.dto.response.AllNoteInfoRespDto;
import com.project.studyplatform.controller.note.dto.response.NoteCreateRespDto;
import com.project.studyplatform.controller.note.dto.response.NoteEditRespDto;
import com.project.studyplatform.controller.note.dto.response.NoteInfoRespDto;
import com.project.studyplatform.domain.note.Note;
import com.project.studyplatform.domain.note.repository.NoteRepository;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.domain.user.repository.UserRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Transactional
    public NoteCreateRespDto createNote(Long userId, NoteCreateReqDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Note note = Note.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        Note savedNote = noteRepository.save(note);

        return new NoteCreateRespDto(savedNote, user);
    }

    public NoteEditRespDto editNote(Long userId, Long noteId, NoteEditReqDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Note note = noteRepository.findByIdWithUser(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        if(!userId.equals(note.getUser().getId())){
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        note.modify(dto.getTitle(), dto.getContent());

        return new NoteEditRespDto(note,user);

    }

    public void deleteNote(Long userId, NoteDeleteReqDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Note note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(()-> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        noteRepository.delete(note);
    }

    public NoteInfoRespDto retrieveNote(Long userId, Long noteId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException((ErrorCode.USER_NOT_FOUND)));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(()-> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        return new NoteInfoRespDto(note, user);
    }

    public List<AllNoteInfoRespDto> retrieveAllNotes(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException((ErrorCode.USER_NOT_FOUND)));

        List<Note> noteList = noteRepository
                .findAllByUser(user);

        return noteList.stream()
                .map(AllNoteInfoRespDto::new)
                .collect(Collectors.toList());

    }
}
