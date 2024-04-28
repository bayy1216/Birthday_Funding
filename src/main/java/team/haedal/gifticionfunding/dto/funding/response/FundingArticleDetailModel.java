package team.haedal.gifticionfunding.dto.funding.response;

import lombok.Builder;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.funding.FundingArticleGifticon;
import team.haedal.gifticionfunding.entity.funding.FundingContribute;
import team.haedal.gifticionfunding.entity.gifticon.UserGifticon;

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
    public static FundingArticleDetailModel from(FundingArticle fundingArticle){
        int currentMoney = fundingArticle.getCurrentMoney();
        int totalMoney = fundingArticle.getTotalMoney();

        return FundingArticleDetailModel.builder()
                .id(fundingArticle.getId())
                .title(fundingArticle.getTitle())
                .writer(UserInfoModel.from(fundingArticle.getUser()))
                .content(fundingArticle.getContent())
                .currentMoney(currentMoney)
                .progress((double) currentMoney / totalMoney)
                .gifticons(fundingArticle.getFundingContributes().stream()
                        .map(FundingContribute::getUserGifticon)
                        .map(UserGifticon::getGifticon)
                        .map(GifticonModel::from)
                        .toList())
                .build();

    }
}
