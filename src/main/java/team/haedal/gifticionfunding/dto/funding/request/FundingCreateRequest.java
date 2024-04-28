package team.haedal.gifticionfunding.dto.funding.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import team.haedal.gifticionfunding.domain.funding.FundingArticleCreate;

import java.time.LocalDateTime;
import java.util.List;

public record FundingCreateRequest(
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        @NotNull(message = "시작일은 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startAt,
        @NotNull(message = "종료일은 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime endAt,
        @NotBlank(message = "내용은 필수입니다.")
        String content,
        @NotNull(message = "기프티콘 ID 리스트는 필수입니다.")
        @Size(min = 1, message = "기프티콘 ID 리스트는 최소 1개 이상이어야 합니다.")
        List<Long> gifticonIds
) {
        public FundingArticleCreate toCommand() {
                return FundingArticleCreate.builder()
                        .title(title)
                        .startAt(startAt)
                        .endAt(endAt)
                        .content(content)
                        .build();
        }
}
