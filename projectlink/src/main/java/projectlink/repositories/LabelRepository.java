package projectlink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectlink.models.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, String> {
}