package team.haedal.gifticionfunding.dto.funding.response;

import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;

import java.time.LocalDateTime;

public record FundingArticleModel(
        Long id,
        String title,
        Double progress,
        UserInfoModel writer,
        LocalDateTime startAt,
        LocalDateTime endAt,
        LocalDateTime createdAt
) {
}
