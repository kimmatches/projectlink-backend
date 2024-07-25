package projectlink.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projectlink.models.AppUser;
import projectlink.models.Board;
import projectlink.services.AppUserService;
import projectlink.services.BoardService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@Tag(name = "AppUserController", description = "AppUserController")
public class AppUserController {

    private final AppUserService appUserService;
    private final BoardService boardService;

    @Autowired
    public AppUserController(AppUserService appUserService, BoardService boardService) {
        this.boardService = boardService;
        this.appUserService = appUserService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> index() {
        List<AppUser> allAppUsers = appUserService.getAllUsers();
        return ResponseEntity.ok().body(allAppUsers);
    }

    @GetMapping(path = "/users/{username}")
    public ResponseEntity<AppUser> findByUsername(@PathVariable("username") String username) {
        AppUser appUser = appUserService.getUserByUsername(username);
        return ResponseEntity.ok().body(appUser);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/register")
                .toUriString()
        );
        AppUser newAppUser = appUserService.createUser(appUser);
        return ResponseEntity.created(uri).body(newAppUser);
    }

    @GetMapping("/{username}/boards")
    public ResponseEntity<List<Board>> getUsersBoards(@PathVariable String username) {
        List<Board> boards = appUserService.getUsersBoards(username);
        return ResponseEntity.ok().body(boards);
    }

    @PostMapping("/{username}/boards/{boardId}")
    public ResponseEntity<AppUser> addBoardToRecents(@PathVariable String username, @PathVariable String boardId) {
        Board board = boardService.getSingleBoard(boardId);
        AppUser appUser = appUserService.addToUsersRecentBoards(username, board);
        return ResponseEntity.ok().body(appUser);
    }
}