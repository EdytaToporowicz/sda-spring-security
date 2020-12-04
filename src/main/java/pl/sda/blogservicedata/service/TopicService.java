package pl.sda.blogservicedata.service;

import org.springframework.stereotype.Service;
import pl.sda.blogservicedata.model.Topic;
import pl.sda.blogservicedata.model.request.TopicDto;
import pl.sda.blogservicedata.repository.TopicRepository;

import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(TopicDto topicDto) {
        Topic topic = new Topic(null, topicDto.getName(), null);
        return topicRepository.save(topic);
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }
}
