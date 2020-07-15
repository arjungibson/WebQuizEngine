package engine.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class AnswerInput
{
    @Size(max = 2)
    Set<Integer> answer;

    public AnswerInput(@JsonProperty("answer") @Size Set<Integer> answer) {
        this.answer = answer;
    }
}
