package team.haedal.gifticionfunding.domain.funding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FundingArticleCreate {
    private final String title;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final String content;
}
