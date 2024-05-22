package team.haedal.gifticionfunding.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.haedal.gifticionfunding.dto.auth.request.EmailLoginRequest;
import team.haedal.gifticionfunding.dto.auth.request.OAuthLoginRequest;
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.dto.user.request.EmailSignupRequest;
import team.haedal.gifticionfunding.service.auth.AuthService;

@Tag(name = "인증", description = "인증 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "이메일 회원가입", description = "성공시 토큰과 사용자정보 반환")
    @PostMapping("/api/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse emailSignUp(@RequestBody @Valid EmailSignupRequest emailSignUpRequest) {
        Long id = authService.createUser(emailSignUpRequest.toCommand());
        return authService.login(emailSignUpRequest.email(), emailSignUpRequest.password());
    }

    @Operation(summary = "이메일 로그인", description = "성공시 토큰과 사용자정보 반환")
    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid EmailLoginRequest emailLoginRequest) {
        return authService.login(emailLoginRequest.email(), emailLoginRequest.password());
    }
    @Operation(summary = "oAuth 로그인", description = "성공시 토큰과 사용자정보 반환")
    @PostMapping("/api/auth/oauth-login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid OAuthLoginRequest oAuthLoginRequest) {
        return authService.oAuthLogin(oAuthLoginRequest.vendor(), oAuthLoginRequest.code());
    }
}
