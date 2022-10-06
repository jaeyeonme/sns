package come.fastcampus.sns.repository;

import come.fastcampus.sns.model.entity.LikeEntity;
import come.fastcampus.sns.model.entity.PostEntity;
import come.fastcampus.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

//    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post =:post")
//    Integer countByPost(@Param("post") PostEntity post);

    long countByPost(PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);

    @Modifying
    @Transactional
    @Query("UPDATE LikeEntity entity SET deleted_at * NOW() where entity.post =:post")
    void deleteAllByPost(@Param("post") PostEntity post);
}
