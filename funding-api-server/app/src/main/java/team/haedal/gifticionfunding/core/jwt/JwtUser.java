package team.haedal.gifticionfunding.core.jwt;

import lombok.Builder;
import lombok.Getter;
import team.haedal.gifticionfunding.domain.user.Role;

@Builder
@Getter
public class JwtUser {
    private Long id;
    private Role role;

    public static JwtUser of(Long id, Role role) {
        return JwtUser.builder()
                .id(id)
                .role(role)
                .build();
    }
}
