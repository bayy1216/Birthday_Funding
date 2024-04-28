package team.haedal.gifticionfunding.dto.funding.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FundingJoinRequest(
        @NotNull(message = "기프티콘 ID 리스트는 필수입니다.")
        @Size(min = 1, message = "기프티콘 ID 리스트는 최소 1개 이상이어야 합니다.")
        List<Long> gifticonIds
) {
}
