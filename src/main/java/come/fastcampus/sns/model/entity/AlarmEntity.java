package come.fastcampus.sns.model.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import come.fastcampus.sns.model.AlarmArgs;
import come.fastcampus.sns.model.AlarmType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "\"alarm\"", indexes = {
    @Index(name = "user_id_idx", columnList = "user_id")
})
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class AlarmEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 알람을 받은 사람
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    // 알람이 어떤 타입인지?
    // 컬럼으로 지정하는 이유? 알람을 뭉쳐서 보여줘야 하기 때문
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    // Ex) OO가 코멘트를 달았습니다. -> 이동까지
    // Alarm 정보에는 알람 발생 시킨 사람, 알람이 발생된 POST
    // MySQL 기준 8 버전 부터 추가되었다.
    // JsonB : index를 설정할 수 있다.
    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private AlarmArgs args;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static AlarmEntity of(UserEntity userEntity, AlarmType alarmType, AlarmArgs args) {
        AlarmEntity entity = new AlarmEntity();
        entity.setUser(userEntity);
        entity.setAlarmType(alarmType);
        entity.setArgs(args);
        return entity;
    }
}


