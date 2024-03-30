package team.haedal.gifticionfunding.entity.user;

import jakarta.persistence.*;
import lombok.*;
import team.haedal.gifticionfunding.domain.Role;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;


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


}
