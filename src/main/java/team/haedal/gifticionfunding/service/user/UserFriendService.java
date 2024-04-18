package team.haedal.gifticionfunding.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.dto.common.PagingRequest;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.user.response.FriendReceivedHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.FriendSentHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

@RequiredArgsConstructor
@Service
public class UserFriendService {
    private final UserJpaRepository userRepository;


    @Transactional(readOnly = true)
    public PagingResponse<UserInfoModel> getFriendPaging(PagingRequest pagingRequest, Long userId) {
        Page<User> userPage =  userRepository.findAllById(userId, pagingRequest.toPageable());
        return PagingResponse.from(userPage, UserInfoModel::from);
    }

    public PagingResponse<FriendSentHistoryModel> getSentFriendRequestPaging(PagingRequest pagingRequest, Long sendUserId) {
        throw new UnsupportedOperationException();
    }

    public Long requestFriend(Long sendUserId, Long receivedUserId) {
        throw new UnsupportedOperationException();
    }

    public PagingResponse<FriendReceivedHistoryModel> getReceivedFriendRequestPaging(PagingRequest pagingRequest, Long receivedUserId) {
        throw new UnsupportedOperationException();
    }

    public void acceptFriendRequest(Long userId, Long actionId) {
        throw new UnsupportedOperationException();
    }
}
