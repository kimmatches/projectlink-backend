package projectlink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projectlink.models.CardFile;

public interface CardFileRepository extends JpaRepository<CardFile, Long> {
}