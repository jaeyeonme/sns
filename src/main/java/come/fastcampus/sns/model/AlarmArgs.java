package come.fastcampus.sns.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {

    // 알람을 발생시킨 사람
    private Integer fromUserId;
    // 알람이 발생된 주체 ID
    private Integer targetId;
}

// comment: OO씨가 새 코멘트를 장성했씁니다. -> postId, commentId
// OO외 2명이 새 코멘트를 작성했습니다. -> commentId, commentId
