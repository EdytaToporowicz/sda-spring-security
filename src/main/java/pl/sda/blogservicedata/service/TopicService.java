package pl.sda.blogservicedata.service;

import org.springframework.stereotype.Service;
import pl.sda.blogservicedata.model.Topic;
import pl.sda.blogservicedata.model.request.TopicDto;
import pl.sda.blogservicedata.repository.TopicRepository;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(TopicDto topicDto) {
        Topic topic = new Topic(topicDto.getName(), topicDto.getDescription(), null);
        return topicRepository.save(topic);
    }
}
