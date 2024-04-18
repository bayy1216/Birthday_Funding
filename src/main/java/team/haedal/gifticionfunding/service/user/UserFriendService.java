package team.haedal.gifticionfunding.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.domain.FriendShipActionStatus;
import team.haedal.gifticionfunding.domain.Pair;
import team.haedal.gifticionfunding.dto.common.PagingRequest;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.user.response.FriendReceivedHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.FriendSentHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.entity.user.Friendship;
import team.haedal.gifticionfunding.entity.user.FriendshipAction;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.user.FriendshipActionJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipQueryRepository;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

import static team.haedal.gifticionfunding.entity.user.QFriendship.friendship;

@RequiredArgsConstructor
@Service
public class UserFriendService {
    private final UserJpaRepository userRepository;
    private final FriendshipJpaRepository friendshipRepository;
    private final FriendshipQueryRepository friendshipQueryRepository;
    private final FriendshipActionJpaRepository friendshipActionRepository;


    @Transactional(readOnly = true)
    public PagingResponse<UserInfoModel> getFriendPaging(PagingRequest pagingRequest, Long userId) {
        Page<Friendship> friendshipPage = friendshipQueryRepository.getFriendshipPage(pagingRequest.toPageRequest(), userId);
        return PagingResponse.from(friendshipPage, friendship -> UserInfoModel.from(friendship.getFriend()));
    }

    @Transactional(readOnly = true)
    public PagingResponse<FriendSentHistoryModel> getSentFriendRequestPaging(PagingRequest pagingRequest, Long sendUserId) {
        Page<FriendshipAction> friendshipActionPage = friendshipActionRepository
                .findAllByFromUserIdAndStatus(sendUserId, FriendShipActionStatus.REQUEST, pagingRequest.toPageable());
        return PagingResponse.from(friendshipActionPage, FriendSentHistoryModel::from);
    }

    @Transactional
    public Long requestFriend(Long sendUserId, Long receivedUserId) {
        User sendUser = userRepository.findByIdOrThrow(sendUserId);
        User receivedUser = userRepository.findByIdOrThrow(receivedUserId);
        FriendshipAction friendshipAction = FriendshipAction.create(sendUser, receivedUser, FriendShipActionStatus.REQUEST);
        friendshipActionRepository.save(friendshipAction);
        return friendshipAction.getId();
    }

    @Transactional(readOnly = true)
    public PagingResponse<FriendReceivedHistoryModel> getReceivedFriendRequestPaging(PagingRequest pagingRequest, Long receivedUserId) {
        Page<FriendshipAction> friendshipActionPage = friendshipActionRepository
                .findAllByToUserIdAndStatus(receivedUserId, FriendShipActionStatus.REQUEST, pagingRequest.toPageable());
        return PagingResponse.from(friendshipActionPage, FriendReceivedHistoryModel::from);
    }

    @Transactional
    public void acceptFriendRequest(Long userId, Long actionId) {
        FriendshipAction action = friendshipActionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청이 존재하지 않습니다."));
        if(!action.getToUser().getId().equals(userId)) {
            throw new IllegalArgumentException("다른 사용자의 친구 요청을 수락할 수 없습니다.");
        }
        Pair<Friendship, Friendship> pair = action.makeFriendship();
        friendshipRepository.save(pair.getFirst());
        friendshipRepository.save(pair.getSecond());
    }
}
