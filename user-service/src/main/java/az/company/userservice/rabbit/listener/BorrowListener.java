package az.company.userservice.rabbit.listener;

import az.company.userservice.dao.entity.BorrowHistoryEntity;
import az.company.userservice.dao.repository.BorrowHistoryRepository;
import az.company.userservice.dao.repository.UserRepository;
import az.company.userservice.exception.NotFoundException;
import az.company.userservice.model.dto.BorrowEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static az.company.userservice.config.RabbitMQConfig.BORROW_HISTORY_QUEUE;
import static az.company.userservice.exception.enums.ErrorStatus.USER_NOT_FOUND;
import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class BorrowListener {
    private final RabbitTemplate rabbitTemplate;
    private final BorrowHistoryRepository borrowHistoryRepository;
    private final UserRepository userRepository;

    @RabbitListener(queues = BORROW_HISTORY_QUEUE)
    public void listen(BorrowEvent borrowEvent) {
        var userEntity = userRepository.findById(borrowEvent.getUserId())
                .orElseThrow(
                        () -> new NotFoundException(
                                USER_NOT_FOUND.name(),
                                format(USER_NOT_FOUND.getMessage(), borrowEvent.getId())
                        )
                );
        var borrowHistoryEntity = BorrowHistoryEntity.builder()
                .bookId(borrowEvent.getBookId())
                .borrowedAt(borrowEvent.getBorrowedAt())
                .returnedAt(borrowEvent.getReturnedAt())
                .user(userEntity)
                .bookTitle(borrowEvent.getBookTitle())
                .status(borrowEvent.getStatus())
                .build();
        borrowHistoryRepository.save(borrowHistoryEntity);
    }
}
