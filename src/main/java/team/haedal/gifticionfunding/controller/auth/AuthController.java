package team.haedal.gifticionfunding.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.haedal.gifticionfunding.dto.auth.request.EmailLoginRequest;
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.service.auth.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody @Valid EmailLoginRequest emailLoginRequest) {
        var token = authService.login(emailLoginRequest.email(), emailLoginRequest.password());
        return LoginResponse.from(token);
    }
}
