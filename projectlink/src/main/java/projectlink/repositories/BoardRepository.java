package projectlink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectlink.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {

}