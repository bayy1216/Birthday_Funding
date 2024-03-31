package team.haedal.gifticionfunding.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.haedal.gifticionfunding.controller.common.annotation.LoginUserId;
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.dto.user.request.EmailSignupRequest;
import team.haedal.gifticionfunding.dto.user.response.UserInfoDto;
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
    @PostMapping("/api/user/signup-email")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse emailSignUp(@RequestBody @Valid EmailSignupRequest emailSignUpRequest) {
        var id = userService.createUser(emailSignUpRequest.toCommand());
        var token = authService.login(emailSignUpRequest.email(), emailSignUpRequest.password());
        return LoginResponse.from(token);
    }


    @Operation(summary = "내 정보 조회", description = "성공시 유저 정보 반환")
    @GetMapping("/api/user/me")
    public UserInfoDto getUserInfo(@LoginUserId Long userId) {
        return userService.getUserInfo(userId);
    }
}
