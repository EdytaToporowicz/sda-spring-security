package pl.sda.blogservicedata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {

    private long blogPostId;
    @Email(message = "Email musi mieć poprawny format")
    private String authorEmail;
    @NotBlank(message = "Treść nie może być pusta")
    private String content;
}
