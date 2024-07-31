package projectlink.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projectlink.models.Board;
import projectlink.models.BoardList;
import projectlink.models.Card;
import projectlink.services.BoardListService;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
@Tag(name = "BoardListController", description = "BoardListController")
public class BoardListController {
    private final BoardListService boardListService;

    @Autowired
    public BoardListController(BoardListService boardListService) {
        this.boardListService = boardListService;
    }

    // GET 요청: 모든 보드 리스트 조회
    @GetMapping()
    public List<BoardList> getAllBoardLists() {
        return boardListService.getAllBoardLists();
    }

    // GET 요청: 단일 보드 리스트 조회
    @GetMapping(path = "/boards/lists/{listId}")
    public BoardList getSingleBoardList(@PathVariable String listId) {
        BoardList list = boardListService.getSingleBoardList(listId);
        System.out.println(list.getBoard());
        return list;
    }

    // GET 요청: 특정 보드의 리스트 조회
    @GetMapping(path = "/boards/{boardId}/lists")
    public List<BoardList> getListsForSingleBoard(@PathVariable String boardId) {
        return boardListService.getListsForSingleBoard(boardId);
    }

    // POST 요청: 보드 리스트 생성
    @PostMapping(path = "/boards/{boardId}/lists")
    public Board createList(@PathVariable String boardId, @RequestBody BoardList newList) {
        return boardListService.createBoardList(boardId, newList);
    }

    // PATCH 요청: 보드 리스트 제목 업데이트
    @PatchMapping(path = "/boards/lists/{listId}")
    public Board updateBoardListTitle(
            @PathVariable String listId, @RequestBody Map<String, String> body
    ) throws Exception {
        Optional<String> title = Optional.ofNullable(body.get("title"));
        if (title.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return boardListService.updateBoardListTitle(listId, title.get());
    }

    // PUT 요청: 보드 리스트의 카드 업데이트
    @PutMapping(path = "/boards/{boardId}/lists/{listId}/cards")
    public ResponseEntity<Board> updateBoardListCards(
            @PathVariable String boardId,
            @PathVariable String listId,
            @RequestBody List<Card> newCards
    ) throws Exception{
        System.out.println("boardId");
        System.out.println(boardId);
        System.out.println("listId");
        System.out.println(listId);
        System.out.println("newCards");
        System.out.println(newCards);
        Board updateBoard = boardListService.updateBoardListCards(boardId, listId, newCards);
        System.out.println("IN PUT MAPPING- UPDATE SUCCESSFUL");
        return ResponseEntity.ok().body(updateBoard);
    }

    // DELETE 요청: 보드 리스트 삭제
    @DeleteMapping(path = "/lists/{listId}")
    public Board deleteBoardList(@PathVariable String listId) {
        return boardListService.deleteSingleBoardList(listId);
    }
}
