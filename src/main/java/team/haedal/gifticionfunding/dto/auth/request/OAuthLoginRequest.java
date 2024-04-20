package team.haedal.gifticionfunding.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import team.haedal.gifticionfunding.domain.Vendor;

public record OAuthLoginRequest(
        @NotBlank(message = "vendor를 입력해주세요.")
        Vendor vendor,
        @NotBlank(message = "code를 입력해주세요.")
        String code) {
}
