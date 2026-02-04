package com.example.mungstragram.user;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram._common.security.JwtProvider;
import com.example.mungstragram.auth.AuthRequest;
import com.example.mungstragram._common.enums.user.Status;
import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram.role.Role;
import com.example.mungstragram.role.RoleRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void createUser(UserRequest.CreateDTO createDTO) {

        if (userRepository.existsByUsername(createDTO.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        if (userRepository.existsByNickname(createDTO.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        User userEntity = createDTO.toEntity(passwordEncoder);

        Role userRole = roleRepository.findByName("USER").
                orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND) );

        userEntity.addRole(userRole);

        userRepository.save(userEntity);
    }

    @Transactional
    public void updateUser(UserRequest.UpdateDTO updateDTO, Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (userRepository.existsByNickname(updateDTO.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        userEntity.update(updateDTO.getNickname());
    }

    @Transactional
    public void withdrawUser(Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (userEntity.getStatus() == Status.WITHDRAWN) {
            throw new CustomException(ErrorCode.USER_WITHDRAWN);
        }

        userEntity.withdraw();
    }

    public String login(AuthRequest.LoginDTO loginDTO){
        User userEntity = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        return jwtProvider.create(userEntity);
    }

    public UserResponse.DetailDTO getByIdUser(Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponse.DetailDTO(userEntity);
    }
}
