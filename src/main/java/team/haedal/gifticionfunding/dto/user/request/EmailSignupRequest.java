package team.haedal.gifticionfunding.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.haedal.gifticionfunding.domain.user.UserEmailCreate;

import java.time.LocalDate;

public record EmailSignupRequest(
        @Email(message = "이메일 형식이 아닙니다.")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,
        String profileImageUrl,
        @NotNull(message = "생년월일을 입력해주세요.")
        LocalDate birthdate) {
    public UserEmailCreate toCommand() {
        return UserEmailCreate.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .birthdate(birthdate)
                .build();
    }
}
