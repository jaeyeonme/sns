package come.fastcampus.sns.controller;

import come.fastcampus.sns.controller.request.UserJoinRequest;
import come.fastcampus.sns.controller.request.UserLoginRequest;
import come.fastcampus.sns.controller.response.AlarmResponse;
import come.fastcampus.sns.controller.response.Response;
import come.fastcampus.sns.controller.response.UserJoinResponse;
import come.fastcampus.sns.controller.response.UserLoginResponse;
import come.fastcampus.sns.exception.ErrorCode;
import come.fastcampus.sns.exception.SnsApplicationException;
import come.fastcampus.sns.model.User;
import come.fastcampus.sns.service.UserService;
import come.fastcampus.sns.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        // join
        User user = userService.join(request.getName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        // login
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarms(Pageable pageable, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to User class failed"));
        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }
}
