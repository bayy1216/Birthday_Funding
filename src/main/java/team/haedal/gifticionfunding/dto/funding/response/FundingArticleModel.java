package team.haedal.gifticionfunding.dto.funding.response;

import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;

public record FundingArticleModel(
        Long id,
        String title,
        Double progress,
        UserInfoModel writer
) {
}
