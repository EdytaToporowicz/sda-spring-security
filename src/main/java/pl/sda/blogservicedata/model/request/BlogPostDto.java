package pl.sda.blogservicedata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sda.blogservicedata.model.Topic;

import javax.validation.constraints.*;
import java.util.List;


@Data
@AllArgsConstructor
public class BlogPostDto {

    @NotNull
    private long authorId;
    @NotNull
    @Size(min = 5, max = 50)
    @Pattern(regexp = "^\\p{javaUpperCase}.*", message = "Tytuł musi zaczynać się wielką literą")
    private String title;
    @NotNull
    @Size(max = 1000)
    private String content;
    @NotNull
    private List<String> topics;

//    @AssertTrue(message = "Tytuł musi zaczynać się wielką literą")
//    public boolean isTitleValid() {
//        return Character.isUpperCase(title.charAt(0));
//    }

}
