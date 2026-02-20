package com.example.mungstragram._common.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram.role.Role;
import com.example.mungstragram.user.CustomUserDetails;
import com.example.mungstragram.user.User;
import com.example.mungstragram.user_role.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [문지기 필터]
 * 모든 요청 (OncePerRequestFilter)마다 헤더 토큰을 확인
 * */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 Authorization 키의 값 추출
        String prefixJwt = request.getHeader("Authorization");

        // 2. 토큰이 없거나 "Bearer "로 시작하지 않는다면 그냥 통과
        if (prefixJwt == null || !prefixJwt.startsWith(JwtProvider.PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            DecodedJWT decodedJWT = jwtProvider.verify(prefixJwt);

            // 토큰 내부에 숨겨둔 유저 id를 꺼낸다.
            Long id = decodedJWT.getClaim("id").asLong();

            // 토큰 내부에 숨겨둔 이름을 꺼낸다
            String username = decodedJWT.getSubject();

            // 토큰 내부에 숨겨둔 권한을 꺼낸다
            String roles = decodedJWT.getClaim("role").asString();

            // 꺼내온 권한을 ,을 기준으로 잘라서 처리한다 예) ROLE_USER,ROLE_ADMIN
            List<UserRole> userRoles = Arrays.stream(
                            roles.replace("[", "").replace("]", "").split(","))
                    .map(String::trim)
                    .map(roleName -> {
                        // "ROLE_USER" -> "USER"로 변환
                        String cleanRoleName = roleName.replace("ROLE_", "");

                        Role role = Role.builder()
                                .name(cleanRoleName) // "USER"
                                .build();

                        return UserRole.builder()
                                .role(role)
                                .build();
                    })
                    .toList();

            // 가짜 사용자를 만들어준다
            User user = User.forAuthentication(id, username, userRoles);

            // 내가 만들어둔 사용자 도시락 포장지에 넣어준다
            CustomUserDetails userDetails = new CustomUserDetails(user);

            // 강제로 로그인 인증 처리를 한다
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            // 시큐리티 바구니에 인증 정보를 담아준다
            // 이렇게 담아줘야 @AuthenticationPrincipal로 유저를 꺼낼 수 있다
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (TokenExpiredException e) {
            setErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
        } catch (JWTVerificationException e) {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().intValue());
        response.setContentType("application/json;charset=UTF-8");

        String json = new ObjectMapper().writeValueAsString(Response.fail(errorCode, errorCode.getMessage()));

        response.getWriter().write(json);
    }

}
