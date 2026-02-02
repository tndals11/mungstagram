package com.example.mungstragram.user;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.auth.AuthRequest;
import com.example.mungstragram._common.enums.user.Status;
import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(UserRequest.CreateDTO createDTO) {

        if (userRepository.existsByUsername(createDTO.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        if (userRepository.existsByNickname(createDTO.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        User userEntity = createDTO.toEntity();
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

    public void login(AuthRequest.LoginDTO loginDTO, HttpSession session){
        User userEntity = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!userEntity.getPassword().equals(loginDTO.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        session.setAttribute("sessionUser", userEntity);
    }

    public UserResponse.DetailDTO getByIdUser(Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponse.DetailDTO(userEntity);
    }
}
