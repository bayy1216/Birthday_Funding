package team.haedal.gifticionfunding.controller.common.error;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponse(String debugMessage,
                            List<String> code) {
}
