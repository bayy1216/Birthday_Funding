package team.haedal.gifticionfunding.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailLoginRequest(
        @Email(message = "이메일 형식이 아닙니다.")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password) {
}
