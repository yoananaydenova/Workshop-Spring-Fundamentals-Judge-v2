package wpartone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wpartone.model.entity.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {
}
