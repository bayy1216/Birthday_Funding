package team.haedal.gifticionfunding.controller.user;

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
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.dto.user.request.EmailSignupRequest;
import team.haedal.gifticionfunding.service.auth.AuthService;
import team.haedal.gifticionfunding.service.user.UserService;

@Tag(name = "유저", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Operation(summary = "이메일 회원가입", description = "성공시 토큰 반환")
    @PostMapping("/api/auth/signup-email")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse emailSignUp(@RequestBody @Valid EmailSignupRequest emailSignUpRequest) {
        var id = userService.createUser(emailSignUpRequest.toCommand());
        var token = authService.login(emailSignUpRequest.email(), emailSignUpRequest.password());
        return LoginResponse.from(token);
    }
}
