package com.project.studyplatform.security.service;

import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.repository.MemberRepository;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 찾을 수 없습니다"));
        return new UserDetailsImpl(member);
    }
}
