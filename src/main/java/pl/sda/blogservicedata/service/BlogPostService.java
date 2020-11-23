package pl.sda.blogservicedata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.sda.blogservicedata.exception.BlogPostNotFoundException;
import pl.sda.blogservicedata.exception.UserNotFoundException;
import pl.sda.blogservicedata.model.BlogPost;
import pl.sda.blogservicedata.model.Topic;
import pl.sda.blogservicedata.model.User;
import pl.sda.blogservicedata.model.mapping.BlogPostMapper;
import pl.sda.blogservicedata.model.request.BlogPostDto;
import pl.sda.blogservicedata.repository.BlogPostRepository;
import pl.sda.blogservicedata.repository.FilteringBlogPostRepository;
import pl.sda.blogservicedata.repository.TopicRepository;
import pl.sda.blogservicedata.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final FilteringBlogPostRepository filteringBlogPostRepository;
    private final BlogPostMapper blogPostMapper;
    private UserRepository userRepository;
    private TopicRepository topicRepository;

    @Autowired
    public BlogPostService(final BlogPostRepository blogPostRepository, FilteringBlogPostRepository filteringBlogPostRepository, final BlogPostMapper blogPostMapper) {
        this.blogPostRepository = blogPostRepository;
        this.filteringBlogPostRepository = filteringBlogPostRepository;
        this.blogPostMapper = blogPostMapper;
    }

    public BlogPost save(final BlogPostDto blogPostDto) {
        BlogPost blogPost = blogPostMapper.map(blogPostDto);
        blogPost.setCreated(LocalDateTime.now());
        return blogPostRepository.save(blogPost);
    }

    public List<BlogPost> findAll() {
        return blogPostRepository.findAll();
    }

    public BlogPost findById(final long id) {
        return blogPostRepository.findById(id).orElseThrow(
                () -> new BlogPostNotFoundException("Could not find blog post with id: " + id));
    }

    public List<BlogPost> findByFilter(final @Nullable List<String> topicsIds, final @Nullable Long authorId, final @Nullable String titlePhrase, @Nullable Pageable pageable) {
        User author = null;
        List<Topic> topics = null;
        if (authorId != null) {
            author = userRepository.findById(authorId).orElseThrow(() -> new UserNotFoundException("User not found: " + authorId));
        }
        if (topicsIds != null && !topicsIds.isEmpty()) {
            topics = topicRepository.findAllById(topicsIds);
        }
        return filteringBlogPostRepository.filterBlogPosts(topics, author, titlePhrase, pageable);
    }

    public void removeById(final long id) {
        Optional<BlogPost> blogPostToRemove = blogPostRepository.findById(id);
        blogPostToRemove
                .ifPresentOrElse(
                        blogPost -> blogPostRepository.delete(blogPost),
                        () -> {
                            throw new BlogPostNotFoundException("Could not find blog post while attempting to delete");
                        });
    }

}
