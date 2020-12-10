package pl.sda.blogservicedata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.blogservicedata.model.BlogPost;
import pl.sda.blogservicedata.model.request.CommentForm;
import pl.sda.blogservicedata.service.BlogPostService;
import pl.sda.blogservicedata.service.CommentService;

import javax.validation.Valid;

@Controller
public class CommentFormController {

    private CommentService commentService;
    private BlogPostService blogPostService;

    public CommentFormController(CommentService commentService, BlogPostService blogPostService) {
        this.commentService = commentService;
        this.blogPostService = blogPostService;
    }

    @PostMapping("/blogPosts/{blogPostId}/comment")
    public String addCommentToBlogPost(
            @PathVariable long blogPostId,
            ModelMap modelMap,
            @ModelAttribute("commentForm") @Valid CommentForm commentForm,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            BlogPost blogPost = blogPostService.findById(blogPostId);
            modelMap.addAttribute("blogPost", blogPost);
            modelMap.addAttribute("commentForm", commentForm);
            return "blogPostDetails";
        }
        commentService.addCommentToBlogPost(commentForm);
        return "redirect:/blogPosts/" + blogPostId;
    }

}
