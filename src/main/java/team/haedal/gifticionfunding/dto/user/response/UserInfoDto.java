package team.haedal.gifticionfunding.dto.user.response;

import lombok.Builder;
import team.haedal.gifticionfunding.entity.user.User;

import java.time.LocalDate;

@Builder
public record UserInfoDto(
        Long id,
        String nickname,
        String profileImageUrl,
        LocalDate birthdate
) {
    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .birthdate(user.getBirthdate())
                .build();
    }
}
