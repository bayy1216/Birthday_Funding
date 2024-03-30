package team.haedal.gifticionfunding.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.haedal.gifticionfunding.domain.Role;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private LocalDate birthdate;

    private String profileImageUrl;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


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
        return email; // 사용자의 id를 반환(고유값)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았는지 리턴(true: 만료안됨)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았는지 리턴(true: 잠기지 않음)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 패스워드가 만료되지 않았는지 리턴(true: 만료안됨)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화(사용가능)인지 리턴(true: 활성화)
    }

}
