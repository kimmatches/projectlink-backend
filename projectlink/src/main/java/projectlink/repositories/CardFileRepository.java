package projectlink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projectlink.models.Card;
import projectlink.models.CardFile;

import java.util.List;

public interface CardFileRepository extends JpaRepository<CardFile, Long> {
    List<CardFile> findByCard(Card card);
}