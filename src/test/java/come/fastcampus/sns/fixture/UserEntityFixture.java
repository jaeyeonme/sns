package come.fastcampus.sns.fixture;

import come.fastcampus.sns.model.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String userName, String password, Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }
}
