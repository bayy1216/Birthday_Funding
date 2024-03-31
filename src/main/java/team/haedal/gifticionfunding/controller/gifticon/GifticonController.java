package team.haedal.gifticionfunding.controller.gifticon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "기프티콘", description = "기프티콘 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class GifticonController {
    private final GifticonService gifticonService;

    @Operation(summary = "기프티콘 목록 조회", description = "기프티콘 목록 페이징 조회")
    @GetMapping("/api/gifticons")
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<GifticonDto> getGifticons(@RequestParam GifticonsQuery query) {
        return gifticonService.getGifticons(query.toPageRequest(), query.toSearch());
    }

    @Operation(summary = "기프티콘 상세 조회", description = "기프티콘 상세 조회")
    @GetMapping("/api/gifticons/{gifticonId}")
    @ResponseStatus(HttpStatus.OK)
    public GifticonDetailDto getGifticon(@PathVariable Long gifticonId) {
        return gifticonService.getGifticon(gifticonId);
    }

    @Operation(summary = "기프티콘 생성", description = "기프티콘 생성, 성공시 생성된 기프티콘의 id 반환")
    @PostMapping("/api/gifticons")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createGifticon(@RequestBody @Valid GifticonCreateRequest request) {
        return gifticonService.createGifticon(request.toCommand());
    }

    @Operation(summary = "기프티콘 수정", description = "기프티콘 수정")
    @PutMapping("/api/gifticons/{gifticonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGifticon(@PathVariable Long gifticonId, @RequestBody @Valid GifticonUpdateRequest request) {
        gifticonService.updateGifticon(gifticonId, request.toCommand());
    }

    @Operation(summary = "기프티콘 삭제", description = "기프티콘 삭제")
    @DeleteMapping("/api/gifticons/{gifticonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGifticon(@PathVariable Long gifticonId) {
        gifticonService.deleteGifticon(gifticonId);
    }
}
