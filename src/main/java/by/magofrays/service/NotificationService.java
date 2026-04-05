package by.magofrays.service;

import by.magofrays.dto.Notification;
import by.magofrays.entity.Family;
import by.magofrays.entity.Task;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaTemplate<String, Notification> notificationKafkaTemplate;

    @Transactional(value = SUPPORTS)
    public void sendNotificationTask(String key, String message, String from, Task task, UUID notSend){
        var creator = task.getCreatedBy().getMember().getId();
        var issuer = task.getIssuedTo().getMember().getId();
        message = "Задача " + task.getTaskName() + ":\n" + message;
        for(var id : List.of(creator, issuer)){
            if(!id.equals(notSend)) {
                sendNotification(key, message, from, id);
            }
        }
    }

    public void sendNotification(String key, String message, String from, UUID recipient){
        log.info("Sending notification to recipient: {}", recipient);
        notificationKafkaTemplate.send(key, Notification.builder()
                .recipient(recipient)
                        .message(message)
                        .from(from)
                .build());

    }

    @Transactional(value = SUPPORTS)
    public void sendNotificationFamily(String key, String message, String from, Family family, UUID notSend){
        var members = family.getMembers();
        message = "Семья " + family.getFamilyName() + ":\n" + message;
        for(var member : members){
            var id = member.getMember().getId();
            if(!id.equals(notSend)) {
                sendNotification(key, message, from, id);
            }
        }
    }
}
