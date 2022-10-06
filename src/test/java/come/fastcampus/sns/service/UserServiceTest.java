package come.fastcampus.sns.service;

import come.fastcampus.sns.exception.ErrorCode;
import come.fastcampus.sns.exception.SnsApplicationException;
import come.fastcampus.sns.fixture.UserEntityFixture;
import come.fastcampus.sns.model.entity.UserEntity;
import come.fastcampus.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserEntityRepository userEntityRepository;
    @MockBean
    private BCryptPasswordEncoder encoder;


    @DisplayName("회원가입이 정상적으로 동작하는 경우")
    @Test
    void signUp_test_success() throws Exception {
        String userName = "userName";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(userName, password, 1));

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @DisplayName("회원가입시 userName으로 회원가입한 유저가 이미 있는 경우")
    @Test
    void signUp_test_failure() throws Exception {
        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
                () -> userService.join(userName, password));

        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, e.getErrorCode());
    }

    @DisplayName("로그인이 정상적으로 동작하는 경우")
    @Test
    void login_test_success() throws Exception {
        String userName = "userName";
        String password = "password";

        // TEST용 ENTITY
        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @DisplayName("로그인시 userName으로 회원가입한 유저가 없는 경우")
    @Test
    void signUp_test_failure_1() throws Exception {
        String userName = "userName";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
                () -> userService.login(userName, password));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @DisplayName("로그인시 password가 틀린 경우")
    @Test
    void signUp_test_failure_2() throws Exception {
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";
        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
                () -> userService.login(userName, wrongPassword));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
    }
}