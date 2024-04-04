package team.haedal.gifticionfunding.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.haedal.gifticionfunding.controller.common.annotation.LoginUserId;
import team.haedal.gifticionfunding.dto.user.response.UserInfoDto;
import team.haedal.gifticionfunding.service.user.UserService;

@Tag(name = "유저", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @Operation(summary = "내 정보 조회", description = "성공시 유저 정보 반환")
    @GetMapping("/api/user/me")
    public UserInfoDto getUserInfo(@LoginUserId Long userId) {
        return userService.getUserInfo(userId);
    }
}
