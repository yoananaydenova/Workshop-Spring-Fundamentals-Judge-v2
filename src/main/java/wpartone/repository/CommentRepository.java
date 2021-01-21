package wpartone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wpartone.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
