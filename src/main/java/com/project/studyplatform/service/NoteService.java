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
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    public NoteCreateRespDto createNote(Long userId, NoteCreateReqDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Note note = Note.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();

        Note savedNote = noteRepository.save(note);

        return new NoteCreateRespDto(savedNote, member);
    }

    public NoteEditRespDto editNote(Long userId, Long noteId, NoteEditReqDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Note note = noteRepository.findByIdWithUser(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        if(!userId.equals(note.getMember().getId())){
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        note.modify(dto.getTitle(), dto.getContent());

        return new NoteEditRespDto(note, member);

    }

    public void deleteNote(Long userId, NoteDeleteReqDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Note note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(()-> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        noteRepository.delete(note);
    }

    public NoteInfoRespDto retrieveNote(Long userId, Long noteId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException((ErrorCode.USER_NOT_FOUND)));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(()-> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        return new NoteInfoRespDto(note, member);
    }

    public List<AllNoteInfoRespDto> retrieveAllNotes(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BusinessException((ErrorCode.USER_NOT_FOUND)));

        List<Note> noteList = noteRepository
                .findAllByUser(member);

        return noteList.stream()
                .map(AllNoteInfoRespDto::new)
                .collect(Collectors.toList());

    }
}
