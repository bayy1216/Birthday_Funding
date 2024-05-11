package team.haedal.gifticionfunding.dto.funding.response;

import lombok.Builder;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;

import java.time.LocalDateTime;

@Builder
public record FundingArticleModel(
        Long id,
        String title,
        Double progress,
        UserInfoModel writer,
        LocalDateTime startAt,
        LocalDateTime endAt,
        LocalDateTime createdAt
) {

	public static FundingArticleModel from(FundingArticle fundingArticle) {
		return FundingArticleModel.builder()
				.id(fundingArticle.getId())
				.title(fundingArticle.getTitle())
				.writer(UserInfoModel.from(fundingArticle.getUser()))
				.createdAt(fundingArticle.getCreatedAt())
				.startAt(fundingArticle.getStartAt())
				.endAt(fundingArticle.getEndAt())
				.build();
	}
}
