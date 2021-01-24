package pl.sda.blogservicedata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String email;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<BlogPost> blogPosts;
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
    private String role;
    private String password;

}
