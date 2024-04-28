package team.haedal.gifticionfunding.dto.funding.response;

import lombok.Builder;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.funding.FundingArticleGifticon;
import team.haedal.gifticionfunding.entity.funding.FundingContribute;

import java.util.List;

@Builder
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
    /**
     * FundingArticle, FundingContribute, FundingArticleGifticon을 이용하여 FundingArticleDetailModel을 생성합니다.
     * fetchJoin이 필요한 사항은 다음과 같습니다.
     * - FundingArticle.user
     * - FundingArticleGifticon.gifticon
     * - FundingContribute.userGifticon.gifticon
     */
    public static FundingArticleDetailModel from(
            FundingArticle fundingArticle,
            List<FundingContribute> fundingContributes,
            List<FundingArticleGifticon> fundingArticleGifticons
    ){
        int currentMoney = fundingContributes.stream()
                .mapToInt(FundingContribute::getPrice)
                .sum();
        int totalMoney = fundingArticleGifticons.stream()
                .mapToInt(FundingArticleGifticon::getPrice)
                .sum();

        return FundingArticleDetailModel.builder()
                .id(fundingArticle.getId())
                .title(fundingArticle.getTitle())
                .writer(UserInfoModel.from(fundingArticle.getUser()))
                .content(fundingArticle.getContent())
                .currentMoney(currentMoney)
                .progress((double) currentMoney / totalMoney)
                .gifticons(fundingArticleGifticons.stream()
                        .map(FundingArticleGifticon::getGifticon)
                        .map(GifticonModel::from)
                        .toList())
                .build();

    }
}
