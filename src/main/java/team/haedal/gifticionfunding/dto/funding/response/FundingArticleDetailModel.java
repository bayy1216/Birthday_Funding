package team.haedal.gifticionfunding.dto.funding.response;

import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;

import java.util.List;

public record FundingArticleDetailModel(
        Long id,
        String title,
        Double progress,
        UserInfoModel writer,

        // Additional
        String content,
        Integer currentMoney,
        List<GifticonModel> gifticons
) {
}
