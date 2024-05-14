package team.haedal.gifticionfunding.dto.funding.response;

import lombok.Builder;
import team.haedal.gifticionfunding.dto.gifticon.response.UserGifticonModel;

import java.util.List;

@Builder
public record CloseFundingResponse(
        Long point,
        List<UserGifticonModel> userGifticons
) {
    public static CloseFundingResponse of(Long point, List<UserGifticonModel> userGifticons) {
        return CloseFundingResponse.builder()
                .point(point)
                .userGifticons(userGifticons)
                .build();
    }
}
