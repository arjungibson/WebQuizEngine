package engine.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class QuestionEntity {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    private UserEntity user;

    @NotBlank
    private String text;

    @ElementCollection
    @Size(min = 2)
    @NotNull
    private List<String> options;

    @ElementCollection
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Integer> answer;

    public QuestionEntity(){

    }
}
