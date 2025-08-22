package com.project.studyplatform.service;

import com.project.studyplatform.controller.member.dto.request.SignupReqDto;
import com.project.studyplatform.controller.member.dto.response.SignupRespDto;
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
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Transactional
    public SignupRespDto signup(SignupReqDto dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXIST);
        }

        String encodePassword = passwordEncoder.encode(dto.getPassword());

        Member member = Member.builder()
                .name(dto.getName())
                .nickname(dto.getNickname())
                .password(encodePassword)
                .email(dto.getEmail())
                .status(dto.getStatus())
                .build();

        Member savedMember = memberRepository.save(member);
        String accessToken = jwtUtil.createToken(savedMember.getId(), savedMember.getEmail());

        return new SignupRespDto(savedMember, accessToken);
    }
}
