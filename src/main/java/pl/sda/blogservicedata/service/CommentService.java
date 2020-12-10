package pl.sda.blogservicedata.service;

import org.springframework.stereotype.Service;
import pl.sda.blogservicedata.exception.BlogPostNotFoundException;
import pl.sda.blogservicedata.exception.CommentNotFondException;
import pl.sda.blogservicedata.exception.UserNotFoundException;
import pl.sda.blogservicedata.model.BlogPost;
import pl.sda.blogservicedata.model.Comment;
import pl.sda.blogservicedata.model.User;
import pl.sda.blogservicedata.model.request.CommentDto;
import pl.sda.blogservicedata.model.request.CommentForm;
import pl.sda.blogservicedata.repository.BlogPostRepository;
import pl.sda.blogservicedata.repository.CommentRepository;
import pl.sda.blogservicedata.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private BlogPostRepository blogPostRepository;
    private UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, BlogPostRepository blogPostRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
    }

    public Comment addCommentToBlogPost(Long blogPostId, CommentDto commentDto) {
        User commentAuthor = userRepository.findById(commentDto.getAuthorId())
                .orElseThrow(() -> new CommentNotFondException("Comment author not found: " + commentDto.getAuthorId()));
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new BlogPostNotFoundException("Blog post not found: " + blogPostId));
        Comment comment = new Comment();
        comment.setAuthor(commentAuthor);
        comment.setContent(commentDto.getContent());
        comment.setCreated(LocalDateTime.now());
        comment.setBlogPost(blogPost);
        return commentRepository.save(comment);
    }

    public void addCommentToBlogPost(CommentForm commentForm) {
        Comment comment = new Comment();
        User author = userRepository.findByEmail(commentForm.getAuthorEmail()).orElseThrow(() ->
                new UserNotFoundException("User not found: " + commentForm.getAuthorEmail()));
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());
        comment.setContent(commentForm.getContent());
        BlogPost blogPost = blogPostRepository.findById(commentForm.getBlogPostId()).orElseThrow(()
                -> new BlogPostNotFoundException("Blog post was not found: " + commentForm.getBlogPostId()));
        comment.setBlogPost(blogPost);
        commentRepository.save(comment);
    }
}
