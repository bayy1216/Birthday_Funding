package team.haedal.gifticionfunding.dto.common;

import java.util.List;

public record PagingResponse<T>(boolean hasNext, List<T> data) {
}
