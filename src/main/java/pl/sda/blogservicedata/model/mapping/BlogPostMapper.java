package pl.sda.blogservicedata.model.mapping;

import org.springframework.stereotype.Component;
import pl.sda.blogservicedata.exception.TopicNotFoundException;
import pl.sda.blogservicedata.exception.UserNotFoundException;
import pl.sda.blogservicedata.model.BlogPost;
import pl.sda.blogservicedata.model.Topic;
import pl.sda.blogservicedata.model.request.BlogPostDto;
import pl.sda.blogservicedata.model.request.BlogPostForm;
import pl.sda.blogservicedata.repository.TopicRepository;
import pl.sda.blogservicedata.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogPostMapper {

    private UserRepository userRepository;
    private TopicRepository topicRepository;

    public BlogPostMapper(UserRepository userRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public BlogPost map(final BlogPostDto blogPostDto) {
        final BlogPost blogPost = new BlogPost();
        blogPost.setAuthor(userRepository.findById(blogPostDto.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException("Author not found: " + blogPostDto.getAuthorId())));
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        List<Topic> topicsList = blogPostDto.getTopics().stream()
                .map(topic -> topicRepository.findByName(topic)
                        .orElseThrow(() -> new TopicNotFoundException("Topic not found in catalogue: " + topic)))
                .collect(Collectors.toList());
        blogPost.setTopics(topicsList);
        return blogPost;
    }

    public BlogPost mapFormData(BlogPostForm blogPostForm) {
        final BlogPost blogPost = new BlogPost();
        blogPost.setAuthor(userRepository.findByEmail(blogPostForm.getAuthorEmail())
                .orElseThrow(() -> new UserNotFoundException("Author not found: " + blogPostForm.getAuthorEmail())));
        blogPost.setTitle(blogPostForm.getTitle());
        blogPost.setContent(blogPostForm.getContent());
        List<Topic> selectedTopics = blogPostForm.getSelectedTopics().stream().map(topic -> topicRepository.findByName(topic).orElseThrow(() ->
                new TopicNotFoundException("Topic not found: " + topic))).collect(Collectors.toList());
        blogPost.setTopics(selectedTopics);
        return blogPost;
    }
}
