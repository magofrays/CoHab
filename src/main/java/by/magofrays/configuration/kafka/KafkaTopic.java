package by.magofrays.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Value("${kafka.topics.notification}")
    private String notificationTopic;

    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name(notificationTopic).build();
    }

}