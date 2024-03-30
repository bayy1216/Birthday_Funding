package team.haedal.gifticionfunding.entity.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserEmailCreate {
    private final String email;
    private final String password;
    private final String nickname;
    private final String profileImageUrl;
    private final LocalDate birthdate;

    public UserEmailCreate changePassword(String bCryptPassword) {
        return UserEmailCreate.builder()
                .email(this.email)
                .password(bCryptPassword)
                .nickname(this.nickname)
                .profileImageUrl(this.profileImageUrl)
                .birthdate(this.birthdate)
                .build();
    }
}
