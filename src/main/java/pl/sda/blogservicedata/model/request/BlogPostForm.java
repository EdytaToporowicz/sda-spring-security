package pl.sda.blogservicedata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostForm {

    @Length(min = 5, message = "Tytuł blog posta musi mieć co najmniej 100 znaków")
    private String title;
    @Length(min = 100, message = "Treść blog posta musi mieć co najmniej 100 znaków")
    private String content;
    @Email(message = "Email musi być poprawny")
    private String authorEmail;
    private List<String> selectedTopics;
}
