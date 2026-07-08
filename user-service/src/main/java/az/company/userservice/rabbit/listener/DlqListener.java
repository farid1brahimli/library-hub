package az.company.userservice.rabbit.listener;

import az.company.userservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static az.company.userservice.config.RabbitMQConfig.BORROW_DLQ;

@Component
public class DlqListener {
    @RabbitListener(queues = BORROW_DLQ)
    public void dlqAlert() {
        System.out.println("DLQ ALERT");
    }
}