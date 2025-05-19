package com.project.studyplatform.service;

import com.project.studyplatform.controller.note.dto.requst.NoteCreateReqDto;
import com.project.studyplatform.controller.note.dto.response.NoteCreateRespDto;
import com.project.studyplatform.domain.note.Note;
import com.project.studyplatform.domain.note.repository.NoteRepository;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.domain.user.repository.UserRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .build();

        Note savedNote = noteRepository.save(note);

        return new NoteCreateRespDto(savedNote, user);
    }
}
