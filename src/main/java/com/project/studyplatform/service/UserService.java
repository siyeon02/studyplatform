package com.project.studyplatform.service;

import com.project.studyplatform.controller.user.dto.request.SignupReqDto;
import com.project.studyplatform.controller.user.dto.request.UserDeleteReqDto;
import com.project.studyplatform.controller.user.dto.request.UserProfileEditReqDto;
import com.project.studyplatform.controller.user.dto.response.SignupRespDto;
import com.project.studyplatform.controller.user.dto.response.UserProfileRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.domain.user.repository.UserRepository;
import com.project.studyplatform.ex.BusinessException;
import com.project.studyplatform.ex.ErrorCode;
import com.project.studyplatform.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserProfileRespDto findUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserProfileRespDto(user);
    }

    @Transactional
    public void userProfileEdit(Long userId, UserProfileEditReqDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.editProfile(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getNickname(), dto.getStatus());
        userRepository.save(user);

    }

    public void deleteUser(Long userId, UserDeleteReqDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCHING);
        }

        userRepository.delete(user);

    }
}
