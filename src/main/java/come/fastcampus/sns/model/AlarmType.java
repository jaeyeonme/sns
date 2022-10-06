package come.fastcampus.sns.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * TEXT 자체를 DB에 저장하게 되면 변할 수 있다.
 * Ex) OO가 코멘트를 달았습니다.
 * 비효율적이다. DB 데이터를 바꿔줘야 하기 때문
 */
@Getter
@RequiredArgsConstructor
public enum AlarmType {

    NEW_COMMENT_ON_POST("new comment"),
    NEW_LIKE_ON_POST("new like");

    private final String alarmText;
}
