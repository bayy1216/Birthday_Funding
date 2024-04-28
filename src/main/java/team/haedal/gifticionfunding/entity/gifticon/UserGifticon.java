package team.haedal.gifticionfunding.entity.gifticon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;
import team.haedal.gifticionfunding.entity.user.User;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserGifticon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gifticon_id")
    private Gifticon gifticon;

    @Column(nullable = false)
    private LocalDate expirationDate;

    private LocalDate usedDate;

    @Column(length = 16)
    private String giftCode;


    /**
     * 펀딩게시글에 기부할 수 있는지 확인
     */
    public boolean canContribute(User owner){
        if(!owner.getId().equals(this.owner.getId())){
            return false;
        }
        if(isOpened()){
            return false;
        }
        if(expirationDate.isBefore(LocalDate.now())){
            return false;
        }
        return true;
    }


    public void changeOwner(User beforeOwner, User afterOwner){
        if(!beforeOwner.getId().equals(this.owner.getId())){
            throw new IllegalArgumentException("변경하려는 소유자가 현재 소유자와 일치하지 않습니다.");
        }
        this.owner = afterOwner;
    }

    public boolean isOpened(){
        return gifticon != null;
    }

    public int getPrice(){
        return gifticon.getPrice();
    }
}
