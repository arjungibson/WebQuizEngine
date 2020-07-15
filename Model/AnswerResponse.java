package engine.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AnswerResponse
{
    private final boolean success;
    private final String feedback;

    public AnswerResponse(@JsonProperty("success") boolean success,
                          @JsonProperty("feedback") String feedback)
    {
        this.success = success;
        this.feedback = feedback;
    }
}
