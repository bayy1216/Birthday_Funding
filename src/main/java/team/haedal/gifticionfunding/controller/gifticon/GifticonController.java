package team.haedal.gifticionfunding.controller.gifticon;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.gifticon.request.GifticonsQuery;
import team.haedal.gifticionfunding.dto.gifticon.request.GifticonCreateRequest;
import team.haedal.gifticionfunding.dto.gifticon.request.GifticonUpdateRequest;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonDetailDto;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonDto;
import team.haedal.gifticionfunding.service.gifticon.GifticonService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GifticonController {
    private final GifticonService gifticonService;

    @GetMapping("/api/gifticons")
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<GifticonDto> getGifticons(@RequestParam GifticonsQuery query) {
        return gifticonService.getGifticons(query.toPageRequest(), query.toSearch());
    }

    @GetMapping("/api/gifticons/{gifticonId}")
    @ResponseStatus(HttpStatus.OK)
    public GifticonDetailDto getGifticon(@PathVariable Long gifticonId) {
        return gifticonService.getGifticon(gifticonId);
    }


    @PostMapping("/api/gifticons")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createGifticon(@RequestBody @Valid GifticonCreateRequest request) {
        return gifticonService.createGifticon(request.toCommand());
    }

    @PutMapping("/api/gifticons/{gifticonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGifticon(@PathVariable Long gifticonId, @RequestBody @Valid GifticonUpdateRequest request) {
        gifticonService.updateGifticon(gifticonId, request.toCommand());
    }

    @DeleteMapping("/api/gifticons/{gifticonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGifticon(@PathVariable Long gifticonId) {
        gifticonService.deleteGifticon(gifticonId);
    }
}
