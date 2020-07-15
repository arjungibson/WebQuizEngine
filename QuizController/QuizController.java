package engine.QuizController;

import engine.Model.*;
import engine.Service.QuizService;
import engine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/api")
@RestController
public class QuizController
{
    private final QuizService quizService;
    private final UserService userService;

    @Autowired
    public QuizController(QuizService quizService, UserService userService)
    {
        this.quizService = quizService;
        this.userService = userService;
    }

    @GetMapping(path = "quizzes")
    public List<QuestionEntity> getAllQuestions()
    {
        return quizService.getAllQuestions();
    }

    @GetMapping(path = "/quizzes/{id}")
    public QuestionEntity getQuestionById(@PathVariable int id)
    {
        return quizService.getQuestionById(id);
    }

    @PostMapping(path = "quizzes/{id}/solve")
    public AnswerResponse getAnswerResponse(@PathVariable("id") int id,
                                            @RequestBody AnswerInput answerInput)
    {
        return quizService.getAnswer(id, answerInput);
    }

    @PostMapping(path = "/quizzes")
    public QuestionEntity createQuestion(@Valid @NotNull @RequestBody QuestionEntity questionEntity, Authentication auth)
    {
        return quizService.insertQuestion(questionEntity, auth);
    }

    @PostMapping(path = "/register")
    public void registerNewUser(@Valid @NotNull @RequestBody UserEntity userEntity)
    {
        userService.insertNewUser(userEntity);
    }

    @GetMapping(path = "/users")
    public List<UserEntity> findAllUsers()
    {
        return userService.findAllUsers();
    }

    @DeleteMapping(path = "quizzes/{id}")
    public ResponseEntity<String> deleteQuizById(@PathVariable int id, Authentication auth)
    {
        return quizService.deleteById(id, auth);
    }

    @DeleteMapping(path = "/deleteAccount")
    public void deleteAccount(Authentication auth)
    {
        userService.deleteAllByUser(auth);
    }
}
