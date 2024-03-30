package team.haedal.gifticionfunding.controller.funding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import team.haedal.gifticionfunding.service.funding.FundingService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FundingController {
    private final FundingService fundingService;
}
