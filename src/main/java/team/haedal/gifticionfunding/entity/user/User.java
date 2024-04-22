package team.haedal.gifticionfunding.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team.haedal.gifticionfunding.domain.Role;
import team.haedal.gifticionfunding.domain.Vendor;
import team.haedal.gifticionfunding.domain.VendorUserInfo;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity implements UserDetails, OAuth2User {
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

    @Column(nullable = false, updatable = false)
    private Vendor vendor;

    @Column(nullable = false, updatable = false)
    private String vendorEmail;

    @Builder
    private User(String email, String password, String nickname, Integer point, LocalDate birthdate, String profileImageUrl, Role role, Vendor vendor, String vendorEmail) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.point = point;
        this.birthdate = birthdate;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.vendor = vendor;
        this.vendorEmail = vendorEmail;
    }


    public static User from(UserEmailCreate userEmailCreate) {
        return User.builder()
                .email(userEmailCreate.getEmail())
                .password(userEmailCreate.getPassword())
                .nickname(userEmailCreate.getNickname())
                .birthdate(userEmailCreate.getBirthdate())
                .profileImageUrl(userEmailCreate.getProfileImageUrl())
                .role(Role.ROLE_USER)
                .point(0)
                .vendor(null)
                .vendorEmail(null)
                .build();
    }

    public static User create(VendorUserInfo vendorUserInfo) {
        String email = vendorUserInfo.getVendorEmail() + "@" + vendorUserInfo.getVendor().toString();
        return User.builder()
                .email(email)
                .password(null)
                .nickname(null)
                .birthdate(null)
                .profileImageUrl(null)
                .role(Role.ROLE_USER)
                .point(0)
                .vendor(vendorUserInfo.getVendor())
                .vendorEmail(vendorUserInfo.getVendorEmail())
                .build();
    }


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(role.getAuthority());
    }

    @Override
    public String getPassword() {
        return password;
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


    @Override
    public String getName() {
        return email;
    }
}
