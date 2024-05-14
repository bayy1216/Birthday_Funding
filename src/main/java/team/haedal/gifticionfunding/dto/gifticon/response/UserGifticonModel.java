package team.haedal.gifticionfunding.dto.gifticon.response;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.Builder;
import team.haedal.gifticionfunding.entity.gifticon.UserGifticon;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserGifticonModel(
        Long id,
        GifticonModel gifticon,
        LocalDate expirationDate,
        UsedInfoModel usedInfo
) {
    @Builder
    record UsedInfoModel(
            LocalDate usedAt,
            String code
    ){

    }

    public static UserGifticonModel from(UserGifticon userGifticon){
        UsedInfoModel usedInfo = null;
        if(userGifticon.isOpened()){
            usedInfo = UsedInfoModel.builder()
                    .usedAt(userGifticon.getUsedDate())
                    .code(userGifticon.getGiftCode())
                    .build();
        }
        return UserGifticonModel.builder()
                .id(userGifticon.getId())
                .gifticon(GifticonModel.from(userGifticon.getGifticon()))
                .expirationDate(userGifticon.getExpirationDate())
                .usedInfo(usedInfo)
                .build();
    }
}
