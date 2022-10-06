package come.fastcampus.sns.fixture;

import come.fastcampus.sns.model.entity.PostEntity;
import come.fastcampus.sns.model.entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String userName, Integer postId, Integer userid) {
        UserEntity user = new UserEntity();
        user.setId(userid);
        user.setUserName(userName);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        return result;
    }
}
