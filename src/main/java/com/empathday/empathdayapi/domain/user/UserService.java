package com.empathday.empathdayapi.domain.user;

import com.empathday.empathdayapi.common.response.ErrorCode;
import com.empathday.empathdayapi.domain.user.exception.UserNotFoundException;
import com.empathday.empathdayapi.infrastructure.user.UserRepository;
import com.empathday.empathdayapi.interfaces.user.UserSignUpDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(UserSignUpDto userSignUpDto) throws Exception {
        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);

        return user;
    }

    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException(ErrorCode.USER_ENTITY_NOT_FOUND)
        );
    }
}


