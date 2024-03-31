package team.haedal.gifticionfunding.dto.common;

import lombok.Builder;

import java.util.List;

@Builder
public record PagingResponse<T>(boolean hasNext, List<T> data) {
}
