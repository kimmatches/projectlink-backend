package projectlink.services;

//import org.assertj.core.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import projectlink.models.Board;
import projectlink.models.Comment;
import projectlink.repositories.CommentRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class CommentService {
    // 다른 repository 연동해야함
    private final CommentRepository commentRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> getSingleComment(String id) {
        return commentRepository.findById(id);
    }
    public Board createNewComment(String username, String cardId , Comment comment) {
//        AppUser author = appUserRepository.findByUsername(username);
//        comment.setAuthor(author);
//        Card parentCard = cardRepository.getById(cardId);
//        comment.setParentCard(parentCard);
        Comment savedComment = commentRepository.save(comment);
//        parentCard.getComments().add(savedComment);
//        cardRepository.save(parentCard);
//        String boardId = parentCard.getBoard().getId();
//        return boardRepository.getById(boardId);
        return null;
    }

    public Board updateComment(String username, String commentId, Comment updatedComment) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
        if (existingCommentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not exist");
        }

        Comment existingComment = existingCommentOptional.get();
        if (!Objects.equals(existingComment.getAuthor().getUsername(), username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot edit someone else's comment");
        }

        existingComment.setBody(updatedComment.getBody());
        commentRepository.save(existingComment);

//        return boardRepository.getById(existingComment.getParentCard().getBoard().getId());
        return null;
    }

    public Board deleteComment(String username, String commentId) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
        if (existingCommentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not exist");
        }

        Comment existingComment = existingCommentOptional.get();
        if (!Objects.equals(existingComment.getAuthor().getUsername(), username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete someone else's comment");
        }

        String boardId = existingComment.getParentCard().getBoard().getId();
        commentRepository.deleteById(commentId);

//        return boardRepository.getById(boardId);

        return null;
    }

}
