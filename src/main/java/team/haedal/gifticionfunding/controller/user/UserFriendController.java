package team.haedal.gifticionfunding.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.haedal.gifticionfunding.controller.common.annotation.LoginUserId;
import team.haedal.gifticionfunding.dto.common.PagingRequest;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.user.response.FriendReceivedHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.FriendSentHistoryModel;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.service.user.UserFriendService;

@Tag(name = "유저", description = "유저-친구 관련 API")
@RequiredArgsConstructor
@RestController
public class UserFriendController {
    private final UserFriendService userFriendService;


    @Operation(summary = "친구 목록 조회", description = "친구 목록 페이징 조회")
    @GetMapping("/api/user/friend")
    public PagingResponse<UserInfoModel> getFriendPaging(@Valid PagingRequest pagingRequest, @LoginUserId Long userId) {
        return userFriendService.getFriendPaging(pagingRequest, userId);
    }

    @Operation(summary = "내가 보낸 친구 요청 목록 조회", description = "친구 요청 목록 페이징 조회")
    @GetMapping("/api/user/friend-request/sent")
    public PagingResponse<FriendSentHistoryModel> getSentFriendRequestPaging(@Valid PagingRequest pagingRequest,
                                                                             @LoginUserId Long userId) {
        return userFriendService.getSentFriendRequestPaging(pagingRequest, userId);
    }


    @Operation(summary = "친구 요청 하기", description = "친구 요청 id 반환")
    @PostMapping("/api/user/friend-request/sent/{friendId}")
    public Long requestFriend(@LoginUserId Long userId, @PathVariable Long friendId) {
        return userFriendService.requestFriend(userId, friendId);
    }

    @Operation(summary = "내가 받은 친구 요청 목록 조회", description = "친구 요청 목록 페이징 조회")
    @GetMapping("/api/user/friend-request/received")
    public PagingResponse<FriendReceivedHistoryModel> getReceivedFriendRequestPaging(@Valid PagingRequest pagingRequest,
                                                                                     @LoginUserId Long userId) {
        return userFriendService.getReceivedFriendRequestPaging(pagingRequest, userId);
    }

    @Operation(summary = "친구 요청 수락", description = "친구 요청 수락")
    @PostMapping("/api/user/friend-request/received/{actionId}")
    public void acceptFriendRequest(@LoginUserId Long userId, @PathVariable Long actionId) {
        userFriendService.acceptFriendRequest(userId, actionId);
    }


}
