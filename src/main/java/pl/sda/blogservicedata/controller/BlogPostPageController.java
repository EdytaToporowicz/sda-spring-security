package pl.sda.blogservicedata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.blogservicedata.model.BlogPost;
import pl.sda.blogservicedata.model.Topic;
import pl.sda.blogservicedata.service.BlogPostService;
import pl.sda.blogservicedata.service.TopicService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BlogPostPageController {

    private BlogPostService blogPostService;
    private TopicService topicService;

    public BlogPostPageController(BlogPostService blogPostService, TopicService topicService) {
        this.blogPostService = blogPostService;
        this.topicService = topicService;
    }

    @GetMapping("/blogPosts")
    public String blogPostsPage(ModelMap modelMap, @RequestParam(required = false) String topic) {
        List<BlogPost> blogPosts = topic == null ? blogPostService.findAll() :
                blogPostService.findByFilter(topic, null, null, null);
 //       List<BlogPost> blogPosts = blogPostService.findAll();
        List<String> topics = topicService.findAll().stream().map(Topic::getName).collect(Collectors.toList());
        modelMap.addAttribute("blogPosts", blogPosts);
        modelMap.addAttribute("topics", topics);
        return "blogPosts";
    }
}
