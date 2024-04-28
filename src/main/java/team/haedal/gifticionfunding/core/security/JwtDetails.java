package team.haedal.gifticionfunding.core.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.haedal.gifticionfunding.core.jwt.JwtUser;
import team.haedal.gifticionfunding.domain.user.Role;

import java.util.Collection;
import java.util.Set;

@Getter
@Builder
public class JwtDetails implements UserDetails {
    private Long userId;
    private Role role;

    public static JwtDetails from(JwtUser jwtUser) {
        return JwtDetails.builder()
                .userId(jwtUser.getId())
                .role(jwtUser.getRole())
                .build();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(role.getAuthority());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
