package projectlink.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import projectlink.models.AppUser;
import projectlink.models.Board;
import projectlink.models.BoardList;
import projectlink.repositories.AppUserRepository;
import projectlink.repositories.BoardRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, AppUserRepository appUserRepository) {
        this.boardRepository = boardRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getSingleBoard(String boardId) {
        Optional<Board> board= boardRepository.findById(boardId);
        if (board.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board does not exist");
        }
        return board.get();
    }

    public Board createBoard(String boardName, String background, String backgroundThumbnail, AppUser appUser) {
        Board board = Board.builder()
                .boardName(boardName)
                .background(background)
                .backgroundThumbnail(backgroundThumbnail)
                .owner(appUser)
                .build();
        return boardRepository.save(board);
    }

    public void deleteBoard(String boardId) {
        Optional<Board> optional = boardRepository.findById(boardId);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }

        Board board = optional.get();

        log.info("Assessing users");
        log.info(String.valueOf(board.getRecentlyViewedBy().size()));

        for (AppUser user : board.getRecentlyViewedBy()) {
            log.info("recently viewed user is {}", user.toString());
            List<Board> filteredBoards = user.getRecentBoards().stream()
                    .filter(b -> !Objects.equals(b.getId(), boardId))
                    .collect(Collectors.toList());
            log.info(String.valueOf(filteredBoards.size()));
            user.setRecentBoards(filteredBoards);
            appUserRepository.save(user);
        }

        for (AppUser user : board.getStarredBy()) {
            log.info("starred by user is {}", user.toString());
            List<Board> filteredBoards = user.getStarredBoards().stream()
                    .filter(b -> !Objects.equals(b.getId(), boardId))
                    .collect(Collectors.toList());
            user.setStarredBoards(filteredBoards);
            appUserRepository.save(user);
        }

        boardRepository.deleteById(boardId);
    }


    public Board updateBoard(String boardId, Board newBoard) {
        Board board = boardRepository.getById(boardId);
        List<BoardList> newLists = newBoard.getLists();
        newLists.forEach(l -> l.setBoard(board));
        board.setLists(newLists);
        board.setBoardName(newBoard.getBoardName());
        return boardRepository.save(board);
    }

    public Board starBoard(String boardId, AppUser appUser) {
        Board board = boardRepository.getById(boardId);
        List<AppUser> currentStarredBy = board.getStarredBy();
        if (currentStarredBy.contains(appUser)) {
            board.getStarredBy().remove(appUser);
        } else {
            board.getStarredBy().add(appUser);
        }
        return boardRepository.save(board);
    }

    public Board updateBoardTitle(String boardId, String newTitle) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found")
        );
        board.setBoardName(newTitle);
        return boardRepository.save(board);
    }
}