package engine.QuizDao;

import engine.Model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("H2")
public interface QuestionDataAccessService extends JpaRepository<QuestionEntity, Integer> {

}
