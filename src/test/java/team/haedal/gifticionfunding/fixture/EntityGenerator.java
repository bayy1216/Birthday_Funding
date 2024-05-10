package team.haedal.gifticionfunding.fixture;

import team.haedal.gifticionfunding.domain.user.Role;
import team.haedal.gifticionfunding.domain.user.Vendor;
import team.haedal.gifticionfunding.entity.user.User;

import java.time.LocalDate;

public abstract class EntityGenerator {

    protected User createUser(String email, String nickname) {
        return User.builder()
                .email(email)
                .password("{noop}password")
                .nickname(nickname)
                .birthdate(LocalDate.of(1990, 1, 1))
                .profileImageUrl("")
                .vendor(Vendor.KAKAO)
                .vendorEmail("vendorEmail")
                .point(0)
                .role(Role.ROLE_USER)
                .build();
    }
    protected User createUser(String email, String nickname, Integer point, LocalDate birthdate, String profileImageUrl) {
        return User.builder()
                .email(email==null ? "user1@example.com" : email)
                .password("{noop}password")
                .nickname(nickname==null ? "" : nickname)
                .birthdate(birthdate==null ? LocalDate.of(1990, 1, 1) : birthdate)
                .profileImageUrl(profileImageUrl==null ? "" : profileImageUrl)
                .role(Role.ROLE_USER)
                .build();
    }
}
