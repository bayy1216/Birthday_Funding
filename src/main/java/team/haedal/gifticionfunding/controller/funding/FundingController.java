package team.haedal.gifticionfunding.controller.funding;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.haedal.gifticionfunding.core.security.JwtDetails;
import team.haedal.gifticionfunding.dto.common.PagingRequest;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.funding.request.FundingCreateRequest;
import team.haedal.gifticionfunding.dto.funding.request.FundingJoinRequest;
import team.haedal.gifticionfunding.dto.funding.response.FundingArticleDetailModel;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.service.funding.FundingQueryService;
import team.haedal.gifticionfunding.service.funding.FundingService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FundingController {
    private final FundingService fundingService;
    private final FundingQueryService fundingQueryService;

    @GetMapping("/api/fundings")
    public PagingResponse<GifticonModel> pagingFundings(@AuthenticationPrincipal JwtDetails jwtDetails,
            @Valid PagingRequest pagingRequest
    ) {
        return fundingQueryService.pagingFundings(pagingRequest, jwtDetails.getUserId());
    }

    @GetMapping("/api/fundings/{fundingId}")
    public FundingArticleDetailModel getFundingDetail(@PathVariable Long fundingId) {
        return fundingQueryService.getFundingDetail(fundingId);
    }

    @PostMapping("/api/fundings")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createFunding(@Valid @RequestBody FundingCreateRequest request,
                              @AuthenticationPrincipal JwtDetails jwtDetails) {
        return fundingService.createFunding(request.toCommand(),request.gifticonIds() ,jwtDetails.getUserId());
    }

    @PostMapping("/api/fundings/{fundingId}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinFunding(@PathVariable Long fundingId,
                            @Valid @RequestBody FundingJoinRequest request,
                            @AuthenticationPrincipal JwtDetails jwtDetails) {
        fundingService.joinFunding(fundingId, request.gifticonIds(), jwtDetails.getUserId());

    }

}
