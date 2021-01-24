package pl.sda.blogservicedata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String email;
    private String name;
    private String password;
    @Pattern(regexp = "ROLE_READER||ROLE_AUTHOR")
    private String role;
}
