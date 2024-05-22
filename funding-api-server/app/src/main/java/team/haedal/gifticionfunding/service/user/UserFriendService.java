package team.haedal.gifticionfunding.service.user;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.domain.user.FriendShipActionStatus;
import team.haedal.gifticionfunding.domain.Pair;
import team.haedal.gifticionfunding.dto.common.PagingRequest;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.user.response.FriendReceivedHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.FriendSentHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.funding.FundingArticleSubscriber;
import team.haedal.gifticionfunding.entity.user.Friendship;
import team.haedal.gifticionfunding.entity.user.FriendshipAction;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.funding.FundingArticleJpaRepository;
import team.haedal.gifticionfunding.repository.funding.FundingArticleSubscriberJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipActionJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipQueryRepository;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

@RequiredArgsConstructor
@Service
public class UserFriendService {
    private final UserJpaRepository userRepository;
    private final FriendshipJpaRepository friendshipRepository;
    private final FriendshipQueryRepository friendshipQueryRepository;
    private final FriendshipActionJpaRepository friendshipActionRepository;
    private final FundingArticleJpaRepository fundingArticleRepository;
    private final FundingArticleSubscriberJpaRepository fundingArticleSubscriberRepository;

    /**
     * 친구 목록 페이징 조회 A->B 관계의 A 사용자의 친구 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public PagingResponse<UserInfoModel> getFriendPaging(PagingRequest pagingRequest, Long userId) {
        Page<Friendship> friendshipPage = friendshipQueryRepository.getFriendshipPage(pagingRequest.toPageRequest(), userId);
        return PagingResponse.from(friendshipPage, friendship -> UserInfoModel.from(friendship.getFriend()));
    }

    /**
     * FriendShipAction에서 Status가 REQUEST인 친구 요청 목록을 페이징 조회한다.
     * 친구요청 A->B에서 A가 보낸 친구 요청 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public PagingResponse<FriendSentHistoryModel> getSentFriendRequestPaging(PagingRequest pagingRequest, Long sendUserId) {
        Page<FriendshipAction> friendshipActionPage = friendshipActionRepository
                .findAllByFromUserIdAndStatus(sendUserId, FriendShipActionStatus.REQUEST, pagingRequest.toPageable());
        return PagingResponse.from(friendshipActionPage, FriendSentHistoryModel::from);
    }

    /**
     * FriendShipActionStatus.REQUEST 친구 요청을 생성한다.
     */
    @Transactional
    public Long requestFriend(Long sendUserId, Long receivedUserId) {
        if (Objects.equals(sendUserId, receivedUserId)) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }
        User sendUser = userRepository.findByIdOrThrow(sendUserId);
        User receivedUser = userRepository.findByIdOrThrow(receivedUserId);
        FriendshipAction friendshipAction = FriendshipAction.create(sendUser, receivedUser, FriendShipActionStatus.REQUEST);
        friendshipActionRepository.save(friendshipAction);
        return friendshipAction.getId();
    }

    /**
     * FriendShipAction에서 Status가 REQUEST인 친구 요청 목록을 페이징 조회한다.
     * 친구요청 A->B에서 받은 사람인 B가 받은 친구 요청 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public PagingResponse<FriendReceivedHistoryModel> getReceivedFriendRequestPaging(PagingRequest pagingRequest, Long receivedUserId) {
        Page<FriendshipAction> friendshipActionPage = friendshipActionRepository
                .findAllByToUserIdAndStatus(receivedUserId, FriendShipActionStatus.REQUEST, pagingRequest.toPageable());
        return PagingResponse.from(friendshipActionPage, FriendReceivedHistoryModel::from);
    }

    /**
     * 친구 요청을 수락한다.
     * 친구 요청을 수락하면 FriendshipAction을 Friendship으로 변환하여 저장한다.
     * A->B, B->A의 Friendship을 생성한다.
     * 이후 FriendshipAction은 삭제한다.
     */
    @Transactional
    public void acceptFriendRequest(Long userId, Long actionId) {
        FriendshipAction action = friendshipActionRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("친구요청", actionId));
        if (!action.getToUser().getId().equals(userId)) {
            throw new IllegalArgumentException("다른 사용자의 친구 요청을 수락할 수 없습니다.");
        }
        Pair<Friendship, Friendship> pair = action.makeFriendship();
        friendshipRepository.save(pair.getFirst());
        friendshipRepository.save(pair.getSecond());
        friendshipActionRepository.delete(action);

        Long fromUserId = action.getFromUser().getId();
        Long toUserId = action.getToUser().getId();

        // 펀딩게시글 구독을 갱신하는 작업. A와 B가 친구가 되었을 때,
        // A의 친구들의 게시글을 B가 구독하게 한다.
        // B의 친구들의 게시글을 A가 구독하게 한다.

        addSubscribe(fromUserId, toUserId);
        addSubscribe(toUserId, fromUserId);
    }

    private void addSubscribe(Long userId, Long newFriendId){
        List<Long> prevFriendsId = friendshipRepository.findAllByUserId(userId)
                .stream().map(f -> f.getFriend().getId()).toList();
        List<Long> friendsIdOfNewFriend = friendshipRepository.findAllByUserId(newFriendId)
                .stream().map(f -> f.getFriend().getId()).toList();
        List<Long> saveFriendsId = friendsIdOfNewFriend.stream()
                .filter(friendId -> !prevFriendsId.contains(friendId))
                .toList();
        List<FundingArticle> fundingArticles = fundingArticleRepository.findAllByUserIdIn(saveFriendsId);
        List<FundingArticleSubscriber> subscribers = fundingArticles
                .stream()
                .map(article -> FundingArticleSubscriber.createById(article, userId))
                .toList();
        fundingArticleSubscriberRepository.saveAll(subscribers);
    }



    /**
     * 친구 요청을 거절한다.
     */
    @Transactional
    public void rejectFriendRequest(Long userId, Long actionId) {
        FriendshipAction action = friendshipActionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청이 존재하지 않습니다."));
        if (!action.getToUser().getId().equals(userId)) {
            throw new IllegalArgumentException("다른 사용자의 친구 요청을 거절할 수 없습니다.");
        }
        action.reject();
    }
}
