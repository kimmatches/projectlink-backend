package projectlink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectlink.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}