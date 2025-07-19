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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public NoteCreateRespDto createNote(Long memberId, NoteCreateReqDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->{
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });
        Note note = Note.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();

        Note savedNote = noteRepository.save(note);

        return new NoteCreateRespDto(savedNote, member);
    }

    public NoteEditRespDto editNote(Long memberId, Long noteId, NoteEditReqDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->{
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Note note = noteRepository.findByIdWithUser(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        if(!memberId.equals(note.getMember().getId())){
            throw new BusinessException(ErrorCode.NO_PERMISSION_TO_EDIT);
        }

        note.modify(dto.getTitle(), dto.getContent());

        return new NoteEditRespDto(note, member);

    }

    public void deleteNote(Long memberId, NoteDeleteReqDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->{
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Note note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(()-> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        note.softDelete();
    }

    public NoteInfoRespDto retrieveNote(Long memberId, Long noteId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->{
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        Note note = noteRepository.findByIdAndNotDeleted(noteId)
                .orElseThrow(()-> new BusinessException(ErrorCode.NOTE_NOT_FOUND));

        return new NoteInfoRespDto(note, member);
    }

    public List<AllNoteInfoRespDto> retrieveAllNotes(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->{
                    log.warn("사용자를 찾을 수 없습니다. memberId={}", memberId);
                    throw new EntityNotFoundException("사용자를 찾을 수 없습니다.(memberId=" + memberId + ")");
                });

        List<Note> noteList = noteRepository
                .findAllByMemberAndDeletedAtIsNull(member);

        return noteList.stream()
                .map(AllNoteInfoRespDto::new)
                .collect(Collectors.toList());

    }
}
