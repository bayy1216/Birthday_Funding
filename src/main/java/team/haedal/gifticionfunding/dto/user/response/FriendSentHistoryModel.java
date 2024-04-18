package team.haedal.gifticionfunding.dto.user.response;

import lombok.Builder;
import team.haedal.gifticionfunding.entity.user.FriendshipAction;

import java.time.LocalDateTime;

@Builder
public record FriendSentHistoryModel(
        Long id,
        UserInfoModel user,
        LocalDateTime createdAt
) {
    public static FriendSentHistoryModel from(FriendshipAction action) {
        var user = action.getToUser();
        return FriendSentHistoryModel.builder()
                .id(action.getId())
                .user(UserInfoModel.from(user))
                .createdAt(action.getCreatedAt())
                .build();
    }
}
