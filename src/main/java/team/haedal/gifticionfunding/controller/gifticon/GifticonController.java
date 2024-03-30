package team.haedal.gifticionfunding.controller.gifticon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import team.haedal.gifticionfunding.service.gifticon.GifticonService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GifticonController {
    private final GifticonService gifticonService;
}
