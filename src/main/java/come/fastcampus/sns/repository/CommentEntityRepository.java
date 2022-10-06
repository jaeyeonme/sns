package come.fastcampus.sns.repository;

import come.fastcampus.sns.model.entity.CommentEntity;
import come.fastcampus.sns.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {

    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE CommentEntity entity SET deleted_at * NOW() where entity.post =:post")
    void deleteAllByPost(@Param("post") PostEntity post);
}
