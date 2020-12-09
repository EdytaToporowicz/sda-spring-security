package pl.sda.blogservicedata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.blogservicedata.model.Topic;
import pl.sda.blogservicedata.model.request.BlogPostForm;
import pl.sda.blogservicedata.service.BlogPostService;
import pl.sda.blogservicedata.service.TopicService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class BlogPostFormController {

    private BlogPostService blogPostService;
    private TopicService topicService;

    public BlogPostFormController(BlogPostService blogPostService, TopicService topicService) {
        this.blogPostService = blogPostService;
        this.topicService = topicService;
    }

    @GetMapping("/blogPostForm")
    public String getBlogPostForm(ModelMap modelMap) {
        BlogPostForm blogPostForm = new BlogPostForm();
        blogPostForm.setSelectedTopics(new ArrayList<>());
        List<Topic> topics = topicService.findAll();
//        for(Topic topic : topics) {
//            Topic emptyTopic = new Topic();
//            emptyTopic.setId(-1L);
//            blogPostForm.getSelectedTopics().add(emptyTopic);
//        }
        modelMap.addAttribute("topics", topics);
        modelMap.addAttribute("blogPostForm", blogPostForm);
        return "blogPostForm";
    }

    @PostMapping("/blogPostForm")
    public String saveBlogPostFromForm(@ModelAttribute("blogPostForm") @Valid BlogPostForm blogPostForm, Errors errors) {
        if (errors.hasErrors()) {
            return "blogPostForm";
        }
        blogPostService.saveFromFormData(blogPostForm);
        return "redirect:blogPosts";
    }
}
