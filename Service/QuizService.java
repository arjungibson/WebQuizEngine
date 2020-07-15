package engine.Service;

import engine.Exceptions.QuizNotFoundException;
import engine.Model.AnswerInput;
import engine.Model.AnswerResponse;
import engine.Model.QuestionEntity;
import engine.QuizDao.QuestionDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {

    private final QuestionDataAccessService questionDataAccessService;
    private final UserService userService;

    @Autowired
    public QuizService(@Qualifier("H2") QuestionDataAccessService questionDataAccessService, UserService userService)
    {
        this.questionDataAccessService = questionDataAccessService;
        this.userService = userService;
    }

    public List<QuestionEntity> getAllQuestions()
    {
        return questionDataAccessService.findAll();
    }


    public AnswerResponse getAnswer(int id, AnswerInput answerInput)
    {
        if(answerInput.getAnswer() == null)
        {
            answerInput.setAnswer(Set.of());
        }

        Set<Integer> answer = answerInput.getAnswer();

        return questionDataAccessService.findById(id)
                .map(QuestionEntity::getAnswer)
                .map((correctAnswer) -> {
                    AnswerResponse correct = new AnswerResponse(true, "You are correct!");
                    AnswerResponse incorrect = new AnswerResponse(false, "You are wrong!");
                    return answer.equals(correctAnswer) ? correct : incorrect;
                })
                .orElseThrow(() -> new QuizNotFoundException(id));
    }

    public QuestionEntity insertQuestion(QuestionEntity questionEntity, Authentication auth)
    {
        questionEntity.setUser(userService.getByEmail(auth.getName()));
        questionDataAccessService.save(questionEntity);
        return questionEntity;
    }

    public ResponseEntity<String> deleteById(int id, Authentication auth)
    {
        final Optional<QuestionEntity> questionOptional = userService.getByEmail(auth.getName()).getQuestions()
                .stream()
                .filter(questionEntity -> questionEntity.getId() == id)
                .findFirst();

        if(questionDataAccessService.findById(id).isEmpty()){
            return new ResponseEntity<>("No quiz with the given id was found",
                    HttpStatus.NOT_FOUND);
        } else if(questionOptional.isEmpty()){
            return new ResponseEntity<>("You are not the author of this quiz",
                    HttpStatus.FORBIDDEN);
        } else {
            questionOptional.ifPresent(questionEntity ->
                    questionDataAccessService.deleteById(questionEntity.getId()));
            return new ResponseEntity<>("The quiz was successfully deleted",
                    HttpStatus.NO_CONTENT);
        }
    }

    public QuestionEntity getQuestionById(int id)
    {
        return questionDataAccessService
                .findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
    }
}
