package projectlink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectlink.models.BoardList;
import projectlink.models.Board;

import java.util.List;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, String> {
    List<BoardList> getBoardListByBoard(Board board);
}
