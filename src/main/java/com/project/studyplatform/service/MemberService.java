package com.project.studyplatform.service;

import com.project.studyplatform.controller.member.dto.request.MemberDeleteReqDto;
import com.project.studyplatform.controller.member.dto.request.MemberProfileEditReqDto;
import com.project.studyplatform.controller.member.dto.response.MemberProfileRespDto;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.repository.MemberRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import com.project.studyplatform.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public MemberProfileRespDto findUserProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new MemberProfileRespDto(member);
    }

    @Transactional
    public void userProfileEdit(Long userId, MemberProfileEditReqDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        member.editProfile(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getNickname(), dto.getStatus());
        memberRepository.save(member);

    }

    public void deleteUser(Long userId, MemberDeleteReqDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCHING);
        }

        memberRepository.delete(member);

    }
}
