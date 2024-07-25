package projectlink.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projectlink.models.Board;
import projectlink.models.Comment;
import projectlink.services.CommentService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cards")
@Tag(name = "CommentController", description = "CommentController")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 조회
    @Operation(summary = "댓글 조회", description = "댓글을 조회 합니다.")
    @GetMapping("/comments/{id}")
    public Optional<Comment> getSingleComment(@PathVariable String id) {
        return commentService.getSingleComment(id);
    }
    // 새로운 댓글 생성
    @PostMapping("/{cardId}/comments")
    public Board createNewComment(Principal principal, @PathVariable String cardId, @RequestBody Comment comment) {
        return commentService.createNewComment(principal.getName(), cardId, comment);
    }
    // 댓글 업데이트
    @PutMapping("/comments/{commentId}")
    public Board updateComment(Principal principal, @PathVariable String commentId, @RequestBody Comment updatedComment) {
        return commentService.updateComment(principal.getName(), commentId, updatedComment);
    }
    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public Board deleteComment(Principal principal, @PathVariable String commentId) {
        return commentService.deleteComment(principal.getName(), commentId);
    }
}
